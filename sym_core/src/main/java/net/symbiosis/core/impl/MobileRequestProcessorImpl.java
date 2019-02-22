package net.symbiosis.core.impl;

import net.symbiosis.authentication.authentication.MobileAuthenticationProvider;
import net.symbiosis.common.structure.Pair;
import net.symbiosis.core.contract.*;
import net.symbiosis.core.contract.symbiosis.SymCashoutAccount;
import net.symbiosis.core.helper.ValidationHelper;
import net.symbiosis.core.service.ConverterService;
import net.symbiosis.core.service.MobileRequestProcessor;
import net.symbiosis.core.service.VoucherProcessor;
import net.symbiosis.core.service.WalletManager;
import net.symbiosis.core_lib.enumeration.SymChannel;
import net.symbiosis.core_lib.enumeration.SymFinancialInstitutionType;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.log.sym_cashout_transaction;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.log.sym_session;
import net.symbiosis.persistence.entity.complex_type.log.sym_swipe_transaction;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_company;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_cashout_account;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.entity.enumeration.sym_auth_group;
import net.symbiosis.persistence.entity.enumeration.sym_country;
import net.symbiosis.persistence.entity.enumeration.sym_financial_institution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static net.symbiosis.common.configuration.Configuration.getProperty;
import static net.symbiosis.core_lib.enumeration.SymChannel.SMART_PHONE;
import static net.symbiosis.core_lib.enumeration.SymEventType.*;
import static net.symbiosis.core_lib.enumeration.SymFinancialInstitutionType.*;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.*;
import static net.symbiosis.core_lib.utilities.CommonUtilities.formatFullMsisdn;
import static net.symbiosis.core_lib.utilities.SymValidator.*;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.SymEnumHelper.*;

/***************************************************************************
 *																		   *
 * Created:	 20 / 10 / 2016												   *
 * Author:	  Tsungai Kaviya											   *
 * Contact:	 tsungai.kaviya@gmail.com									   *
 * 																		   *
 ***************************************************************************/

@Service
public class MobileRequestProcessorImpl implements MobileRequestProcessor {

    private static final Logger logger = Logger.getLogger(MobileRequestProcessorImpl.class.getSimpleName());
    private final VoucherProcessor voucherProcessor;
    private final ConverterService converterService;
    private final WalletManager walletManager;

    @Autowired
    public MobileRequestProcessorImpl(VoucherProcessor voucherProcessor, ConverterService converterService, WalletManager walletManager) {
        this.voucherProcessor = voucherProcessor;
        this.converterService = converterService;
        this.walletManager = walletManager;
    }

    @Override
    public SymSystemUserList startSession(String imei, String username, String pin, SymChannel channel) {
        logger.info(format("Performing login for %s", username));

        String request = format("imei:%s|username:%s|pin:%s|channel:%s", imei, username, pin, channel.name());

        sym_request_response_log requestResponseLog = new sym_request_response_log(
                fromEnum(SMART_PHONE), fromEnum(USER_LOGIN), request).save();

        MobileAuthenticationProvider authenticationProvider = new MobileAuthenticationProvider(
                requestResponseLog, username, pin, imei
        );

        SymResponseObject<sym_auth_user> authResponse = authenticationProvider.authenticateUser();

        if (!authResponse.getResponseCode().equals(SUCCESS)) {
            logResponse(null, requestResponseLog, authResponse.getResponseCode());
            return new SymSystemUserList(authResponse.getResponseCode());
        }

        sym_session newSession = getEntityManagerRepo().findLast(sym_session.class,
                singletonList(new Pair<>("auth_user.id", authResponse.getResponseObject().getId())));

        logResponse(authResponse.getResponseObject(), requestResponseLog, authResponse.getResponseCode());

        return new SymSystemUserList(authResponse.getResponseCode(), converterService.toDTO(newSession));
    }

    @Override
    public SymResponse endSession(Long sessionId, String imei, String authToken, SymChannel channel) {

        logger.info(format("Performing logout for session %s", sessionId));

        String request = format("sessionId:%s|imei:%s|authToken:%s|channel:%s", sessionId, imei, authToken, channel.name());
        sym_request_response_log requestResponseLog = new sym_request_response_log(
                fromEnum(SMART_PHONE), fromEnum(USER_LOGOUT), request).save();

        sym_session currentSession = getEntityManagerRepo().findById(sym_session.class, sessionId);

        SymResponseObject logoutResponse;

        if (currentSession == null) {
            logoutResponse = new SymResponseObject(DATA_NOT_FOUND);
            logResponse(null, requestResponseLog, logoutResponse.getResponseCode());
        } else {
            MobileAuthenticationProvider authenticationProvider = new MobileAuthenticationProvider(
                    requestResponseLog, currentSession.getAuth_user()
            );
            requestResponseLog.setAuth_user(currentSession.getAuth_user());
            requestResponseLog.setSystem_user(currentSession.getAuth_user().getUser());
            requestResponseLog.save();

            authenticationProvider.setSession(currentSession);
            authenticationProvider.setDeviceId(imei);
            authenticationProvider.setAuthToken(authToken);

            logoutResponse = authenticationProvider.endSession();
            logResponse(currentSession.getAuth_user(), requestResponseLog, logoutResponse.getResponseCode());
        }

        return new SymResponse(logoutResponse.getResponseCode());
    }

    private SymResponseObject<sym_session> verifyLogin(Long userId, String imei, String authToken) {

        logger.info(format("Verifying auth token %s for user %s with imei %s", authToken, userId, imei));

        if (userId == null) {
            logger.info("Authentication failed! userId cannot be null");
            return new SymResponseObject<>(AUTH_AUTHENTICATION_FAILED);
        }

        if (!isValidAuthData(authToken)) {
            logger.info(format("Authentication failed! Invalid auth token %s", authToken));
            return new SymResponseObject<>(AUTH_AUTHENTICATION_FAILED);
        }

        sym_auth_user authUser = getEntityManagerRepo().findFirst(sym_auth_user.class, asList(
            new Pair<>("sym_user_id", userId),
            new Pair<>("channel_id", fromEnum(SMART_PHONE).getId())
        ));

        if (authUser == null) {
            logger.info(format("Authentication failed! User %s does not exist on SMART_PHONE chanel", userId));
            return new SymResponseObject<>(AUTH_NON_EXISTENT);
        }

        sym_session authSession = getEntityManagerRepo().findLast(sym_session.class, asList(
            new Pair<>("auth_user_id", authUser.getId()),
            new Pair<>("device_id", imei)
        ));

        if (authSession == null) {
            logger.info(format("Authentication failed! Session for auth user %s with imei %s does not exist", authUser.getId(), imei));
            return new SymResponseObject<>(AUTH_NON_EXISTENT);
        }

        if (authSession.getAuth_token().equals(authToken)) {
            logger.info(format("Authentication verified for user %s with imei %s", userId, imei));
            //TODO check auth token expiry
            //TODO device is in sym_device_phone table and is active
            authUser.setLast_auth_date(new Date()).save();
            return new SymResponseObject<>(SUCCESS, authSession);
        } else {
            logger.info(format("Authentication failed! Could not verify authToken %s for user %s", authToken, userId));
            return new SymResponseObject<>(AUTH_AUTHENTICATION_FAILED);
        }
    }

    @Override
    public SymSystemUserList registerMobileUser(String email, String msisdn, String username, String imei,
                                                String companyName, String firstName, String lastName, String pin) {

        logger.info(format("Performing registration for %s %s", firstName, lastName));

        if (!isNullOrEmpty(companyName) && !isValidPlainText(companyName)) {
            return new SymSystemUserList(INPUT_INVALID_NAME).setResponse(format("Company name '%s' is invalid", companyName));
        }

        msisdn = formatFullMsisdn(msisdn, countryFromString(getProperty("DefaultCountry")).getDialing_code());

        sym_user newUser = new sym_user(firstName, lastName,
                null, username, null, pin, 0, 0, null,
                email, msisdn, null, fromEnum(ACC_ACTIVE),
                countryFromString(getProperty("DefaultCountry")),
                languageFromString(getProperty("DefaultLanguage")));

        sym_request_response_log requestResponseLog = new sym_request_response_log(
                fromEnum(SMART_PHONE), fromEnum(USER_REGISTRATION), newUser.toPrintableString()).save();

        MobileAuthenticationProvider authenticationProvider = new MobileAuthenticationProvider(
            requestResponseLog, username, pin, imei
        );

        sym_company company = new sym_company();

        company.setCompany_name(companyName == null ? username : companyName);
        company.setAddress_country(findByName(sym_country.class, getProperty("DefaultCountry")));
        company.setPhone1(formatFullMsisdn(msisdn, company.getAddress_country().getDialing_code()));

        List<sym_company> existingCompanies = getEntityManagerRepo()
                .findWhere(sym_company.class, new Pair<>("company_name", company.getCompany_name()));

        if (existingCompanies != null && existingCompanies.size() > 0) {
            logResponse(null, requestResponseLog, EXISTING_DATA_FOUND);
            return new SymSystemUserList(EXISTING_DATA_FOUND).setResponse(format("Company with name '%s' already exists", companyName));
        }

        SymResponseObject<sym_auth_user> registrationResponse = authenticationProvider.
            registerMobileUser(newUser, imei, findByName(sym_auth_group.class, getProperty("DefaultMobileGroup")), company);

        logResponse(registrationResponse.getResponseObject(), requestResponseLog, registrationResponse.getResponseCode());
        return new SymSystemUserList(registrationResponse.getResponseCode(), converterService.toDTO(registrationResponse.getResponseObject()));
    }

    @Override
    public SymCashoutAccountList getCashoutAccounts(Long userId, String imei, String authToken) {

        logger.info("Getting cashout accounts for user " + userId);

        String incomingRequest = format("userId=%s|imei=%s", userId, imei);

        sym_request_response_log requestResponseLog = new sym_request_response_log(
                fromEnum(SMART_PHONE), fromEnum(WALLET_GET_CASHOUT_ACCOUNTS), incomingRequest).save();

        SymResponseObject<sym_session> authResponse = verifyLogin(userId, imei, authToken);
        if (!authResponse.getResponseCode().equals(SUCCESS)) {
            logResponse(null, requestResponseLog, authResponse.getResponseCode());
            return new SymCashoutAccountList(authResponse.getResponseCode());
        }

        sym_auth_user authUser = authResponse.getResponseObject().getAuth_user();

        ArrayList<SymCashoutAccount> cashoutAccounts = new ArrayList<>();
        getEntityManagerRepo()
            .findWhere(sym_cashout_account.class, asList(new Pair<>("sym_user_id", userId), new Pair<>("is_active", 1)))
            .forEach(ca -> cashoutAccounts.add(converterService.toDTO(ca)));
        logger.info(format("Returning %s cashout accounts for user %s", cashoutAccounts.size(), userId));
        logResponse(authUser, requestResponseLog, SUCCESS);
        return new SymCashoutAccountList(SUCCESS, cashoutAccounts);
    }

    @Override
    public SymResponse addCashoutAccount(Long userId, String imei, String authToken, Long institutionId,
                                         String accountNickName, String accountName, String accountNumber,
                                         String accountBranchCode, String accountPhone, String accountEmail) {

        logger.info(format("Adding cashout account %s for user %s", accountNickName, userId));

        String incomingRequest = format("userId=%s|imei=%s|institutionId=%s|accountNickName=%s|" +
            "accountName=%s|accountNumber=%s|accountBranchCode=%s|accountPhone=%s|accountEmail=%s",
            userId, imei, institutionId, accountNickName, accountName,
            accountNumber, accountBranchCode, accountPhone, accountEmail);

        sym_request_response_log requestResponseLog = new sym_request_response_log(
                fromEnum(SMART_PHONE), fromEnum(WALLET_ADD_CASHOUT_ACCOUNT), incomingRequest).save();

        SymResponseObject<sym_session> authResponse = verifyLogin(userId, imei, authToken);
        if (!authResponse.getResponseCode().equals(SUCCESS)) {
            logResponse(null, requestResponseLog, authResponse.getResponseCode());
            return new SymResponse(authResponse.getResponseCode());
        }

        sym_auth_user authUser = authResponse.getResponseObject().getAuth_user();

        sym_financial_institution financialInstitution = getEntityManagerRepo().findById(sym_financial_institution.class, institutionId);

        if (financialInstitution == null) {
            String outgoingResponse = format("financialInstitution %s does not exist", institutionId);
            logger.warning(outgoingResponse);
            logResponse(authUser, requestResponseLog, DATA_NOT_FOUND, outgoingResponse);
            return new SymResponse(DATA_NOT_FOUND).setResponse_message(outgoingResponse);
        }

        accountPhone = formatFullMsisdn(accountPhone, authUser.getUser().getCountry().getDialing_code());

        SymFinancialInstitutionType institutionType = SymFinancialInstitutionType.
                valueOf(financialInstitution.getInstitution_type().getName());

        if (isNullOrEmpty(accountNickName) || !isValidPlainText(accountNickName)) {
            String outgoingResponse = "a valid accountNickName must be specified";
            logger.warning(outgoingResponse);
            logResponse(authUser, requestResponseLog, INPUT_INVALID_REQUEST, outgoingResponse);
            return new SymResponse(INPUT_INVALID_REQUEST);
        } else if ((institutionType.equals(BANK) || !isNullOrEmpty(accountName)) && !isValidName(accountName)) {
            String outgoingResponse = "accountName is not valid";
            logger.warning(outgoingResponse);
            logResponse(authUser, requestResponseLog, INPUT_INVALID_NAME, outgoingResponse);
            return new SymResponse(INPUT_INVALID_NAME);
        } else if (isNullOrEmpty(accountNumber)) {
            String outgoingResponse = "a valid accountNumber must be specified";
            logger.warning(outgoingResponse);
            logResponse(authUser, requestResponseLog, INPUT_INVALID_REQUEST, outgoingResponse);
            return new SymResponse(INPUT_INVALID_REQUEST);
        } else if (!isNullOrEmpty(accountBranchCode) && !isNumeric(accountBranchCode)) {
            String outgoingResponse = "accountBranchCode is not valid";
            logger.warning(outgoingResponse);
            logResponse(authUser, requestResponseLog, INPUT_INVALID_REQUEST, outgoingResponse);
            return new SymResponse(INPUT_INVALID_REQUEST);
        } else if ((institutionType.equals(MOBILE_BANK) || !isNullOrEmpty(accountPhone)) && !isValidMsisdn(accountPhone)) {
            String outgoingResponse = "accountPhone is not valid";
            logger.warning(outgoingResponse);
            logResponse(authUser, requestResponseLog, INPUT_INVALID_MSISDN, outgoingResponse);
            return new SymResponse(INPUT_INVALID_MSISDN);
        } else if ((institutionType.equals(ONLINE_BANK) || !isNullOrEmpty(accountEmail)) && !isValidEmail(accountEmail)) {
            String outgoingResponse = "accountEmail is not valid";
            logger.warning(outgoingResponse);
            logResponse(authUser, requestResponseLog, INPUT_INVALID_EMAIL, outgoingResponse);
            return new SymResponse(INPUT_INVALID_EMAIL);
        }

        new sym_cashout_account(authUser.getUser(), financialInstitution, accountNickName, accountName, accountNumber,
                accountBranchCode, accountPhone, accountEmail, true).save();
        logger.info(format("Added new %s cashout account %s for user %s", institutionType.name(), accountNickName, userId));
        logResponse(authUser, requestResponseLog, SUCCESS);
        return new SymResponse(SUCCESS);
    }

    @Override
    public SymResponse removeCashoutAccounts(Long userId, String imei, String authToken, Long cashoutAccountId) {

        logger.info(format("Removing cashout account %s for user %s", cashoutAccountId, userId));

        String incomingRequest = format("userId=%s|imei=%s|cashoutAccountId=%s", userId, imei, cashoutAccountId);

        sym_request_response_log requestResponseLog = new sym_request_response_log(
                fromEnum(SMART_PHONE), fromEnum(WALLET_DISABLE_CASHOUT_ACCOUNT), incomingRequest).save();

        SymResponseObject<sym_session> authResponse = verifyLogin(userId, imei, authToken);
        if (!authResponse.getResponseCode().equals(SUCCESS)) {
            logResponse(null, requestResponseLog, authResponse.getResponseCode());
            return new SymResponse(authResponse.getResponseCode());
        }

        sym_auth_user authUser = authResponse.getResponseObject().getAuth_user();

        sym_cashout_account cashoutAccount = getEntityManagerRepo().findById(sym_cashout_account.class, cashoutAccountId);

        if (cashoutAccount == null) {
            String outgoingResponse = format("cashoutAccount %s does not exist", cashoutAccountId);
            logger.warning(outgoingResponse);
            logResponse(authUser, requestResponseLog, DATA_NOT_FOUND, outgoingResponse);
            return new SymResponse(DATA_NOT_FOUND);
        } else if (!cashoutAccount.getCashout_account_user().getId().equals(userId)) {
            String outgoingResponse = format("cashoutAccount %s is not linked to user %s", cashoutAccountId, userId);
            logger.warning(outgoingResponse);
            logResponse(authUser, requestResponseLog, DATA_NOT_FOUND, outgoingResponse);
            return new SymResponse(DATA_NOT_FOUND);
        }

        cashoutAccount.setIs_active(false);
        cashoutAccount.save();
        String outgoingResponse = format("Removed cashoutAccount %s for user %s", cashoutAccountId, userId);
        logger.info(outgoingResponse);
        logResponse(authUser, requestResponseLog, SUCCESS, outgoingResponse);
        return new SymResponse(SUCCESS);
    }

    @Override
    public SymVoucherPurchaseList buyVoucher(Long userId, String imei, String authToken, Long voucherId, BigDecimal voucherValue, String recipient) {

        logger.info(format("Got mobile voucher purchase request by user %s for voucherId %s (amount=%s, recipient=%s)",
                userId, voucherId, voucherValue == null ? "not specified" : voucherValue, recipient));

        String incomingRequest = format("userId=%s|imei=%s|voucherId=%s|voucherValue=%s|recipient=%s",
                userId, imei, voucherId, voucherValue, recipient);

        sym_request_response_log requestResponseLog = new sym_request_response_log(fromEnum(SMART_PHONE), fromEnum(VOUCHER_PURCHASE), incomingRequest);

        SymResponseObject<sym_session> authResponse = verifyLogin(userId, imei, authToken);
        if (!authResponse.getResponseCode().equals(SUCCESS)) {
            logResponse(null, requestResponseLog, authResponse.getResponseCode());
            return new SymVoucherPurchaseList(authResponse.getResponseCode());
        }

        sym_auth_user authUser = authResponse.getResponseObject().getAuth_user();

        logger.info(format("Using company %s wallet", authUser.getUser().getWallet().getCompany().getCompany_name()));

        SymVoucherPurchaseList purchaseResponse = voucherProcessor.buyVoucher(voucherId, authUser.getId(), voucherValue, recipient, authUser.getUser().getUsername());

        logResponse(authUser, requestResponseLog, purchaseResponse.getSymResponse().getResponse());

        return purchaseResponse;
    }

    @Override
    public SymWalletList swipeTransaction(Long userId, String imei, String authToken, BigDecimal amount, String reference, String cardNumber, String cardPin) {

        logger.info(format("Got mobile swipe transaction from user %s for amount %s (reference=%s)",
                userId, amount, reference == null ? "not specified" : reference));

        String incomingRequest = format("userId=%s|imei=%s|amount=%s|reference=%s|cardNumber=%s",
                userId, imei, amount, reference, cardNumber);

        sym_request_response_log requestResponseLog = new sym_request_response_log(fromEnum(SMART_PHONE), fromEnum(WALLET_SWIPE_IN), incomingRequest);

        SymResponseObject<sym_session> authResponse = verifyLogin(userId, imei, authToken);
        if (!authResponse.getResponseCode().equals(SUCCESS)) {
            logResponse(null, requestResponseLog, authResponse.getResponseCode());
            return new SymWalletList(authResponse.getResponseCode());
        }

        sym_auth_user authUser = authResponse.getResponseObject().getAuth_user();

        SymResponseObject validationResponse = ValidationHelper.validateAmount(amount);
        if (!validationResponse.getResponseCode().equals(SUCCESS)) {
            logResponse(authUser, requestResponseLog, validationResponse.getResponseCode());
            return new SymWalletList(validationResponse.getResponseCode());
        }
        if (!isNullOrEmpty(reference) && !isValidPlainText(reference)) {
            logResponse(authUser, requestResponseLog, INPUT_INVALID_REQUEST, "Transaction reference is invalid");
            return new SymWalletList(INPUT_INVALID_REQUEST).setResponse("Transaction reference is invalid");
        }
        if (!isValidCardNumber(valueOf(cardNumber))) {
            logResponse(authUser, requestResponseLog, INPUT_INVALID_REQUEST, "Card number is invalid");
            return new SymWalletList(INPUT_INVALID_REQUEST).setResponse("Card number is invalid");
        }
        if (!isNullOrEmpty(valueOf(cardPin)) && !isValidCardPin(valueOf(cardPin))) {
            logResponse(authUser, requestResponseLog, INPUT_INVALID_REQUEST, "Card pin is invalid");
            return new SymWalletList(INPUT_INVALID_PASSWORD).setResponse("Card pin is invalid");
        }

        sym_wallet wallet = authUser.getUser().getWallet();

        sym_swipe_transaction swipeTransaction = new sym_swipe_transaction(authUser, reference,
            null, cardNumber, amount, wallet.getCurrent_balance(),
            null, new Date(), null).save();

        SymResponseObject<sym_wallet> updateResponse = walletManager.updateWalletBalance(wallet, amount);

        //TODO execute swipe transaction


        swipeTransaction.setNew_balance(wallet.getCurrent_balance());
        swipeTransaction.setTransaction_status(fromEnum(updateResponse.getResponseCode()));
        swipeTransaction.save();

        logResponse(authUser, requestResponseLog, SUCCESS);
        return new SymWalletList(updateResponse.getResponseCode(), converterService.toDTO(wallet));
    }

    @Override
    public SymWalletList cashoutTransaction(Long userId, String imei, String authToken, BigDecimal amount, String reference, Long cashoutAccountId, String pin) {

        logger.info(format("Got mobile cashout transaction from user %s for amount %s (reference=%s)",
                userId, amount, reference == null ? "not specified" : reference));

        String incomingRequest = format("userId=%s|imei=%s|amount=%s|reference=%s|cashoutAccountId=%s",
                userId, imei, amount, reference, cashoutAccountId);

        sym_request_response_log requestResponseLog = new sym_request_response_log(fromEnum(SMART_PHONE), fromEnum(WALLET_CASHOUT), incomingRequest);

        SymResponseObject<sym_session> authResponse = verifyLogin(userId, imei, authToken);
        if (!authResponse.getResponseCode().equals(SUCCESS)) {
            logResponse(null, requestResponseLog, authResponse.getResponseCode());
            return new SymWalletList(authResponse.getResponseCode());
        }

        sym_auth_user authUser = authResponse.getResponseObject().getAuth_user();

        SymResponseObject validationResponse = ValidationHelper.validateAmount(amount);
        if (!validationResponse.getResponseCode().equals(SUCCESS)) {
            logResponse(authUser, requestResponseLog, validationResponse.getResponseCode());
            return new SymWalletList(validationResponse.getResponseCode());
        }
        if (!isNullOrEmpty(reference) && !isValidPlainText(reference)) {
            logResponse(authUser, requestResponseLog, INPUT_INVALID_REQUEST, "Transaction reference is invalid");
            return new SymWalletList(INPUT_INVALID_REQUEST).setResponse("Transaction reference is invalid");
        }
        SymResponseObject<sym_cashout_account> cashoutAccountResponse = ValidationHelper.validateCashoutAccount(cashoutAccountId);
        if (!cashoutAccountResponse.getResponseCode().equals(SUCCESS)) {
            logResponse(authUser, requestResponseLog, cashoutAccountResponse.getResponseCode());
            return new SymWalletList(cashoutAccountResponse.getResponseCode());
        }
        if (!isValidPin(pin)) {
            logResponse(authUser, requestResponseLog, INPUT_INVALID_PASSWORD, "Card pin is invalid");
            return new SymWalletList(INPUT_INVALID_PASSWORD).setResponse("Card pin is invalid");
        }

        sym_wallet wallet = authUser.getUser().getWallet();

        sym_cashout_transaction cashoutTransaction = new sym_cashout_transaction(authUser, reference,
                cashoutAccountResponse.getResponseObject(), amount, wallet.getCurrent_balance(),
                null, new Date(), null).save();

        MobileAuthenticationProvider authProvider = new MobileAuthenticationProvider(
                requestResponseLog, authUser.getUser().getUsername(), pin, imei
        );

        SymResponseObject<sym_auth_user> pinResponse = authProvider.validatePin(authUser, pin);
        if (!pinResponse.getResponseCode().equals(SUCCESS)) {
            cashoutTransaction.setTransaction_status(fromEnum(pinResponse.getResponseCode()));
            cashoutTransaction.save();
            logResponse(authUser, requestResponseLog, pinResponse.getResponseCode());
            return new SymWalletList(pinResponse.getResponseCode());
        }

        SymResponseObject<sym_wallet> updateResponse = walletManager.updateWalletBalance(wallet, amount.multiply(new BigDecimal(-1)));

        //TODO execute cashout transaction

        cashoutTransaction.setNew_balance(wallet.getCurrent_balance());
        cashoutTransaction.setTransaction_status(fromEnum(updateResponse.getResponseCode()));
        cashoutTransaction.save();

        logResponse(authUser, requestResponseLog, updateResponse.getResponseCode());
        return new SymWalletList(updateResponse.getResponseCode(), converterService.toDTO(wallet));
    }
}
