package net.beyondtelecom.gopay.bt_core.implementation;

import net.beyondtelecom.bt_core_lib.enumeration.BTChannel;
import net.beyondtelecom.bt_core_lib.enumeration.BTFinancialInstitutionType;
import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.bt_core_lib.response.BTResponseObject;
import net.beyondtelecom.gopay.bt_authentication.authentication.GoPayAuthenticationProvider;
import net.beyondtelecom.gopay.bt_common.structure.Pair;
import net.beyondtelecom.gopay.bt_core.contract.BTCashoutAccountList;
import net.beyondtelecom.gopay.bt_core.contract.BTResponse;
import net.beyondtelecom.gopay.bt_core.contract.BTSystemUserList;
import net.beyondtelecom.gopay.bt_core.contract.BTWalletList;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTCashoutAccount;
import net.beyondtelecom.gopay.bt_core.service.ConverterService;
import net.beyondtelecom.gopay.bt_core.service.GoPayRequestProcessor;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.*;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_cashout_transaction;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_request_response_log;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_session;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_swipe_transaction;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_auth_group;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_country;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_financial_institution;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.lang.String.valueOf;
import static java.util.Arrays.asList;
import static net.beyondtelecom.bt_core_lib.enumeration.BTChannel.SMART_PHONE;
import static net.beyondtelecom.bt_core_lib.enumeration.BTEventType.*;
import static net.beyondtelecom.bt_core_lib.enumeration.BTFinancialInstitutionType.*;
import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.*;
import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.*;
import static net.beyondtelecom.bt_core_lib.utilities.CommonUtilities.formatFullMsisdn;
import static net.beyondtelecom.gopay.bt_common.configuration.Configuration.getProperty;
import static net.beyondtelecom.gopay.bt_core.WalletMan.updateWalletBalance;
import static net.beyondtelecom.gopay.bt_core.helper.ValidationHelper.*;
import static net.beyondtelecom.gopay.bt_persistence.dao.EnumEntityRepoManager.findByName;
import static net.beyondtelecom.gopay.bt_persistence.helper.BTEnumHelper.*;
import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 *																		   *
 * Created:	 20 / 10 / 2016												   *
 * Author:	  Tsungai Kaviya											   *
 * Contact:	 tsungai.kaviya@gmail.com									   *
 * 																		   *
 ***************************************************************************/

public class GoPayRequestProcessorImpl implements GoPayRequestProcessor {

    private final ConverterService converterService;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Autowired
    public GoPayRequestProcessorImpl(ConverterService converterService) {
        this.converterService = converterService;
    }

    @Override
    public BTSystemUserList registerGoPayUser(String email, String msisdn, String username, String deviceId,
                                              String companyName, String firstName, String lastName, String pin) {

        logger.info(format("Performing registration for %s %s", firstName, lastName));

        if (!isNullOrEmpty(companyName) && !isValidPlainText(companyName)) {
            return new BTSystemUserList(INPUT_INVALID_NAME).setResponse(format("Company name '%s' is invalid", companyName));
        }

        msisdn = formatFullMsisdn(msisdn, countryFromString(getProperty("DefaultCountry")).getDialing_code());

        bt_user newUser = new bt_user(firstName, lastName,
                null, username, null, pin,0, 0, null,
                email, msisdn, null, fromEnum(ACC_ACTIVE),
                countryFromString(getProperty("DefaultCountry")),
                languageFromString(getProperty("DefaultLanguage")));

        bt_request_response_log requestResponseLog = new bt_request_response_log(
                fromEnum(SMART_PHONE), fromEnum(REGISTRATION), newUser.toPrintableString()).save();

        GoPayAuthenticationProvider authenticationProvider = new GoPayAuthenticationProvider(
                requestResponseLog, username, pin, deviceId
        );

        bt_company company = new bt_company();

        company.setCompany_name(companyName == null ? username : companyName);
        company.setAddress_country(findByName(bt_country.class, getProperty("DefaultCountry")));
        company.setPhone1(formatFullMsisdn(msisdn, company.getAddress_country().getDialing_code()));

        List<bt_company> existingCompanies = getEntityManagerRepo()
                .findWhere(bt_company.class, new Pair<>("company_name", company.getCompany_name()));

        if (existingCompanies != null && existingCompanies.size() > 0) {
            return new BTSystemUserList(EXISTING_DATA_FOUND).setResponse(format("Company with name '%s' already exists", companyName));
        }

        BTResponseObject<bt_auth_user> registrationResponse = authenticationProvider.
            registerMobileUser(newUser, findByName(bt_auth_group.class, getProperty("DefaultMobileGroup")), company);

        requestResponseLog.setOutgoing_response(registrationResponse.getMessage());
        requestResponseLog.setOutgoing_response_time(new Date());
        requestResponseLog.setResponse_code(fromEnum(registrationResponse.getResponseCode()));
        if (registrationResponse.getResponseCode().equals(SUCCESS)) {
            requestResponseLog.setAuth_user(registrationResponse.getResponseObject());
            requestResponseLog.setSystem_user(registrationResponse.getResponseObject().getUser());
        }
        requestResponseLog.save();

        return new BTSystemUserList(registrationResponse.getResponseCode(), converterService.toDTO(registrationResponse.getResponseObject()));
    }

    @Override
    public BTCashoutAccountList getCashoutAccounts(Long userId) {
        logger.info("Getting cashout accounts for user " + userId);
        ArrayList<BTCashoutAccount> cashoutAccounts = new ArrayList<>();
        getEntityManagerRepo()
            .findWhere(bt_cashout_account.class, new Pair<>("bt_user_id", userId))
            .forEach(ca -> cashoutAccounts.add(converterService.toDTO(ca)));
        logger.info(format("Returning %s cashout accounts for user %s", cashoutAccounts.size(), userId));
        return new BTCashoutAccountList(SUCCESS, cashoutAccounts);
    }

    @Override
    public BTResponse addCashoutAccount(Long userId, Long institutionId, String accountNickName, String accountName,
                                        String accountNumber, String accountBranchCode, String accountPhone,
                                        String accountEmail) {

        logger.info(format("Adding cashout account %s for user %s", accountNickName, userId));
        bt_user user = getEntityManagerRepo().findById(bt_user.class, userId);

        if (user == null) {
            logger.warning(format("userId %s does not exist", userId));
            return new BTResponse(DATA_NOT_FOUND);
        }

        String request = format("userId=%s,institutionId=%s,accountNickName=%s,accountName=%s," +
                                "accountNumber%s,accountBranchCode=%s,accountPhone=%s,accountEmail=%s",
                                userId, institutionId, accountNickName, accountName, accountNumber,
                                accountBranchCode, accountPhone, accountEmail);

        bt_request_response_log requestResponseLog = new bt_request_response_log(
                fromEnum(SMART_PHONE), fromEnum(UPDATE_USER), request).save();

        bt_financial_institution financialInstitution = getEntityManagerRepo().findById(bt_financial_institution.class, institutionId);

        if (financialInstitution == null) {
            String outgoingResponse = format("financialInstitution %s does not exist", institutionId);
            logger.warning(outgoingResponse);
            requestResponseLog.setResponse_code(fromEnum(DATA_NOT_FOUND));
            requestResponseLog.setOutgoing_response(outgoingResponse).setOutgoing_response_time(new Date()).save();
            return new BTResponse(DATA_NOT_FOUND);
        }

        accountPhone = formatFullMsisdn(accountPhone, user.getCountry().getDialing_code());

        BTFinancialInstitutionType institutionType = BTFinancialInstitutionType.
                valueOf(financialInstitution.getInstitution_type().getName());

        if (isNullOrEmpty(accountNickName) || !isValidPlainText(accountNickName)) {
            String outgoingResponse = "a valid accountNickName must be specified";
            logger.warning(outgoingResponse);
            requestResponseLog.setResponse_code(fromEnum(INPUT_INVALID_REQUEST));
            requestResponseLog.setOutgoing_response(outgoingResponse).setOutgoing_response_time(new Date()).save();
            return new BTResponse(INPUT_INVALID_REQUEST);
        } else if ((institutionType.equals(BANK) || !isNullOrEmpty(accountName)) && !isValidName(accountName)) {
            String outgoingResponse = "accountName is not valid";
            logger.warning(outgoingResponse);
            requestResponseLog.setResponse_code(fromEnum(INPUT_INVALID_NAME));
            requestResponseLog.setOutgoing_response(outgoingResponse).setOutgoing_response_time(new Date()).save();
            return new BTResponse(INPUT_INVALID_NAME);
        } else if (isNullOrEmpty(accountNumber)) {
            String outgoingResponse = "a valid accountNumber must be specified";
            logger.warning(outgoingResponse);
            requestResponseLog.setResponse_code(fromEnum(INPUT_INVALID_REQUEST));
            requestResponseLog.setOutgoing_response(outgoingResponse).setOutgoing_response_time(new Date()).save();
            return new BTResponse(INPUT_INVALID_REQUEST);
        } else if (!isNullOrEmpty(accountBranchCode) && !isNumeric(accountBranchCode)) {
            String outgoingResponse = "accountBranchCode is not valid";
            logger.warning(outgoingResponse);
            requestResponseLog.setResponse_code(fromEnum(INPUT_INVALID_REQUEST));
            requestResponseLog.setOutgoing_response(outgoingResponse).setOutgoing_response_time(new Date()).save();
            return new BTResponse(INPUT_INVALID_REQUEST);
        } else if ((institutionType.equals(MOBILE_BANK) || !isNullOrEmpty(accountPhone)) && !isValidMsisdn(accountPhone)) {
            String outgoingResponse = "accountPhone is not valid";
            logger.warning(outgoingResponse);
            requestResponseLog.setResponse_code(fromEnum(INPUT_INVALID_MSISDN));
            requestResponseLog.setOutgoing_response(outgoingResponse).setOutgoing_response_time(new Date()).save();
            return new BTResponse(INPUT_INVALID_MSISDN);
        } else if ((institutionType.equals(ONLINE_BANK) || !isNullOrEmpty(accountEmail)) && !isValidEmail(accountEmail)) {
            String outgoingResponse = "accountEmail is not valid";
            logger.warning(outgoingResponse);
            requestResponseLog.setResponse_code(fromEnum(INPUT_INVALID_EMAIL));
            requestResponseLog.setOutgoing_response(outgoingResponse).setOutgoing_response_time(new Date()).save();
            return new BTResponse(INPUT_INVALID_EMAIL);
        }

        new bt_cashout_account(user, financialInstitution, accountNickName, accountName, accountNumber,
                               accountBranchCode, accountPhone, accountEmail).save();
        logger.info(format("Added new %s cashout account %s for user %s", institutionType.name(), accountNickName, userId));
        requestResponseLog.setResponse_code(fromEnum(SUCCESS));
        requestResponseLog.setOutgoing_response(SUCCESS.getMessage()).setOutgoing_response_time(new Date()).save();
        return new BTResponse(SUCCESS);
    }

    @Override
    public BTResponse removeCashoutAccounts(Long userId, Long cashoutAccountId) {
        logger.info(format("Removing cashout account %s for user %s", cashoutAccountId, userId));

        bt_user user = getEntityManagerRepo().findById(bt_user.class, userId);

        if (user == null) {
            logger.warning(format("userId %s does not exist", userId));
            return new BTResponse(DATA_NOT_FOUND);
        }

        String request = format("userId=%s,cashoutAccountId=%s", userId, cashoutAccountId);

        bt_request_response_log requestResponseLog = new bt_request_response_log(
                fromEnum(SMART_PHONE), fromEnum(UPDATE_USER), request).save();

        bt_cashout_account cashoutAccount = getEntityManagerRepo().findById(bt_cashout_account.class, cashoutAccountId);

        if (cashoutAccount == null) {
            String outgoingResponse = format("cashoutAccount %s does not exist", cashoutAccountId);
            logger.warning(outgoingResponse);
            requestResponseLog.setResponse_code(fromEnum(DATA_NOT_FOUND));
            requestResponseLog.setOutgoing_response(outgoingResponse).setOutgoing_response_time(new Date()).save();
            return new BTResponse(DATA_NOT_FOUND);
        } else if (!cashoutAccount.getCashout_account_user().getId().equals(userId)) {
            String outgoingResponse = format("cashoutAccount %s is not linked to user %s", cashoutAccountId, userId);
            logger.warning(outgoingResponse);
            requestResponseLog.setResponse_code(fromEnum(DATA_NOT_FOUND));
            requestResponseLog.setOutgoing_response(outgoingResponse).setOutgoing_response_time(new Date()).save();
            return new BTResponse(DATA_NOT_FOUND);
        }

        cashoutAccount.delete();
        String outgoingResponse = format("Disabled cashoutAccount %s for user %s", cashoutAccountId, userId);
        logger.warning(outgoingResponse);
        requestResponseLog.setResponse_code(fromEnum(SUCCESS));
        requestResponseLog.setOutgoing_response(outgoingResponse).setOutgoing_response_time(new Date()).save();

        return new BTResponse(SUCCESS);
    }

    @Override
    public synchronized BTWalletList swipeTransaction(Long userId, String deviceId, BigDecimal amount,
                                                  String reference, String cardNumber, String cardPin) {

        BTResponseObject<bt_user> userResponse = validateSystemUser(userId);
        if (!userResponse.getResponseCode().equals(SUCCESS)) {
            return new BTWalletList(userResponse.getResponseCode());
        }
        BTResponseObject validationResponse = validateAmount(amount);
        if (!validationResponse.getResponseCode().equals(SUCCESS)) {
            return new BTWalletList(validationResponse.getResponseCode());
        }
        if (!isNullOrEmpty(reference) && !isValidPlainText(reference)) {
            return new BTWalletList(INPUT_INVALID_REQUEST).setResponse("Transaction reference is invalid");
        }
        if (!isValidCardNumber(valueOf(cardNumber))) {
            return new BTWalletList(INPUT_INVALID_REQUEST).setResponse(
                format("cardNumber '%s' is not valid", cardNumber));
        }
        if (!isNullOrEmpty(valueOf(cardPin)) && !isValidCardPin(valueOf(cardPin))) {
            return new BTWalletList(INPUT_INVALID_PASSWORD).setResponse("cardPin is not valid");
        }
        List<bt_auth_user> authUsers = getEntityManagerRepo().findWhere(bt_auth_user.class,
            asList(
                new Pair<>("bt_user_id", userId),
                new Pair<>("channel_id", fromEnum(SMART_PHONE).getId())
            )
        );

        if (authUsers == null || authUsers.size() == 0) {
            return new BTWalletList(DATA_NOT_FOUND).setResponse(format("userId '%s' not found", userId));
        }

        bt_wallet wallet = authUsers.get(0).getUser().getWallet();

        String request = format("userId=%s,deviceId=%s,transactionAmount=%s,transactionReference=%s,cashoutAccountId=%s,pin=XXXXX",
                userId, deviceId, amount, reference, cardNumber);

        bt_request_response_log swipeTransactionLog = new bt_request_response_log(fromEnum(SMART_PHONE), fromEnum(LOAD_WALLET),
                userResponse.getResponseObject(), authUsers.get(0), null,
                new Date(), null, request, null).save();

        bt_swipe_transaction swipeTransaction = new bt_swipe_transaction(authUsers.get(0), reference,
            null, cardNumber, amount, wallet.getCurrent_balance(),
            null, new Date(), null).save();

        BTResponseCode updateResponse = updateWalletBalance(wallet, amount);

        //TODO execute swipe transaction

        swipeTransactionLog.setResponse_code(fromEnum(SUCCESS));
        swipeTransactionLog.setOutgoing_response(SUCCESS.getMessage());
        swipeTransactionLog.setOutgoing_response_time(new Date());

        swipeTransaction.setNew_balance(wallet.getCurrent_balance());
        swipeTransaction.setTransaction_status(fromEnum(updateResponse));
        swipeTransaction.save();

        return new BTWalletList(updateResponse, converterService.toDTO(wallet));
    }

    @Override
    public synchronized BTWalletList cashoutTransaction(Long userId, String deviceId, BigDecimal amount,
                                           String reference, Long cashoutAccountId, String pin) {

        BTResponseObject<bt_user> userResponse = validateSystemUser(userId);
        if (!userResponse.getResponseCode().equals(SUCCESS)) {
            return new BTWalletList(userResponse.getResponseCode());
        }
        BTResponseObject validationResponse = validateAmount(amount);
        if (!validationResponse.getResponseCode().equals(SUCCESS)) {
            return new BTWalletList(validationResponse.getResponseCode());
        }
        if (!isNullOrEmpty(reference) && !isValidPlainText(reference)) {
            return new BTWalletList(INPUT_INVALID_REQUEST).setResponse("Transaction reference is invalid");
        }
        BTResponseObject<bt_cashout_account> cashoutAccountResponse = validateCashoutAccount(cashoutAccountId);
        if (!cashoutAccountResponse.getResponseCode().equals(SUCCESS)) {
            return new BTWalletList(cashoutAccountResponse.getResponseCode());
        }
        if (!isValidPin(pin)) {
            return new BTWalletList(INPUT_INVALID_PASSWORD).setResponse("Pin is not valid");
        }
        List<bt_auth_user> authUsers = getEntityManagerRepo().findWhere(bt_auth_user.class,
            asList(
                new Pair<>("bt_user_id", userId),
                new Pair<>("channel_id", fromEnum(SMART_PHONE).getId())
            )
        );

        if (authUsers == null || authUsers.size() == 0) {
            return new BTWalletList(DATA_NOT_FOUND).setResponse(format("userId '%s' not found", userId));
        }

        bt_wallet wallet = authUsers.get(0).getUser().getWallet();

        bt_cashout_transaction cashoutTransaction = new bt_cashout_transaction(authUsers.get(0), reference,
                cashoutAccountResponse.getResponseObject(), amount, wallet.getCurrent_balance(),
                null, new Date(), null).save();

        String request = format("userId=%s,deviceId=%s,amount=%s,reference=%s,cashoutAccountId=%s,pin=XXXXX",
            userId, deviceId, amount, reference, cashoutAccountId);

        bt_request_response_log cashoutLog = new bt_request_response_log(fromEnum(SMART_PHONE), fromEnum(CASHOUT),
                userResponse.getResponseObject(), authUsers.get(0), null,
                new Date(), null, request, null).save();

        GoPayAuthenticationProvider authProvider = new GoPayAuthenticationProvider(
            cashoutLog, userResponse.getResponseObject().getUsername(), pin, deviceId
        );

        BTResponseObject<bt_auth_user> authResponse = authProvider.validatePin(authUsers.get(0), pin);
        if (!authResponse.getResponseCode().equals(SUCCESS)) {
            cashoutLog.setResponse_code(fromEnum(authResponse.getResponseCode()));
            cashoutLog.setOutgoing_response(authResponse.getMessage());
            cashoutLog.setOutgoing_response_time(new Date());
            cashoutLog.save();
            cashoutTransaction.setTransaction_status(fromEnum(authResponse.getResponseCode()));
            cashoutTransaction.save();
            return new BTWalletList(authResponse.getResponseCode());
        }

        BTResponseCode updateResponse = updateWalletBalance(wallet, amount.multiply(new BigDecimal(-1)));

        //TODO execute cashout transaction

        cashoutTransaction.setNew_balance(wallet.getCurrent_balance());
        cashoutTransaction.setTransaction_status(fromEnum(updateResponse));
        cashoutTransaction.save();

        return new BTWalletList(updateResponse, converterService.toDTO(wallet));
    }

    @Override
    public BTSystemUserList startSession(String deviceId, String username, String pin, BTChannel channel) {
        logger.info(format("Performing login for %s", username));

        String request = format("deviceId:%s|username:%s|pin:%s|channel:%s", deviceId, username, pin, channel.name());

        bt_request_response_log requestResponseLog = new bt_request_response_log(
                fromEnum(SMART_PHONE), fromEnum(LOGIN), request).save();

        GoPayAuthenticationProvider authenticationProvider = new GoPayAuthenticationProvider(
                requestResponseLog, username, pin, deviceId
        );

        BTResponseObject<bt_auth_user> authResponse = authenticationProvider.authenticateUser();

        requestResponseLog.setOutgoing_response(authResponse.getMessage());
        requestResponseLog.setOutgoing_response_time(new Date());
        requestResponseLog.setResponse_code(fromEnum(authResponse.getResponseCode()));
        if (authResponse.getResponseCode().equals(SUCCESS)) {
            requestResponseLog.setAuth_user(authResponse.getResponseObject());
            requestResponseLog.setSystem_user(authResponse.getResponseObject().getUser());
        }
        requestResponseLog.save();

        return new BTSystemUserList(authResponse.getResponseCode(), converterService.toDTO(authResponse.getResponseObject()));
    }

    @Override
    public BTResponse endSession(Long sessionId, String deviceId, String authToken, BTChannel channel) {

        logger.info(format("Performing logout for session %s", sessionId));

        String request = format("sessionId:%s|deviceId:%s|authToken:%s|channel:%s", sessionId, deviceId, authToken, channel.name());
        bt_request_response_log requestResponseLog = new bt_request_response_log(
                fromEnum(SMART_PHONE), fromEnum(LOGOUT), request).save();

        bt_session currentSession = getEntityManagerRepo().findById(bt_session.class, sessionId);

        BTResponseObject logoutResponse;

        if (currentSession == null) {
            logoutResponse = new BTResponseObject(DATA_NOT_FOUND);
        } else {
            GoPayAuthenticationProvider authenticationProvider = new GoPayAuthenticationProvider(
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

        requestResponseLog.setOutgoing_response(logoutResponse.getMessage());
        requestResponseLog.setOutgoing_response_time(new Date());
        requestResponseLog.setResponse_code(fromEnum(logoutResponse.getResponseCode()));
        requestResponseLog.save();

        return new BTResponse(logoutResponse.getResponseCode());
    }
}
