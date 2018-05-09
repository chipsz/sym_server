package net.beyondtelecom.gopay.bt_authentication.authentication;

/* *************************************************************************
 * Created:     2016/01/01                                                 *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 * Website:     http://www.beyondtelecom.net                                    *
 * Contact:     beyondtelecom@gmail.com                                         *
*/

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.bt_core_lib.response.BTResponseObject;
import net.beyondtelecom.gopay.bt_common.structure.Pair;
import net.beyondtelecom.gopay.bt_persistence.dao.EnumEntityRepoManager;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_group_role;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_request_response_log;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_session;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_channel;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_response_code;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static net.beyondtelecom.bt_core_lib.enumeration.BTEventType.LOGIN;
import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.*;
import static net.beyondtelecom.gopay.bt_authentication.authentication.BTAuthenticator.getUserByUsername;
import static net.beyondtelecom.gopay.bt_persistence.helper.BTEnumHelper.fromEnum;
import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEntityManagerRepo;

public abstract class BTChainAuthenticationProvider {

	protected static final Logger logger = Logger.getLogger(BTChainAuthenticationProvider.class.getSimpleName());
	private final bt_channel gopayChannel;
	private bt_request_response_log requestResponseLog;
	bt_auth_user btAuthUser;
	bt_user btUser;
	bt_session btSession;
	private BTResponseObject<bt_auth_user> responseObject;
	private String username, email, msisdn, password, deviceId, authToken;

	protected static final HashMap<bt_channel, ArrayList<AuthenticationStep>> authenticationChain = new HashMap<>();

	private static final HashMap<BTResponseCode, BTResponseCode> mappedResponseCode = new HashMap<>();

	static {
		// We will mask any response code < 0 because it is a general system error that a user should not see
        EnumEntityRepoManager.findEnabled(bt_response_code.class)
				.stream()
				.filter(btResponseCode -> btResponseCode.getId() < 0)
				.forEach(errResponseCode -> mappedResponseCode.put(errResponseCode.asBTResponseCode(), GENERAL_ERROR));

		// We will mask certain authentication response codes to avoid username/password guessing
		mappedResponseCode.put(DATA_NOT_FOUND,          AUTH_AUTHENTICATION_FAILED);
		mappedResponseCode.put(INPUT_INVALID_REQUEST,	AUTH_AUTHENTICATION_FAILED);
		mappedResponseCode.put(AUTH_INCORRECT_PASSWORD, AUTH_AUTHENTICATION_FAILED);
		mappedResponseCode.put(AUTH_NON_EXISTENT,		AUTH_AUTHENTICATION_FAILED);
	}

	protected interface AuthenticationStep {
		BTResponseObject<bt_auth_user> executeAuthenticationStep();
	}

	//each auth provider must determine its own chain of authentication
	protected abstract void initializeAuthenticationChain();

	public BTChainAuthenticationProvider(bt_request_response_log requestResponseLog, bt_channel channel) {
		this.requestResponseLog = requestResponseLog;
		this.gopayChannel = channel;
		initializeAuthenticationChain();
	}

	// Functions to set auth data. They return BTChainAuthenticationProvider simply to allow method chaining
	public BTChainAuthenticationProvider setUser(bt_user user) { this.btUser = user; return this; }

    public BTChainAuthenticationProvider setAuthUser(bt_auth_user authUser) { this.btAuthUser = authUser; return this; }

    public BTChainAuthenticationProvider setSession(bt_session session) { this.btSession = session; return this; }

    public BTChainAuthenticationProvider setAuthUsername(String username) { this.username = username; return this; }

    public BTChainAuthenticationProvider setAuthEmail(String email) { this.email = email; return this; }

    public BTChainAuthenticationProvider setAuthMsisdn(String msisdn) { this.msisdn = msisdn; return this; }

    public BTChainAuthenticationProvider setAuthPassword(String password) { this.password = password; return this; }

    public BTChainAuthenticationProvider setDeviceId(String deviceId) { this.deviceId = deviceId; return this; }

    public BTChainAuthenticationProvider setAuthToken(String authToken) { this.authToken = authToken; return this; }

	public static void addMappedResponseCode(BTResponseCode btResponseCode, BTResponseCode returnResponse) {
		mappedResponseCode.put(btResponseCode, returnResponse);
	}

	public static BTResponseCode getMappedResponseCode(BTResponseCode btResponseCode) {
		return mappedResponseCode.get(btResponseCode);
	}

	public final BTResponseObject<bt_auth_user> authenticateUser() {

		requestResponseLog.setEvent_type(fromEnum(LOGIN));

		ArrayList<AuthenticationStep> chain = authenticationChain.get(gopayChannel);

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

	protected BTResponseObject<bt_auth_user> getUserByUsernameAndChannel() {
		BTResponseObject<bt_auth_user> userResponse = getUserByUsername(username, gopayChannel);

		if (userResponse.getResponseCode() == SUCCESS) {
			btAuthUser = userResponse.getResponseObject();
		} else { return userResponse; }

		return userResponse;
	}

	protected BTResponseObject<bt_auth_user> getUserByOptimisticUsernameAndChannel() {
		BTResponseObject<bt_auth_user> userResponse = getUserByUsername(username, gopayChannel,true);

		if (userResponse.getResponseCode() == SUCCESS) {
			btAuthUser = userResponse.getResponseObject();
		} else { return userResponse; }

		return userResponse;
	}

	protected BTResponseObject<bt_auth_user> validatePassword() {
		BTResponseObject<bt_auth_user> authUserResponse = BTAuthenticator.validatePassword(btAuthUser, password);
		return new BTResponseObject<>(authUserResponse.getResponseCode(), authUserResponse.getResponseObject());
	}

	protected BTResponseObject<bt_auth_user> validatePin() {
        if (btAuthUser == null) { getUserByUsernameAndChannel(); }
		BTResponseObject<bt_auth_user> authUserResponse = BTAuthenticator.validatePin(btAuthUser, password);
		return new BTResponseObject<>(authUserResponse.getResponseCode(), authUserResponse.getResponseObject());
	}

	protected BTResponseObject<bt_auth_user> startSession() {
		BTResponseObject<bt_session> loginResponse = BTAuthenticator.startSession(
                gopayChannel, deviceId, username, password, true);
		if (loginResponse.getResponseCode().equals(SUCCESS)) {
		    btSession = loginResponse.getResponseObject();
		    btAuthUser = loginResponse.getResponseObject().getAuth_user();
		    btUser = loginResponse.getResponseObject().getAuth_user().getUser();
            return new BTResponseObject<>(loginResponse.getResponseCode(), loginResponse.getResponseObject().getAuth_user());
        } else { return new BTResponseObject<>(loginResponse.getResponseCode()); }
	}

    public BTResponseObject endSession() { return BTAuthenticator.endSession(btSession.getId(), gopayChannel, deviceId, authToken); }

	public boolean hasRole(bt_role role) {
	    if (btAuthUser == null) { return false; }

        List results = getEntityManagerRepo().findWhere(bt_auth_group_role.class, new ArrayList<>(
            asList(
                new Pair<>("auth_group", btAuthUser.getAuth_group().getId()),
                new Pair<>("role", role.getId()),
                new Pair<>("is_enabled", 1)
            )
        ));

	    logger.info(format("Found %s results for auth_group %s role %s",
            results == null ? 0 : results.size(), btAuthUser.getAuth_group(), role));

        return results != null && results.size() >= 1;
    }

//	protected BTResponseObject<bt_auth_user> logAndReturn() {
//        bt_auth_user auth_user, String device_id, String auth_token, Date start_time, Date end_time
//
//		bt_session log = new bt_session(btAuthUser, btAuthUser.getDevice_id(), gopayChannel, requestResponseLog.getEvent_type(),
//			getBeyondTelecomResponseCode(responseObject.getResponseCode()), requestResponseLog.getId(),
//			new Date(), responseObject.getMessage());
//
//		BeyondTelecomLogHelper.logSystemEvent(log);
//
//		if (responseObject.getResponseCode() != SUCCESS) {
//			Object mappedResponse = mappedResponseCode.get(responseObject.getResponseCode());
//			if (mappedResponse != null && mappedResponse instanceof bt_response_code) {
//				logger.info("Returning response " + mappedResponse + " for response code " + responseObject.getResponseCode());
//				BTResponseCode btResponseCode = BTResponseCode.valueOf(((bt_response_code) mappedResponse).getId());
//				responseObject.setResponseCode(btResponseCode);
//			}
//		}
//		return responseObject;
//	}
}

