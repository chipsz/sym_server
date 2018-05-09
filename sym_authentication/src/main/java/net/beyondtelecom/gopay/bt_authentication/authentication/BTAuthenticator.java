package net.beyondtelecom.gopay.bt_authentication.authentication;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.bt_core_lib.response.BTResponseObject;
import net.beyondtelecom.gopay.bt_common.structure.Pair;
import net.beyondtelecom.gopay.bt_persistence.dao.complex_type.BTAuthUserDao;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_session;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_auth_group;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_channel;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_response_code;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.*;
import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.*;
import static net.beyondtelecom.bt_core_lib.utilities.CommonUtilities.formatFullMsisdn;
import static net.beyondtelecom.gopay.bt_authentication.authentication.AuthenticationHelper.isPinChannel;
import static net.beyondtelecom.gopay.bt_authentication.authentication.AuthenticationHelper.validateMandatoryChannelData;
import static net.beyondtelecom.gopay.bt_common.security.BeyondTelecomSecurityEncryption.DEFAULT_SECURITY_HASH;
import static net.beyondtelecom.gopay.bt_common.security.BeyondTelecomSecurityEncryption.MAX_PASSWORD_TRIES;
import static net.beyondtelecom.gopay.bt_common.security.Security.generateIV;
import static net.beyondtelecom.gopay.bt_common.security.Security.hashWithSalt;
import static net.beyondtelecom.gopay.bt_common.utilities.ReferenceGenerator.Gen;
import static net.beyondtelecom.gopay.bt_common.utilities.ReferenceGenerator.GenPin;
import static net.beyondtelecom.gopay.bt_persistence.dao.EnumEntityRepoManager.findByName;
import static net.beyondtelecom.gopay.bt_persistence.helper.BTEnumHelper.fromEnum;
import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getAuthUserDao;
import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEntityManagerRepo;

/**
* Created with IntelliJ IDEA.
* User: tkaviya
* Date: 8/6/13
* Time: 7:06 PM
*/
@Service
public class BTAuthenticator {

	private static Logger logger = Logger.getLogger(BTAuthenticator.class.getSimpleName());

	private static BTAuthUserDao goPayAuthUserDao;

	static BTAuthUserDao getBTAuthUserDao() {
		if (goPayAuthUserDao == null) {
			goPayAuthUserDao = getAuthUserDao();
		}
		return goPayAuthUserDao;
	}

	static BTResponseObject<bt_session> startSession(bt_channel channel,
		String deviceId, String username, String password, boolean searchAllUsernameTypes) {

		if   (channel == null) {
			return new BTResponseObject<bt_session>(INPUT_INVALID_REQUEST).setMessage("Invalid Channel");
		} else if   (deviceId != null && !isValidAuthData(deviceId)) {
			return new BTResponseObject<bt_session>(INPUT_INVALID_REQUEST).setMessage("Invalid Device Id: " + deviceId);
		} else if   (!isValidUsername(username)) {
			return new BTResponseObject<>(INPUT_INVALID_USERNAME);
		} else if   (!isValidPassword(password) && !isValidPin(password)) {
			return new BTResponseObject<>(INPUT_INVALID_PASSWORD);
		}

		logger.info(format("Finding user %s on channel %s ", username, channel.getName()));
		BTResponseObject<bt_auth_user> userResponse = getUserByUsername(username, channel, searchAllUsernameTypes);
		if (!userResponse.getResponseCode().equals(SUCCESS)) {
			return new BTResponseObject<>(userResponse.getResponseCode());
		}

		logger.info(format("Authenticating user %s onto channel %s ", username, channel.getName()));
		userResponse = authenticateUser(channel, userResponse.getResponseObject(), password);

		if (!userResponse.getResponseCode().equals(SUCCESS)) {
			logger.info(format("Authentication failed with response code %s ", userResponse.getResponseCode().name()));
			return new BTResponseObject<>(userResponse.getResponseCode());
		}

		logger.info("Authenticated " + username);
		bt_auth_user authUser = userResponse.getResponseObject();
        authUser.setLast_login_date(authUser.getLast_auth_date() != null ? authUser.getLast_auth_date() : new Date());
        authUser.setLast_auth_date(new Date());

		logger.info(format("Creating new session for user %s ", username));
		bt_session newSession = new bt_session(authUser, deviceId, generateIV(), new Date(), null).save();

		logger.info("Returning SUCCESS response");
		return new BTResponseObject<>(SUCCESS, newSession);
	}

	static BTResponseObject endSession(Long sessionId,
		bt_channel channel, String deviceId, String authToken) {

		if (sessionId == null) {
			return new BTResponseObject<>(INPUT_INVALID_REQUEST).setMessage("Invalid Session ID");
		} else if   (channel == null) {
			return new BTResponseObject<>(INPUT_INVALID_REQUEST).setMessage("Invalid Channel");
		} else if   (!isValidAuthData(deviceId)) {
			return new BTResponseObject<>(INPUT_INVALID_REQUEST).setMessage("Invalid Device Id");
		} else if   (!isValidAuthData(authToken)) {
			return new BTResponseObject<>(INPUT_INVALID_REQUEST).setMessage("Invalid Auth Token");
		}

		logger.info(format("Got end session request for session %s on channel %s by device %s",
				sessionId, channel.getName(), deviceId));

		bt_session currentSession = getEntityManagerRepo().findById(bt_session.class, sessionId);

		if (currentSession == null) { return new BTResponseObject<>(DATA_NOT_FOUND); }

		if (!currentSession.getDevice_id().equals(deviceId) ||
			!currentSession.getAuth_token().equals(authToken) ||
			!currentSession.getAuth_user().getChannel().getId().equals(channel.getId())) {
			return new BTResponseObject<>(AUTH_INSUFFICIENT_PRIVILEGES);
		}

		terminateSession(currentSession);

		return new BTResponseObject<>(SUCCESS);
	}

	static void terminateSession(bt_session currentSession) {
		if (currentSession == null) { return; }
		logger.info(format("Updating session end time to current time and terminating session %s", currentSession.getId()));
		currentSession.setEnd_time(new Date());
		getEntityManagerRepo().saveOrUpdate(currentSession);
	}

	static BTResponseObject<bt_auth_user> authenticateUser(bt_channel channel,
          String username, String password, boolean optimistic) {

		logger.info(format("Authenticating user %s with password %s on channel %s ", username, password, channel.getName()));
		BTResponseObject<bt_auth_user> response = getUserByUsername(username, channel, optimistic);

		if (!response.getResponseCode().equals(SUCCESS)) {
			logger.info(format("Authentication failed! %s : %s", response.getResponseCode().name(), response.getMessage()));
			return new BTResponseObject<>(response.getResponseCode());
		}

        if (isPinChannel(channel)) {
            return validatePin(response.getResponseObject(), password);
        } else {
            return validatePassword(response.getResponseObject(), password);
        }
	}

	static BTResponseObject<bt_auth_user> authenticateUser(bt_channel channel,
        bt_auth_user user, String password) {

        if (user == null) { return new BTResponseObject<>(INPUT_INCOMPLETE_REQUEST); }

		logger.info(format("Authenticating user %s with password %s on channel %s ",
                user.getUser().getUsername(), password, channel.getName()));

        if (isPinChannel(channel)) {
            return validatePin(user, password);
        } else {
            return validatePassword(user, password);
        }
	}

	static BTResponseObject<bt_auth_user> setResponse(bt_auth_user authUser) {

		if (authUser == null) { return new BTResponseObject<>(AUTH_NON_EXISTENT); }

		logger.info(format("Returning auth user %s in status %s ", authUser.getId(), authUser.getUser().getUser_status().getName()));
		return new BTResponseObject<>(SUCCESS, authUser);
	}

	/* When optimistic boolean is set, the function will try to find a user based on username only
	 * When optimistic boolean is set, the function will try to find a user based on username, email or msisdn */
	static BTResponseObject<bt_auth_user> getUserByUsername(
			String username, bt_channel channel, boolean optimistic) {

		if (username == null || channel == null) {
			logger.info("Invalid request specified for username=" + username + " and channel="+channel);
			return new BTResponseObject<>(INPUT_INVALID_REQUEST);
		}

		logger.info("Searching for system user by username " + username + " on channel " + channel.getName());
		logger.info("Optimistic mode enabled ? " + optimistic);

		if (!isValidUsername(username)) {
			if (!optimistic || (!isValidEmail(username) && !isValidMsisdn(username))) {
				logger.info("Invalid username \"" + username + "\" specified");
				return new BTResponseObject<>(INPUT_INVALID_USERNAME);
			}
		}

		logger.info("Attempting to find user by username");
		bt_auth_user authUser = getBTAuthUserDao().findByUsernameAndChannel(username, channel);

		if (authUser == null && optimistic) {
			logger.info("Attempting to find user by email");
			authUser = getBTAuthUserDao().findByEmailAndChannel(username, channel);
		}
		if (authUser == null && optimistic) {
			logger.info("Attempting to find user by msisdn");
			authUser = getBTAuthUserDao().findByMsisdnAndChannel(username, channel);
		}
		return setResponse(authUser);
	}

	static BTResponseObject<bt_auth_user> getUserByUsername(
			String username, bt_channel gopayChannel) {
		return getUserByUsername(username, gopayChannel, false);
	}

	static BTResponseObject<bt_auth_user> getUserByEmail(
		String email, bt_channel channel) {

		if (email == null || channel == null) {
			logger.info("Invalid request specified for username=" + email + " and channel="+channel);
			return new BTResponseObject<>(INPUT_INVALID_REQUEST);
		}

		logger.info("Searching for system user by email " + email + " on channel " + channel.getName());

		if (isValidEmail(email)) {
			return setResponse(getBTAuthUserDao().findByEmailAndChannel(email, channel));
		}
		else return new BTResponseObject<>(INPUT_INVALID_EMAIL);
	}

	static BTResponseObject<bt_auth_user> getUserByMsisdn(
		String msisdn, bt_channel channel) {

		if (msisdn == null || channel == null) {
			logger.info("Invalid request specified for username=" + msisdn + " and channel="+channel);
			return new BTResponseObject<>(INPUT_INVALID_REQUEST);
		}

		logger.info("Searching for system user by msisdn " + msisdn + " on channel " + channel.getName());

		if (isValidTenDigitMsisdn(msisdn)) {
			return setResponse(getBTAuthUserDao().findByMsisdnAndChannel(msisdn, channel));
		}
		else { return new BTResponseObject<>(INPUT_INVALID_MSISDN); }
	}

	static BTResponseObject<bt_auth_user> registerUser(
			bt_user newUser, bt_channel channel, bt_auth_group authGroup, String deviceId) {

		if (channel == null) {
			logger.info("Registration failed! Invalid channel (null) specified");
			return new BTResponseObject<>(INPUT_INVALID_REQUEST);
		} else if (newUser == null) {
			logger.info("Registration failed! Invalid user (null) specified");
			return new BTResponseObject<>(INPUT_INVALID_REQUEST);
		}

		if (isNullOrEmpty(newUser.getUsername()) && isNullOrEmpty(newUser.getEmail()) && isNullOrEmpty(newUser.getMsisdn())) {
			logger.info("Registration failed! User has no valid identifier (username/email/phone) specified.");
			return new BTResponseObject<>(INPUT_INVALID_REQUEST);
		}

        if (newUser.getCountry() == null || !isValidName(newUser.getCountry().getName())) {
            return new BTResponseObject<bt_auth_user>(INPUT_INVALID_REQUEST).setMessage("Invalid country specified");
        }

        newUser.setMsisdn(formatFullMsisdn(newUser.getMsisdn(), newUser.getCountry().getDialing_code()));

		BTResponseObject<bt_user> validationResponse = validateMandatoryChannelData(newUser, channel);
		if (validationResponse.getResponseCode() != SUCCESS) {
			logger.info("Registration failed! User data incomplete for registration on channel " + channel.getName());
			logger.info(validationResponse.getMessage());
			return new BTResponseObject<>(validationResponse.getResponseCode());
		}

		//valid data supplied, now check for previous registration
        List<Pair<String, ?>> conditionList = new ArrayList<>();
        if (!isNullOrEmpty(newUser.getEmail()))     { conditionList.add(new Pair<>("email", newUser.getEmail())); }
        if (!isNullOrEmpty(newUser.getMsisdn()))    { conditionList.add(new Pair<>("msisdn", newUser.getMsisdn())); }
        if (!isNullOrEmpty(newUser.getUsername()))  { conditionList.add(new Pair<>("username", newUser.getUsername())); }
        List<bt_user> existingUser =  getEntityManagerRepo().findWhere(bt_user.class, conditionList,
            1, false, false, true, false
        );

		if (existingUser != null && existingUser.size() >= 1) {

            if (existingUser.get(0).getEmail() != null && existingUser.get(0).getEmail().equalsIgnoreCase(newUser.getEmail())) {
                logger.info("Registration failed! User with email \"" + newUser.getEmail() + "\" already exists.");
                return new BTResponseObject<>(PREVIOUS_EMAIL_FOUND);
            }

            if (existingUser.get(0).getMsisdn() != null && existingUser.get(0).getMsisdn().equals(newUser.getMsisdn())) {
                logger.info("Registration failed! User with MSISDN \"" + newUser.getMsisdn() + "\" already exists.");
                return new BTResponseObject<>(PREVIOUS_MSISDN_FOUND);
            }

            if (existingUser.get(0).getUsername() != null && existingUser.get(0).getUsername().equalsIgnoreCase(newUser.getUsername())) {
                logger.info("Registration failed! User with username \"" + newUser.getUsername() + "\" already exists.");
                return new BTResponseObject<>(PREVIOUS_USERNAME_FOUND);
            }
        }

		//No for previous registration, proceed to register user
		newUser.setUser_status(fromEnum(ACC_ACTIVE));
		newUser.setSalt(generateIV());

		if (isPinChannel(channel)) {
            newUser.setPin(hashPassword(newUser.getPin(), newUser.getSalt()));
        } else {
            newUser.setPassword(hashPassword(newUser.getPassword(), newUser.getSalt()));
        }

		newUser.save();

		return new BTResponseObject<>(SUCCESS, new bt_auth_user(newUser, channel, authGroup, deviceId,
                newUser.getSalt(), new Date(), null, null).save());
	}

    static BTResponseObject<bt_auth_user> assignChannel(
        bt_user user, bt_channel channel, bt_auth_group auth_group, String deviceId) {

        if (channel == null) {
            logger.info("Channel assignment failed! Invalid channel (null) specified");
            return new BTResponseObject<>(INPUT_INVALID_REQUEST);
        } else if (user == null) {
            logger.info("Channel assignment failed! Invalid user (null) specified");
            return new BTResponseObject<>(INPUT_INVALID_REQUEST);
        }

        //valid data supplied, now check for previous registration
        List<bt_auth_user> existingUsers = getEntityManagerRepo().findWhere(bt_auth_user.class, asList(
            new Pair<>("user", user.getId()),
            new Pair<>("channel", channel.getId())
        ));

        if (existingUsers != null && existingUsers.size() > 0) {
            logger.info("Channel assignment failed! Auth User with username \"" + user.getUsername()
                + "\" already exists on channel " + channel.getName());
            return new BTResponseObject<>(PREVIOUS_USERNAME_FOUND);
        }

        user.save();

        return new BTResponseObject<>(SUCCESS, new bt_auth_user(
            user, channel, auth_group, deviceId, null, new Date(), null, null
        ).save());
    }

	static BTResponseObject<bt_auth_user> confirmUserRegistration(Long userId, String authToken,
                                                                 String deviceId, String username,
                                                                 String password, bt_channel channel) {
		if (!isValidAuthData(authToken)) {
			logger.info(format("Registration failed! authToken is invalid (%s).", authToken));
			return new BTResponseObject<bt_auth_user>(INPUT_INVALID_REQUEST)
					.setMessage("Registration failed! Auth token is invalid (null).");
		}
		if (!isValidAuthData(deviceId)) {
			logger.info(format("Registration failed! deviceId is invalid (%s).", deviceId));
			return new BTResponseObject<bt_auth_user>(INPUT_INVALID_REQUEST)
					.setMessage("Registration failed! Invalid device id");
		}
		if (!isValidUsername(username)) {
			logger.info(format("Registration failed! Username is invalid (%s).", username));
			return new BTResponseObject<>(INPUT_INVALID_USERNAME);
		}
		if (!isValidPassword(password)) {
			logger.info(format("Registration failed! Password is invalid (%s).", password));
			return new BTResponseObject<>(INPUT_INVALID_PASSWORD);
		}
		if (channel == null) {
			logger.info("Registration failed! Invalid channel (null) specified");
			return new BTResponseObject<>(INPUT_INVALID_REQUEST);
		}

		//verify that user has entered a valid username and auth token
		BTResponseObject<bt_auth_user> symAuthUserResponse = getUserByUsername(username, channel);
		if (symAuthUserResponse.getResponseObject() == null || !symAuthUserResponse.getResponseObject().getUser().getId().equals(userId)) {
			logger.info("Registration failed! User " + username + " with id " + userId + " does not exist.");
			return new BTResponseObject<>(AUTH_NON_EXISTENT);
		}

		bt_user regUser = symAuthUserResponse.getResponseObject().getUser();

		if (!regUser.getUser_status().getId().equals(fromEnum(ACC_INACTIVE).getId())) {
			logger.info("Registration failed! Previous registration found for user " + username + " in status "
					+ regUser.getUser_status().getName());
			return new BTResponseObject<>(PREVIOUS_USERNAME_FOUND);
		}


		if (!regUser.getSalt().equals(authToken)) {
			logger.info("Registration failed! Invalid authToken " + authToken);
			return new BTResponseObject<>(AUTH_AUTHENTICATION_FAILED);
		}

		regUser.setUser_status(fromEnum(ACC_ACTIVE));
		regUser.setSalt(generateSalt());
		regUser.setPassword(hashPassword(password, regUser.getSalt()));
		regUser.setPassword_tries(0);
		regUser.save();
		return new BTResponseObject<>(SUCCESS, symAuthUserResponse.getResponseObject());
	}

    static BTResponseObject<bt_auth_user> changePin(bt_auth_user authUser,
        String newPin, boolean validatePrevious, String oldPassword) {

        logger.info(format("Changing pin for user %s ", authUser.getUser().getUsername()));
        BTResponseObject<bt_auth_user> response;

        if (validatePrevious) {
            response = validatePin(authUser, oldPassword);
            if (!response.getResponseCode().equals(SUCCESS)) { return response; }
        }

        if (newPin == null) { newPin = GenPin(); }

        if (!isValidPin(newPin)) {
            logger.severe(format("Failed to change user %s pin. Pin was not valid", authUser.getUser().getUsername()));
            return new BTResponseObject<>(INPUT_INVALID_PASSWORD);
        }

        bt_user user = authUser.getUser();

        if (user.getSalt() == null) { user.setSalt(generateSalt()); }

        user.setPin(hashPassword(newPin, authUser.getUser().getSalt()));
        user.setPin_tries(0);
        if (user.getUser_status().equals(findByName(bt_response_code.class, ACC_PASSWORD_TRIES_EXCEEDED.name()))) {
            user.setUser_status(findByName(bt_response_code.class, ACC_ACTIVE.name()));
        }
        user.save().refresh();

        logger.info(format("Pin change response: %s", SUCCESS.getMessage()));
        return new BTResponseObject<>(SUCCESS, authUser);
    }

    static BTResponseObject<bt_auth_user> changePassword(bt_auth_user authUser,
            String newPassword, boolean validatePrevious, String oldPassword) {

        logger.info(format("Changing password for user %s ", authUser.getUser().getUsername()));
        BTResponseObject<bt_auth_user> response;

        if (validatePrevious) {
            response = validatePassword(authUser, oldPassword);
            if (!response.getResponseCode().equals(SUCCESS)) { return response; }
        }

        if (newPassword == null) { newPassword = Gen(); }

        if (!isValidPassword(newPassword)) {
            logger.severe(format("Failed to change user %s pin. Password was not valid", authUser.getUser().getUsername()));
            return new BTResponseObject<>(INPUT_INVALID_PASSWORD);
        }

        bt_user user = authUser.getUser();

        if (user.getSalt() == null) { user.setSalt(generateSalt()); }

        user.setPassword(hashPassword(newPassword, authUser.getUser().getSalt()));
        user.setPassword_tries(0);
        if (user.getUser_status().equals(findByName(bt_response_code.class, ACC_PASSWORD_TRIES_EXCEEDED.name()))) {
            user.setUser_status(findByName(bt_response_code.class, ACC_ACTIVE.name()));
        }
        user.save().refresh();

        logger.info(format("Password change response: %s", SUCCESS.getMessage()));
        return new BTResponseObject<>(SUCCESS, authUser);

    }

    static String hashPassword(String rawPassword, String salt) {
		logger.info(format("Hashing password %s with salt %s", rawPassword, salt));
		return hashWithSalt(rawPassword, DEFAULT_SECURITY_HASH, salt.getBytes());
	}

	static BTResponseObject<bt_auth_user> validatePassword(bt_auth_user gopayAuthUser, String password) {

		logger.info(format("Validating password for auth user %s ", gopayAuthUser.getId()));
		BTResponseObject<bt_auth_user> response = new BTResponseObject<>(GENERAL_ERROR, gopayAuthUser);

		bt_user gopayUser = gopayAuthUser.getUser();

		gopayAuthUser.setLast_auth_date(new Date());
		if (!gopayUser.getUser_status().getId().equals(fromEnum(ACC_ACTIVE).getId())) {
			logger.info("Account is not active. Setting response to " + gopayAuthUser.getUser().getUser_status().getName());
			response.setResponseCode(BTResponseCode.valueOf(gopayUser.getUser_status().getId().intValue()));
		}
		else
		{
			int passwordTries = gopayUser.getPassword_tries();

			if (passwordTries >= MAX_PASSWORD_TRIES) {
				logger.info("Authentication failed! Password tries exceeded");
				response.setResponseCode(ACC_PASSWORD_TRIES_EXCEEDED);
			}
			else if (!isValidPassword(password))
			{
				logger.info("Authentication failed! Password format was invalid");
				response.setResponseCode(INPUT_INVALID_REQUEST).setMessage("Password format was invalid");
				gopayUser.setPassword_tries(++passwordTries);
				if (gopayUser.getPassword_tries() >= MAX_PASSWORD_TRIES) {
					logger.info("Authentication failed! Password format was invalid. Password tries exceeded");
					gopayUser.setUser_status(fromEnum(ACC_PASSWORD_TRIES_EXCEEDED));
				}
			}
			else if (gopayUser.getPassword().equals(hashPassword(password, gopayUser.getSalt()))) {
				logger.info("Authentication success");
				response.setResponseCode(SUCCESS);
				logger.info("Updating password tries to 0");
				gopayUser.setPassword_tries(0);
			}
			else {
				logger.info("Authentication failed! Incorrect password");
				response.setResponseCode(AUTH_INCORRECT_PASSWORD);
				gopayUser.setPassword_tries(++passwordTries);
				gopayAuthUser.setLast_login_date(new Date());
				if (gopayUser.getPassword_tries() >= MAX_PASSWORD_TRIES) {
					logger.info("Password tries exceeded");
					gopayUser.setUser_status(fromEnum(ACC_PASSWORD_TRIES_EXCEEDED));
				}
			}
		}

		gopayUser.save().refresh();
		gopayAuthUser.save();

		logger.info("Returning response " + response.getResponseCode().name());
		return response;
	}

	static BTResponseObject<bt_auth_user> validatePin(bt_auth_user gopayAuthUser, String pin) {

		logger.info(format("Validating pin for auth user %s ", gopayAuthUser.getId()));
		BTResponseObject<bt_auth_user> response = new BTResponseObject<>(GENERAL_ERROR, gopayAuthUser);

		bt_user gopayUser = gopayAuthUser.getUser();

		gopayAuthUser.setLast_auth_date(new Date());
		if (!gopayUser.getUser_status().getId().equals(fromEnum(ACC_ACTIVE).getId())) {
			logger.info("Account is not active. Setting response to " + gopayAuthUser.getUser().getUser_status().getName());
			response.setResponseCode(BTResponseCode.valueOf(gopayUser.getUser_status().getId().intValue()));
		}
		else
		{
			int pinTries = gopayUser.getPin_tries();

			if (pinTries >= MAX_PASSWORD_TRIES) {
				logger.info("Authentication failed! Pin tries exceeded");
				response.setResponseCode(ACC_PASSWORD_TRIES_EXCEEDED);
			}
			else if (!isValidPin(pin))
			{
				logger.info("Authentication failed! Pin format was invalid");
				response.setResponseCode(INPUT_INVALID_REQUEST).setMessage("Pin format was invalid");
				gopayUser.setPin_tries(++pinTries);
				if (gopayUser.getPin_tries() >= MAX_PASSWORD_TRIES) {
					logger.info("Authentication failed! Pin format was invalid. Pin tries exceeded");
					gopayUser.setUser_status(fromEnum(ACC_PASSWORD_TRIES_EXCEEDED));
				}
			}
			else if (gopayUser.getPin().equals(hashPassword(pin, gopayUser.getSalt()))) {
				logger.info("Authentication success");
				response.setResponseCode(SUCCESS);
				logger.info("Updating pin tries to 0");
				gopayUser.setPin_tries(0);
			}
			else {
				logger.info("Authentication failed! Incorrect pin");
				response.setResponseCode(AUTH_INCORRECT_PASSWORD);
				gopayUser.setPin_tries(++pinTries);
				gopayAuthUser.setLast_login_date(new Date());
				if (gopayUser.getPin_tries() >= MAX_PASSWORD_TRIES) {
					logger.info("Pin tries exceeded");
					gopayUser.setUser_status(fromEnum(ACC_PASSWORD_TRIES_EXCEEDED));
				}
			}
		}

		gopayUser.save().refresh();
		gopayAuthUser.save();

		logger.info("Returning response " + response.getResponseCode().name());
		return response;
	}

	static String generateSalt() { return generateIV(); }
}
