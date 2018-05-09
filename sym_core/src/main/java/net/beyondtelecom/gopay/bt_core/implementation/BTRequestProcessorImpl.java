package net.beyondtelecom.gopay.bt_core.implementation;

import net.beyondtelecom.bt_core_lib.enumeration.BTChannel;
import net.beyondtelecom.bt_core_lib.response.BTResponseObject;
import net.beyondtelecom.gopay.bt_authentication.authentication.WebAuthenticationProvider;
import net.beyondtelecom.gopay.bt_common.structure.Pair;
import net.beyondtelecom.gopay.bt_core.contract.*;
import net.beyondtelecom.gopay.bt_core.contract.gopay.BTWallet;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTCurrency;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTFinancialInstitution;
import net.beyondtelecom.gopay.bt_core.contract.symbiosis.BTSystemUser;
import net.beyondtelecom.gopay.bt_core.service.BTRequestProcessor;
import net.beyondtelecom.gopay.bt_core.service.ConverterService;
import net.beyondtelecom.gopay.bt_persistence.dao.EnumEntityRepoManager;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_wallet;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_request_response_log;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_session;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static net.beyondtelecom.bt_core_lib.enumeration.BTChannel.WEB;
import static net.beyondtelecom.bt_core_lib.enumeration.BTEventType.*;
import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.*;
import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.*;
import static net.beyondtelecom.bt_core_lib.utilities.CommonUtilities.javaToJSRegex;
import static net.beyondtelecom.gopay.bt_common.configuration.Configuration.*;
import static net.beyondtelecom.gopay.bt_common.utilities.SymTransformer.stringToDate;
import static net.beyondtelecom.gopay.bt_persistence.dao.EnumEntityRepoManager.findByName;
import static net.beyondtelecom.gopay.bt_persistence.helper.BTEnumHelper.*;
import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 * *
 * Created:     20 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public class BTRequestProcessorImpl implements BTRequestProcessor {

	private final ConverterService converterService;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Autowired
    public BTRequestProcessorImpl(ConverterService converterService) {
        this.converterService = converterService;
    }

    public BTEnumEntity getResponseCode(Long responseCodeId) {

		bt_response_code response_code = getEntityManagerRepo().findById(bt_response_code.class, responseCodeId);

		if (response_code == null) {  return new BTEnumEntity(DATA_NOT_FOUND); }

		return new BTEnumEntity(SUCCESS, converterService.toDTO(response_code));
	}

    @Override
	public BTList getResponseCodes() {
		logger.info("Getting system response code list");
		List<bt_response_code> dbResponseCodes = EnumEntityRepoManager.findEnabled(bt_response_code.class);
		logger.info(format("Got %s bt_response_codes from DB", dbResponseCodes.size()));
		ArrayList<BTResponse> responses = new ArrayList<>();
        dbResponseCodes.forEach((r) -> responses.add(new BTResponse(r)));
		return new BTList(SUCCESS, responses);
	}

    @Override
	public BTEnumEntity getLanguage(Long languageId) {
		return new BTEnumEntity(SUCCESS,
				converterService.toDTO(getEntityManagerRepo().findById(bt_language.class, languageId)));
	}

    @Override
	public BTEnumEntity getChannel(Long channelId) {
		return new BTEnumEntity(SUCCESS,
				converterService.toDTO(getEntityManagerRepo().findById(bt_channel.class, channelId)));
	}

    @Override
	public BTEnumEntity getCountry(Long countryId) {
		return new BTEnumEntity(SUCCESS,
				converterService.toDTO(getEntityManagerRepo().findById(bt_country.class, countryId)));
	}

    @Override
	public BTEnumEntity getGroup(Long groupId) {
		return new BTEnumEntity(SUCCESS,
				converterService.toDTO(getEntityManagerRepo().findById(bt_auth_group.class, groupId)));
	}

    @Override
	public BTEnumEntity getRole(Long roleId) {
		return new BTEnumEntity(SUCCESS,
				converterService.toDTO(getEntityManagerRepo().findById(bt_role.class, roleId)));
	}

    @Override
	public BTEnumEntity getEventType(Long eventTypeId) {
		return new BTEnumEntity(SUCCESS,
				converterService.toDTO(getEntityManagerRepo().findById(bt_event_type.class, eventTypeId)));
	}

    @Override
    public BTSystemUserList registerWebUser(String email, String msisdn, String msisdn2, String username, String deviceId,
                                            String firstName, String lastName, String dateOfBirth) {

        logger.info(format("Performing registration for %s %s", firstName, lastName));

        bt_user newUser = new bt_user(firstName, lastName,
                stringToDate(dateOfBirth), username, null, null,0, 0, null,
                email, msisdn, msisdn2, fromEnum(ACC_INACTIVE),
                countryFromString(getProperty("DefaultCountry")),
                languageFromString(getProperty("DefaultLanguage")));

        bt_request_response_log requestResponseLog = new bt_request_response_log(
                fromEnum(WEB), fromEnum(REGISTRATION), newUser.toPrintableString()).save();

        WebAuthenticationProvider authenticationProvider = new WebAuthenticationProvider(
                requestResponseLog, username, null, deviceId
        );

        BTResponseObject<bt_auth_user> registrationResponse = authenticationProvider.
                registerWebUser(newUser, null, findByName(bt_auth_group.class, getProperty("DefaultWebGroup")), null);

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
	public BTSystemUserList searchUser(String email, String msisdn, String username, String firstName, String lastName) {

		logger.info(format("Search for systemUser email=%s, msisdn=%s, username=%s, firstName=%s, lastName=%s",
				email, msisdn, username, firstName, lastName));

		if (isNullOrEmpty(username) && isNullOrEmpty(email) && isNullOrEmpty(msisdn) &&
			isNullOrEmpty(firstName) && isNullOrEmpty(lastName)) {
			BTSystemUserList response = new BTSystemUserList(INPUT_INCOMPLETE_REQUEST);
			response.getBTResponse()
				.setResponse_message("Cannot search for user with no search parameters (email/msisdn/username/firstName/lastName)");
			return response;
		}

		ArrayList<BTSystemUser> systemUsers = new ArrayList<>();

		ArrayList<Pair<String, ?>> searchTerms = new ArrayList<>();

		if (!isNullOrEmpty(username)) { searchTerms.add(new Pair<>("username", username)); }
		if (!isNullOrEmpty(email)) { searchTerms.add(new Pair<>("email", email)); }
		if (!isNullOrEmpty(msisdn)) { searchTerms.add(new Pair<>("msisdn", msisdn)); }
		if (!isNullOrEmpty(msisdn)) { searchTerms.add(new Pair<>("msisdn2", msisdn)); }
		if (!isNullOrEmpty(firstName)) { searchTerms.add(new Pair<>("first_name", firstName)); }
		if (!isNullOrEmpty(lastName)) { searchTerms.add(new Pair<>("last_name", lastName)); }

		getEntityManagerRepo().findWhere(bt_user.class, searchTerms,
                false, true, true, false)
                .forEach(v -> systemUsers.add(converterService.toDTO(v)));

		logger.info(format("Returning %s systemUsers", systemUsers.size()));

		return new BTSystemUserList(SUCCESS, systemUsers);
	}

//    @Override
//	public BTSystemUserList confirmRegistration(Long userId, String authToken, String deviceId, String username,
//                                                String password, BTChannel channel) {
//
//		logger.info(format("Confirming registration for %s", username));
//		BTResponseObject<BTSystemUser> confirmResponse;
//
//		try {
//		    confirmResponse = confirmUserRegistration(userId, authToken, deviceId, username, password,
//                findByName(bt_channel.class, channel.name()));
//		}
//		catch (Exception ex) { return new BTSystemUserList(GENERAL_ERROR); }
//
//		return new BTSystemUserList(confirmResponse.getResponseCode(), confirmResponse.getResponseObject());
//	}

    @Override
    public BTSystemUserList startSession(String deviceId, String username, String password, BTChannel channel) {

		logger.info(format("Performing login for %s", username));

        String request = format("deviceId:%s|username:%s|password:%s|channel:%s", deviceId, username, password, channel.name());

        bt_request_response_log requestResponseLog = new bt_request_response_log(
                fromEnum(WEB), fromEnum(LOGIN), request).save();

        WebAuthenticationProvider authenticationProvider = new WebAuthenticationProvider(
                requestResponseLog, username, password, deviceId
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
                fromEnum(WEB), fromEnum(LOGOUT), request).save();

        bt_session currentSession = getEntityManagerRepo().findById(bt_session.class, sessionId);

        BTResponseObject logoutResponse;

        if (currentSession == null) {
            logoutResponse = new BTResponseObject(DATA_NOT_FOUND);
        } else {
            WebAuthenticationProvider authenticationProvider = new WebAuthenticationProvider(
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

	@Override
	public BTMap getSystemConfigs() {
		logger.info("Getting system configuration list");
		HashMap<String, String> sysConfigs = new HashMap<>();

		sysConfigs.put("CurrencySymbol", getCurrencySymbol());
		sysConfigs.put("CountryCodePrefix", getCountryCodePrefix());
		sysConfigs.put("SupportEmail", getProperty("SupportEmail"));
		sysConfigs.put("SupportPhone", getProperty("SupportPhone"));
		sysConfigs.put("MinPasswordLength", MIN_PASSWORD_LENGTH.toString());
		sysConfigs.put("MaxPasswordLength", MAX_PASSWORD_LENGTH.toString());
		sysConfigs.put("MinNameLength", MIN_NAME_LEN.toString());
		sysConfigs.put("MaxNameLength", MAX_NAME_LEN.toString());
		sysConfigs.put("MinUsernameLength", MIN_UNAME_LEN.toString());
		sysConfigs.put("MaxUsernameLength", MAX_UNAME_LEN.toString());
		sysConfigs.put("PinLength", PIN_LEN.toString());
		sysConfigs.put("NameRegex", javaToJSRegex(NAME_REGEX));
		sysConfigs.put("UsernameRegex", javaToJSRegex(USERNAME_REGEX));
		sysConfigs.put("EmailRegex", javaToJSRegex(EMAIL_REGEX));
		sysConfigs.put("PasswordRegex", javaToJSRegex(PASSWORD_REGEX));
		sysConfigs.put("TenDigitMsisdnRegex", javaToJSRegex(TEN_DIGIT_MSISDN_REGEX));
		sysConfigs.put("MsisdnRegex", javaToJSRegex(MSISDN_REGEX));

		logger.info(format("Got %s system configurations", sysConfigs.size()));

		return new BTMap(SUCCESS, sysConfigs);
	}

    @Override
    public BTFinancialInstitutionList getFinancialInstitution(Long institutionId) {
        logger.info(format("Getting financial institution with Id %s", institutionId));
        bt_financial_institution institution = getEntityManagerRepo().findById(bt_financial_institution.class, institutionId);
        if (institution == null) { return new BTFinancialInstitutionList(DATA_NOT_FOUND); }
        logger.info(format("Returning financial institution with Id %s: %s", institutionId, institution.toString()));
        return new BTFinancialInstitutionList(SUCCESS, converterService.toDTO(institution));
    }

    @Override
	public BTFinancialInstitutionList getFinancialInstitutions() {
		logger.info("Getting financial institution list");
		ArrayList<BTFinancialInstitution> financialInstitutions = new ArrayList<>();
        EnumEntityRepoManager.findEnabled(bt_financial_institution.class).forEach(f -> financialInstitutions.add(converterService.toDTO(f)));
		logger.info(format("Returning %s financial institutions", financialInstitutions.size()));
		return new BTFinancialInstitutionList(SUCCESS, financialInstitutions);
	}

    @Override
    public BTCurrencyList getCurrency(Long currencyId) {
        logger.info(format("Getting currency with Id %s", currencyId));
        bt_currency currency = getEntityManagerRepo().findById(bt_currency.class, currencyId);
        if (currency == null) { return new BTCurrencyList(DATA_NOT_FOUND); }
        logger.info(format("Returning currency with Id %s: %s", currencyId, currency.toString()));
        return new BTCurrencyList(SUCCESS, converterService.toDTO(currency));
    }

    @Override
	public BTCurrencyList getCurrencies() {
		logger.info("Getting currency list");
		ArrayList<BTCurrency> currencies = new ArrayList<>();
        EnumEntityRepoManager.findEnabled(bt_currency.class).forEach(c -> currencies.add(converterService.toDTO(c)));
		logger.info(format("Returning %s currencies", currencies.size()));
		return new BTCurrencyList(SUCCESS, currencies);
	}

    @Override
    public BTWalletList getWallet(Long walletId) {
        logger.info(format("Getting wallet with Id %s", walletId));
        bt_wallet wallet = getEntityManagerRepo().findById(bt_wallet.class, walletId);
        if (wallet == null) { return new BTWalletList(DATA_NOT_FOUND); }
        logger.info(format("Returning wallet with Id %s: %s", walletId, wallet.toString()));
        return new BTWalletList(SUCCESS, converterService.toDTO(wallet));
    }

    @Override
    public BTWalletList getWallets() {
        logger.info("Getting wallet list");
        ArrayList<BTWallet> wallets = new ArrayList<>();
        getEntityManagerRepo().findAll(bt_wallet.class).forEach(v -> wallets.add(converterService.toDTO(v)));
        logger.info(format("Returning %s wallets", wallets.size()));
        return new BTWalletList(SUCCESS, wallets);
    }
}
