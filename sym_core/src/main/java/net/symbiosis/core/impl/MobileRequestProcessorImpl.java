package net.symbiosis.core.implementation;

import net.symbiosis.authentication.authentication.MobileAuthenticationProvider;
import net.symbiosis.common.structure.Pair;
import net.symbiosis.core.contract.SymCashoutAccountList;
import net.symbiosis.core.contract.SymResponse;
import net.symbiosis.core.contract.SymSystemUserList;
import net.symbiosis.core.contract.SymWalletList;
import net.symbiosis.core.contract.symbiosis.SymCashoutAccount;
import net.symbiosis.core.helper.ValidationHelper;
import net.symbiosis.core.service.ConverterService;
import net.symbiosis.core.service.MobileRequestProcessor;
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
    private final ConverterService converterService;
    private final WalletManager walletManager;

    @Autowired
    public MobileRequestProcessorImpl(ConverterService converterService, WalletManager walletManager) {
        this.converterService = converterService;
        this.walletManager = walletManager;
    }

    @Override
    public SymSystemUserList registerMobileUser(String email, String msisdn, String username, String deviceId,
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
                fromEnum(SMART_PHONE), fromEnum(REGISTRATION), newUser.toPrintableString()).save();

        MobileAuthenticationProvider authenticationProvider = new MobileAuthenticationProvider(
                requestResponseLog, username, pin, deviceId
        );

        sym_company company = new sym_company();

        company.setCompany_name(companyName == null ? username : companyName);
        company.setAddress_country(findByName(sym_country.class, getProperty("DefaultCountry")));
        company.setPhone1(formatFullMsisdn(msisdn, company.getAddress_country().getDialing_code()));

        List<sym_company> existingCompanies = getEntityManagerRepo()
                .findWhere(sym_company.class, new Pair<>("company_name", company.getCompany_name()));

        if (existingCompanies != null && existingCompanies.size() > 0) {
            logResponse(requestResponseLog, EXISTING_DATA_FOUND);
            return new SymSystemUserList(EXISTING_DATA_FOUND).setResponse(format("Company with name '%s' already exists", companyName));
        }

        SymResponseObject<sym_auth_user> registrationResponse = authenticationProvider.
            registerMobileUser(newUser, findByName(sym_auth_group.class, getProperty("DefaultMobileGroup")), company);

        if (registrationResponse.getResponseCode().equals(SUCCESS)) {
            requestResponseLog.setAuth_user(registrationResponse.getResponseObject());
            requestResponseLog.setSystem_user(registrationResponse.getResponseObject().getUser());
        }

        logResponse(requestResponseLog, registrationResponse.getResponseCode());
        return new SymSystemUserList(registrationResponse.getResponseCode(), converterService.toDTO(registrationResponse.getResponseObject()));
    }

    @Override
    public SymCashoutAccountList getCashoutAccounts(Long userId) {
        logger.info("Getting cashout accounts for user " + userId);
        ArrayList<SymCashoutAccount> cashoutAccounts = new ArrayList<>();
        getEntityManagerRepo()
                .findWhere(sym_cashout_account.class, new Pair<>("sym_user_id", userId))
                .forEach(ca -> cashoutAccounts.add(converterService.toDTO(ca)));
        logger.info(format("Returning %s cashout accounts for user %s", cashoutAccounts.size(), userId));
        return new SymCashoutAccountList(SUCCESS, cashoutAccounts);
    }

    @Override
    public SymResponse addCashoutAccount(Long userId, Long institutionId, String accountNickName, String accountName,
                                         String accountNumber, String accountBranchCode, String accountPhone,
                                         String accountEmail) {

        logger.info(format("Adding cashout account %s for user %s", accountNickName, userId));
        sym_user user = getEntityManagerRepo().findById(sym_user.class, userId);

        if (user == null) {
            logger.warning(format("userId %s does not exist", userId));
            return new SymResponse(DATA_NOT_FOUND);
        }

        String request = format("userId=%s,institutionId=%s,accountNickName=%s,accountName=%s," +
                        "accountNumber%s,accountBranchCode=%s,accountPhone=%s,accountEmail=%s",
                userId, institutionId, accountNickName, accountName, accountNumber,
                accountBranchCode, accountPhone, accountEmail);

        sym_request_response_log requestResponseLog = new sym_request_response_log(
                fromEnum(SMART_PHONE), fromEnum(UPDATE_USER), request).save();

        sym_financial_institution financialInstitution = getEntityManagerRepo().findById(sym_financial_institution.class, institutionId);

        if (financialInstitution == null) {
            String outgoingResponse = format("financialInstitution %s does not exist", institutionId);
            logger.warning(outgoingResponse);
            logResponse(requestResponseLog, DATA_NOT_FOUND, outgoingResponse);
            return new SymResponse(DATA_NOT_FOUND).setResponse_message(outgoingResponse);
        }

        accountPhone = formatFullMsisdn(accountPhone, user.getCountry().getDialing_code());

        SymFinancialInstitutionType institutionType = SymFinancialInstitutionType.
                valueOf(financialInstitution.getInstitution_type().getName());

        if (isNullOrEmpty(accountNickName) || !isValidPlainText(accountNickName)) {
            String outgoingResponse = "a valid accountNickName must be specified";
            logger.warning(outgoingResponse);
            logResponse(requestResponseLog, INPUT_INVALID_REQUEST, outgoingResponse);
            return new SymResponse(INPUT_INVALID_REQUEST);
        } else if ((institutionType.equals(BANK) || !isNullOrEmpty(accountName)) && !isValidName(accountName)) {
            String outgoingResponse = "accountName is not valid";
            logger.warning(outgoingResponse);
            logResponse(requestResponseLog, INPUT_INVALID_NAME, outgoingResponse);
            return new SymResponse(INPUT_INVALID_NAME);
        } else if (isNullOrEmpty(accountNumber)) {
            String outgoingResponse = "a valid accountNumber must be specified";
            logger.warning(outgoingResponse);
            logResponse(requestResponseLog, INPUT_INVALID_REQUEST, outgoingResponse);
            return new SymResponse(INPUT_INVALID_REQUEST);
        } else if (!isNullOrEmpty(accountBranchCode) && !isNumeric(accountBranchCode)) {
            String outgoingResponse = "accountBranchCode is not valid";
            logger.warning(outgoingResponse);
            logResponse(requestResponseLog, INPUT_INVALID_REQUEST, outgoingResponse);
            return new SymResponse(INPUT_INVALID_REQUEST);
        } else if ((institutionType.equals(MOBILE_BANK) || !isNullOrEmpty(accountPhone)) && !isValidMsisdn(accountPhone)) {
            String outgoingResponse = "accountPhone is not valid";
            logger.warning(outgoingResponse);
            logResponse(requestResponseLog, INPUT_INVALID_MSISDN, outgoingResponse);
            return new SymResponse(INPUT_INVALID_MSISDN);
        } else if ((institutionType.equals(ONLINE_BANK) || !isNullOrEmpty(accountEmail)) && !isValidEmail(accountEmail)) {
            String outgoingResponse = "accountEmail is not valid";
            logger.warning(outgoingResponse);
            logResponse(requestResponseLog, INPUT_INVALID_EMAIL, outgoingResponse);
            return new SymResponse(INPUT_INVALID_EMAIL);
        }

        new sym_cashout_account(user, financialInstitution, accountNickName, accountName, accountNumber,
                accountBranchCode, accountPhone, accountEmail).save();
        logger.info(format("Added new %s cashout account %s for user %s", institutionType.name(), accountNickName, userId));
        logResponse(requestResponseLog, SUCCESS);
        return new SymResponse(SUCCESS);
    }

    @Override
    public SymResponse removeCashoutAccounts(Long userId, Long cashoutAccountId) {
        logger.info(format("Removing cashout account %s for user %s", cashoutAccountId, userId));

        sym_user user = getEntityManagerRepo().findById(sym_user.class, userId);

        if (user == null) {
            logger.warning(format("userId %s does not exist", userId));
            return new SymResponse(DATA_NOT_FOUND);
        }

        String request = format("userId=%s,cashoutAccountId=%s", userId, cashoutAccountId);

        sym_request_response_log requestResponseLog = new sym_request_response_log(
                fromEnum(SMART_PHONE), fromEnum(UPDATE_USER), request).save();

        sym_cashout_account cashoutAccount = getEntityManagerRepo().findById(sym_cashout_account.class, cashoutAccountId);

        if (cashoutAccount == null) {
            String outgoingResponse = format("cashoutAccount %s does not exist", cashoutAccountId);
            logger.warning(outgoingResponse);
            logResponse(requestResponseLog, DATA_NOT_FOUND, outgoingResponse);
            return new SymResponse(DATA_NOT_FOUND);
        } else if (!cashoutAccount.getCashout_account_user().getId().equals(userId)) {
            String outgoingResponse = format("cashoutAccount %s is not linked to user %s", cashoutAccountId, userId);
            logger.warning(outgoingResponse);
            logResponse(requestResponseLog, DATA_NOT_FOUND, outgoingResponse);
            return new SymResponse(DATA_NOT_FOUND);
        }

        cashoutAccount.delete();
        String outgoingResponse = format("Disabled cashoutAccount %s for user %s", cashoutAccountId, userId);
        logger.info(outgoingResponse);
        logResponse(requestResponseLog, SUCCESS, outgoingResponse);
        return new SymResponse(SUCCESS);
    }

    @Override
    public synchronized SymWalletList swipeTransaction(Long userId, String deviceId, BigDecimal amount,
                                                       String reference, String cardNumber, String cardPin) {

        SymResponseObject<sym_user> userResponse = ValidationHelper.validateSystemUser(userId);
        if (!userResponse.getResponseCode().equals(SUCCESS)) {
            return new SymWalletList(userResponse.getResponseCode());
        }
        SymResponseObject validationResponse = ValidationHelper.validateAmount(amount);
        if (!validationResponse.getResponseCode().equals(SUCCESS)) {
            return new SymWalletList(validationResponse.getResponseCode());
        }
        if (!isNullOrEmpty(reference) && !isValidPlainText(reference)) {
            return new SymWalletList(INPUT_INVALID_REQUEST).setResponse("Transaction reference is invalid");
        }
        if (!isValidCardNumber(valueOf(cardNumber))) {
            return new SymWalletList(INPUT_INVALID_REQUEST).setResponse(
                    format("cardNumber '%s' is not valid", cardNumber));
        }
        if (!isNullOrEmpty(valueOf(cardPin)) && !isValidCardPin(valueOf(cardPin))) {
            return new SymWalletList(INPUT_INVALID_PASSWORD).setResponse("cardPin is not valid");
        }
        List<sym_auth_user> authUsers = getEntityManagerRepo().findWhere(sym_auth_user.class,
                asList(
                        new Pair<>("sym_user_id", userId),
                        new Pair<>("channel_id", fromEnum(SMART_PHONE).getId())
                )
        );

        if (authUsers == null || authUsers.size() == 0) {
            return new SymWalletList(DATA_NOT_FOUND).setResponse(format("userId '%s' not found", userId));
        }

        sym_wallet wallet = authUsers.get(0).getUser().getWallet();

        String request = format("userId=%s,deviceId=%s,transactionAmount=%s,transactionReference=%s,cashoutAccountId=%s,pin=XXXXX",
                userId, deviceId, amount, reference, cardNumber);

        sym_request_response_log requestResponseLog = new sym_request_response_log(fromEnum(SMART_PHONE), fromEnum(LOAD_WALLET),
                userResponse.getResponseObject(), authUsers.get(0), null,
                new Date(), null, request, null).save();

        sym_swipe_transaction swipeTransaction = new sym_swipe_transaction(authUsers.get(0), reference,
                null, cardNumber, amount, wallet.getCurrent_balance(),
                null, new Date(), null).save();

        SymResponseObject<sym_wallet> updateResponse = walletManager.updateWalletBalance(wallet, amount);

        //TODO execute swipe transaction


        swipeTransaction.setNew_balance(wallet.getCurrent_balance());
        swipeTransaction.setTransaction_status(fromEnum(updateResponse.getResponseCode()));
        swipeTransaction.save();

        logResponse(requestResponseLog, SUCCESS);
        return new SymWalletList(updateResponse.getResponseCode(), converterService.toDTO(wallet));
    }

    @Override
    public synchronized SymWalletList cashoutTransaction(Long userId, String deviceId, BigDecimal amount,
                                                         String reference, Long cashoutAccountId, String pin) {

        SymResponseObject<sym_user> userResponse = ValidationHelper.validateSystemUser(userId);
        if (!userResponse.getResponseCode().equals(SUCCESS)) {
            return new SymWalletList(userResponse.getResponseCode());
        }
        SymResponseObject validationResponse = ValidationHelper.validateAmount(amount);
        if (!validationResponse.getResponseCode().equals(SUCCESS)) {
            return new SymWalletList(validationResponse.getResponseCode());
        }
        if (!isNullOrEmpty(reference) && !isValidPlainText(reference)) {
            return new SymWalletList(INPUT_INVALID_REQUEST).setResponse("Transaction reference is invalid");
        }
        SymResponseObject<sym_cashout_account> cashoutAccountResponse = ValidationHelper.validateCashoutAccount(cashoutAccountId);
        if (!cashoutAccountResponse.getResponseCode().equals(SUCCESS)) {
            return new SymWalletList(cashoutAccountResponse.getResponseCode());
        }
        if (!isValidPin(pin)) {
            return new SymWalletList(INPUT_INVALID_PASSWORD).setResponse("Pin is not valid");
        }
        List<sym_auth_user> authUsers = getEntityManagerRepo().findWhere(sym_auth_user.class,
                asList(
                        new Pair<>("sym_user_id", userId),
                        new Pair<>("channel_id", fromEnum(SMART_PHONE).getId())
                )
        );

        if (authUsers == null || authUsers.size() == 0) {
            return new SymWalletList(DATA_NOT_FOUND).setResponse(format("userId '%s' not found", userId));
        }

        sym_wallet wallet = authUsers.get(0).getUser().getWallet();

        sym_cashout_transaction cashoutTransaction = new sym_cashout_transaction(authUsers.get(0), reference,
                cashoutAccountResponse.getResponseObject(), amount, wallet.getCurrent_balance(),
                null, new Date(), null).save();

        String request = format("userId=%s,deviceId=%s,amount=%s,reference=%s,cashoutAccountId=%s,pin=XXXXX",
                userId, deviceId, amount, reference, cashoutAccountId);

        sym_request_response_log requestResponseLog = new sym_request_response_log(fromEnum(SMART_PHONE), fromEnum(CASHOUT),
                userResponse.getResponseObject(), authUsers.get(0), null,
                new Date(), null, request, null).save();

        MobileAuthenticationProvider authProvider = new MobileAuthenticationProvider(
                requestResponseLog, userResponse.getResponseObject().getUsername(), pin, deviceId
        );

        SymResponseObject<sym_auth_user> authResponse = authProvider.validatePin(authUsers.get(0), pin);
        if (!authResponse.getResponseCode().equals(SUCCESS)) {
            cashoutTransaction.setTransaction_status(fromEnum(authResponse.getResponseCode()));
            cashoutTransaction.save();
            logResponse(requestResponseLog, authResponse.getResponseCode());
            return new SymWalletList(authResponse.getResponseCode());
        }

        SymResponseObject<sym_wallet> updateResponse = walletManager.updateWalletBalance(wallet, amount.multiply(new BigDecimal(-1)));

        //TODO execute cashout transaction

        cashoutTransaction.setNew_balance(wallet.getCurrent_balance());
        cashoutTransaction.setTransaction_status(fromEnum(updateResponse.getResponseCode()));
        cashoutTransaction.save();

        logResponse(requestResponseLog, updateResponse.getResponseCode());
        return new SymWalletList(updateResponse.getResponseCode(), converterService.toDTO(wallet));
    }

    @Override
    public SymSystemUserList startSession(String deviceId, String username, String pin, SymChannel channel) {
        logger.info(format("Performing login for %s", username));

        String request = format("deviceId:%s|username:%s|pin:%s|channel:%s", deviceId, username, pin, channel.name());

        sym_request_response_log requestResponseLog = new sym_request_response_log(
                fromEnum(SMART_PHONE), fromEnum(LOGIN), request).save();

        MobileAuthenticationProvider authenticationProvider = new MobileAuthenticationProvider(
                requestResponseLog, username, pin, deviceId
        );

        SymResponseObject<sym_auth_user> authResponse = authenticationProvider.authenticateUser();

        if (authResponse.getResponseCode().equals(SUCCESS)) {
            requestResponseLog.setAuth_user(authResponse.getResponseObject());
            requestResponseLog.setSystem_user(authResponse.getResponseObject().getUser());
        }

        logResponse(requestResponseLog, authResponse.getResponseCode());
        return new SymSystemUserList(authResponse.getResponseCode(), converterService.toDTO(authResponse.getResponseObject()));
    }

    @Override
    public SymResponse endSession(Long sessionId, String deviceId, String authToken, SymChannel channel) {

        logger.info(format("Performing logout for session %s", sessionId));

        String request = format("sessionId:%s|deviceId:%s|authToken:%s|channel:%s", sessionId, deviceId, authToken, channel.name());
        sym_request_response_log requestResponseLog = new sym_request_response_log(
                fromEnum(SMART_PHONE), fromEnum(LOGOUT), request).save();

        sym_session currentSession = getEntityManagerRepo().findById(sym_session.class, sessionId);

        SymResponseObject logoutResponse;

        if (currentSession == null) {
            logoutResponse = new SymResponseObject(DATA_NOT_FOUND);
        } else {
            MobileAuthenticationProvider authenticationProvider = new MobileAuthenticationProvider(
                    requestResponseLog, currentSession.getAuth_user()
            );
            requestResponseLog.setAuth_user(currentSession.getAuth_user());
            requestResponseLog.setSystem_user(currentSession.getAuth_user().getUser());
            requestResponseLog.save();

            authenticationProvider.setSession(currentSession);
            authenticationProvider.setDeviceId(deviceId);
            authenticationProvider.setAuthToken(authToken);

            logoutResponse = authenticationProvider.endSession();
        }

        logResponse(requestResponseLog, logoutResponse.getResponseCode());
        return new SymResponse(logoutResponse.getResponseCode());
    }
}
