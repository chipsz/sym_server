package net.symbiosis.core.implementation;

import net.symbiosis.authentication.authentication.WebAuthenticationProvider;
import net.symbiosis.common.structure.Pair;
import net.symbiosis.core.contract.SymResponse;
import net.symbiosis.core.contract.SymSystemUserList;
import net.symbiosis.core.contract.symbiosis.SymSystemUser;
import net.symbiosis.core.service.ConverterService;
import net.symbiosis.core.service.WebRequestProcessor;
import net.symbiosis.core_lib.enumeration.SymChannel;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.log.sym_session;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.enumeration.sym_auth_group;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.logging.Logger;

import static java.lang.String.format;
import static net.symbiosis.common.configuration.Configuration.getProperty;
import static net.symbiosis.common.utilities.SymTransformer.stringToDate;
import static net.symbiosis.core_lib.enumeration.SymChannel.WEB;
import static net.symbiosis.core_lib.enumeration.SymEventType.*;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.*;
import static net.symbiosis.core_lib.utilities.SymValidator.isNullOrEmpty;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.SymEnumHelper.*;

/***************************************************************************
 * Created:     9/5/2018                                                  *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     kaviyat@payserv.co.zw                                      *
 **************************************************************************/

@Service
public class WebRequestProcessorImpl implements WebRequestProcessor {

    private static final Logger logger = Logger.getLogger(WebRequestProcessorImpl.class.getSimpleName());
    private final ConverterService converterService;

    @Autowired
    public WebRequestProcessorImpl(ConverterService converterService) {
        this.converterService = converterService;
    }

    @Override
    public SymSystemUserList registerWebUser(String email, String msisdn, String msisdn2, String username, String deviceId,
                                             String firstName, String lastName, String dateOfBirth) {

        logger.info(format("Performing registration for %s %s", firstName, lastName));

        sym_user newUser = new sym_user(firstName, lastName,
                stringToDate(dateOfBirth), username, null, null, 0, 0, null,
                email, msisdn, msisdn2, fromEnum(ACC_INACTIVE),
                countryFromString(getProperty("DefaultCountry")),
                languageFromString(getProperty("DefaultLanguage")));

        sym_request_response_log requestResponseLog = new sym_request_response_log(
                fromEnum(WEB), fromEnum(REGISTRATION), newUser.toPrintableString()).save();

        WebAuthenticationProvider authenticationProvider = new WebAuthenticationProvider(
                requestResponseLog, username, null, deviceId
        );

        SymResponseObject<sym_auth_user> registrationResponse = authenticationProvider.
                registerWebUser(newUser, null, findByName(sym_auth_group.class, getProperty("DefaultWebGroup")), null);

        if (registrationResponse.getResponseCode().equals(SUCCESS)) {
            requestResponseLog.setAuth_user(registrationResponse.getResponseObject());
            requestResponseLog.setSystem_user(registrationResponse.getResponseObject().getUser());
        }

        logResponse(requestResponseLog, registrationResponse.getResponseCode());
        return new SymSystemUserList(registrationResponse.getResponseCode(), converterService.toDTO(registrationResponse.getResponseObject()));
    }

    @Override
    public SymSystemUserList searchUser(String email, String msisdn, String username, String firstName, String lastName) {

        logger.info(format("Search for systemUser email=%s, msisdn=%s, username=%s, firstName=%s, lastName=%s",
                email, msisdn, username, firstName, lastName));

        if (isNullOrEmpty(username) && isNullOrEmpty(email) && isNullOrEmpty(msisdn) &&
                isNullOrEmpty(firstName) && isNullOrEmpty(lastName)) {
            SymSystemUserList response = new SymSystemUserList(INPUT_INCOMPLETE_REQUEST);
            response.getSymResponse()
                    .setResponse_message("Cannot search for user with no search parameters (email/msisdn/username/firstName/lastName)");
            return response;
        }

        ArrayList<SymSystemUser> systemUsers = new ArrayList<>();

        ArrayList<Pair<String, ?>> searchTerms = new ArrayList<>();

        if (!isNullOrEmpty(username)) {
            searchTerms.add(new Pair<>("username", username));
        }
        if (!isNullOrEmpty(email)) {
            searchTerms.add(new Pair<>("email", email));
        }
        if (!isNullOrEmpty(msisdn)) {
            searchTerms.add(new Pair<>("msisdn", msisdn));
        }
        if (!isNullOrEmpty(msisdn)) {
            searchTerms.add(new Pair<>("msisdn2", msisdn));
        }
        if (!isNullOrEmpty(firstName)) {
            searchTerms.add(new Pair<>("first_name", firstName));
        }
        if (!isNullOrEmpty(lastName)) {
            searchTerms.add(new Pair<>("last_name", lastName));
        }

        getEntityManagerRepo().findWhere(sym_user.class, searchTerms,
                false, true, true, false)
                .forEach(v -> systemUsers.add(converterService.toDTO(v)));

        logger.info(format("Returning %s systemUsers", systemUsers.size()));

        return new SymSystemUserList(SUCCESS, systemUsers);
    }

//    @Override
//	public SymSystemUserList confirmRegistration(Long userId, String authToken, String deviceId, String username,
//                                                String password, SymChannel channel) {
//
//		logger.info(format("Confirming registration for %s", username));
//		SymResponseObject<SymSystemUser> confirmResponse;
//
//		try {
//		    confirmResponse = confirmUserRegistration(userId, authToken, deviceId, username, password,
//                findByName(sym_channel.class, channel.name()));
//		}
//		catch (Exception ex) { return new SymSystemUserList(GENERAL_ERROR); }
//
//		return new SymSystemUserList(confirmResponse.getResponseCode(), confirmResponse.getResponseObject());
//	}

    @Override
    public SymSystemUserList startSession(String deviceId, String username, String password, SymChannel channel) {

        logger.info(format("Performing login for %s", username));

        String request = format("deviceId:%s|username:%s|password:%s|channel:%s", deviceId, username, password, channel.name());

        sym_request_response_log requestResponseLog = new sym_request_response_log(
                fromEnum(WEB), fromEnum(LOGIN), request).save();

        WebAuthenticationProvider authenticationProvider = new WebAuthenticationProvider(
                requestResponseLog, username, password, deviceId
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
                fromEnum(WEB), fromEnum(LOGOUT), request).save();

        sym_session currentSession = getEntityManagerRepo().findById(sym_session.class, sessionId);

        SymResponseObject logoutResponse;

        if (currentSession == null) {
            logoutResponse = new SymResponseObject(DATA_NOT_FOUND);
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

        logResponse(requestResponseLog, logoutResponse.getResponseCode());
        return new SymResponse(logoutResponse.getResponseCode());
    }

}
