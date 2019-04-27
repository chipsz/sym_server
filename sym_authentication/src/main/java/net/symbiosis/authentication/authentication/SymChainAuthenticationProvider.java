package net.symbiosis.authentication.authentication;

/* *************************************************************************
 * Created:     2016/01/01                                                 *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 */

import net.symbiosis.core_lib.enumeration.SymResponseCode;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.dao.EnumEntityRepoManager;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.log.sym_session;
import net.symbiosis.persistence.entity.complex_type.sym_auth_group_role;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.enumeration.sym_channel;
import net.symbiosis.persistence.entity.enumeration.sym_response_code;
import net.symbiosis.persistence.entity.enumeration.sym_role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static net.symbiosis.authentication.authentication.SymbiosisAuthenticator.getUserByUsername;
import static net.symbiosis.core_lib.enumeration.SymEventType.USER_LOGIN;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.*;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;

public abstract class SymChainAuthenticationProvider {

    protected static final Logger logger = Logger.getLogger(SymChainAuthenticationProvider.class.getSimpleName());
    private final sym_channel symChannel;
    private sym_request_response_log requestResponseLog;
    sym_auth_user symAuthUser;
    sym_user symUser;
    sym_session symSession;
    private SymResponseObject<sym_auth_user> responseObject;
    private String username, email, msisdn, password, deviceId, authToken;

    protected static final HashMap<sym_channel, ArrayList<AuthenticationStep>> authenticationChain = new HashMap<>();

    private static final HashMap<SymResponseCode, SymResponseCode> mappedResponseCode = new HashMap<>();

    static {
        // We will mask any response code < 0 because it is a general system error that a user should not see
        EnumEntityRepoManager.findEnabled(sym_response_code.class)
                .stream()
                .filter(symResponseCode -> symResponseCode.getId() < 0)
                .forEach(errResponseCode -> mappedResponseCode.put(errResponseCode.asSymResponseCode(), GENERAL_ERROR));

        // We will mask certain authentication response codes to avoid username/password guessing
        mappedResponseCode.put(DATA_NOT_FOUND, AUTH_AUTHENTICATION_FAILED);
        mappedResponseCode.put(INPUT_INVALID_REQUEST, AUTH_AUTHENTICATION_FAILED);
        mappedResponseCode.put(AUTH_INCORRECT_PASSWORD, AUTH_AUTHENTICATION_FAILED);
        mappedResponseCode.put(AUTH_NON_EXISTENT, AUTH_AUTHENTICATION_FAILED);
    }

    protected interface AuthenticationStep {
        SymResponseObject<sym_auth_user> executeAuthenticationStep();
    }

    //each auth provider must determine its own chain of authentication
    protected abstract void initializeAuthenticationChain();

    public SymChainAuthenticationProvider(sym_request_response_log requestResponseLog, sym_channel channel) {
        this.requestResponseLog = requestResponseLog;
        this.symChannel = channel;
        initializeAuthenticationChain();
    }

    // Functions to set auth data. They return SymChainAuthenticationProvider simply to allow method chaining
    public SymChainAuthenticationProvider setUser(sym_user user) {
        this.symUser = user;
        return this;
    }

    public SymChainAuthenticationProvider setAuthUser(sym_auth_user authUser) {
        this.symAuthUser = authUser;
        return this;
    }

    public SymChainAuthenticationProvider setSession(sym_session session) {
        this.symSession = session;
        return this;
    }

    public SymChainAuthenticationProvider setAuthUsername(String username) {
        this.username = username;
        return this;
    }

    public SymChainAuthenticationProvider setAuthEmail(String email) {
        this.email = email;
        return this;
    }

    public SymChainAuthenticationProvider setAuthMsisdn(String msisdn) {
        this.msisdn = msisdn;
        return this;
    }

    public SymChainAuthenticationProvider setAuthPassword(String password) {
        this.password = password;
        return this;
    }

    public SymChainAuthenticationProvider setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public SymChainAuthenticationProvider setAuthToken(String authToken) {
        this.authToken = authToken;
        return this;
    }

    public static void addMappedResponseCode(SymResponseCode symResponseCode, SymResponseCode returnResponse) {
        mappedResponseCode.put(symResponseCode, returnResponse);
    }

    public static SymResponseCode getMappedResponseCode(SymResponseCode symResponseCode) {
        return mappedResponseCode.get(symResponseCode);
    }

    public final SymResponseObject<sym_auth_user> authenticateUser() {

        requestResponseLog.setEvent_type(fromEnum(USER_LOGIN));

        ArrayList<AuthenticationStep> chain = authenticationChain.get(symChannel);

        for (AuthenticationStep authenticationStep : chain) {
            responseObject = authenticationStep.executeAuthenticationStep();
            if (responseObject.getResponseCode() != SUCCESS) {
                logger.info("Authentication failed with response: " +
                        responseObject.getResponseCode().name() + " -> " +
                        responseObject.getResponseCode().getMessage());
                requestResponseLog.setResponse_code(fromEnum(responseObject.getResponseCode()));
                break;
            }
        }
//		return logAndReturn();
        return responseObject;

    }

    protected SymResponseObject<sym_auth_user> getUserByUsernameAndChannel() {
        SymResponseObject<sym_auth_user> userResponse = getUserByUsername(username, symChannel);

        if (userResponse.getResponseCode() == SUCCESS) {
            symAuthUser = userResponse.getResponseObject();
        } else {
            return userResponse;
        }

        return userResponse;
    }

    protected SymResponseObject<sym_auth_user> getUserByOptimisticUsernameAndChannel() {
        SymResponseObject<sym_auth_user> userResponse = getUserByUsername(username, symChannel, true);

        if (userResponse.getResponseCode() == SUCCESS) {
            symAuthUser = userResponse.getResponseObject();
        } else {
            return userResponse;
        }

        return userResponse;
    }

    protected SymResponseObject<sym_auth_user> validatePassword() {
        SymResponseObject<sym_auth_user> authUserResponse = SymbiosisAuthenticator.validatePassword(symAuthUser, password);
        return new SymResponseObject<>(authUserResponse.getResponseCode(), authUserResponse.getResponseObject());
    }

    protected SymResponseObject<sym_auth_user> validatePin() {
        if (symAuthUser == null) {
            getUserByUsernameAndChannel();
        }
        SymResponseObject<sym_auth_user> authUserResponse = SymbiosisAuthenticator.validatePin(symAuthUser, password);
        return new SymResponseObject<>(authUserResponse.getResponseCode(), authUserResponse.getResponseObject());
    }

    protected SymResponseObject<sym_auth_user> startSession() {
        SymResponseObject<sym_session> loginResponse = SymbiosisAuthenticator.startSession(
                symChannel, deviceId, username, password, true);
        if (loginResponse.getResponseCode().equals(SUCCESS)) {
            symSession = loginResponse.getResponseObject();
            symAuthUser = loginResponse.getResponseObject().getAuth_user();
            symUser = loginResponse.getResponseObject().getAuth_user().getUser();
            return new SymResponseObject<>(loginResponse.getResponseCode(), loginResponse.getResponseObject().getAuth_user());
        } else {
            return new SymResponseObject<>(loginResponse.getResponseCode());
        }
    }

    public SymResponseObject endSession() {
        return SymbiosisAuthenticator.endSession(symSession.getId(), symChannel, deviceId, authToken);
    }

    public boolean hasRole(sym_role role) {
        if (symAuthUser == null) {
            return false;
        }

        List results = getEntityManagerRepo().findWhere(sym_auth_group_role.class, new ArrayList<>(
                asList(
                        new Pair<>("auth_group", symAuthUser.getAuth_group().getId()),
                        new Pair<>("role", role.getId()),
                        new Pair<>("is_enabled", 1)
                )
        ));

        logger.info(format("Found %s results for auth_group %s role %s",
                results == null ? 0 : results.size(), symAuthUser.getAuth_group(), role));

        return results != null && results.size() >= 1;
    }

//	protected SymResponseObject<sym_auth_user> logAndReturn() {
//        sym_auth_user auth_user, String device_id, String auth_token, Date start_time, Date end_time
//
//		sym_session log = new sym_session(symAuthUser, symAuthUser.getDevice_id(), symChannel, requestResponseLog.getEvent_type(),
//			getSymResponseCode(responseObject.getResponseCode()), requestResponseLog.getId(),
//			new Date(), responseObject.getMessage());
//
//		SymbiosisLogHelper.logSystemEvent(log);
//
//		if (responseObject.getResponseCode() != SUCCESS) {
//			Object mappedResponse = mappedResponseCode.get(responseObject.getResponseCode());
//			if (mappedResponse != null && mappedResponse instanceof sym_response_code) {
//				logger.info("Returning response " + mappedResponse + " for response code " + responseObject.getResponseCode());
//				SymResponseCode symResponseCode = SymResponseCode.valueOf(((sym_response_code) mappedResponse).getId());
//				responseObject.setResponseCode(symResponseCode);
//			}
//		}
//		return responseObject;
//	}
}

