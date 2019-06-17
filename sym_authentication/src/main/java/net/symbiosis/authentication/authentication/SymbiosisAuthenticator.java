package net.symbiosis.authentication.authentication;

import net.symbiosis.core_lib.enumeration.SymResponseCode;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.log.sym_session;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.enumeration.sym_auth_group;
import net.symbiosis.persistence.entity.enumeration.sym_channel;
import net.symbiosis.persistence.entity.enumeration.sym_response_code;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.Integer.parseInt;
import static java.lang.String.format;
import static java.util.Arrays.asList;
import static net.symbiosis.authentication.authentication.AuthenticationHelper.validateMandatoryChannelData;
import static net.symbiosis.common.utilities.SymValidator.*;
import static net.symbiosis.core_lib.enumeration.DBConfigVars.CONFIG_MAX_PASSWORD_TRIES;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.*;
import static net.symbiosis.core_lib.security.Security.generateIV;
import static net.symbiosis.core_lib.security.Security.hashWithSalt;
import static net.symbiosis.core_lib.security.SymbiosisSecurityEncryption.DEFAULT_SECURITY_HASH;
import static net.symbiosis.core_lib.utilities.CommonUtilities.formatFullMsisdn;
import static net.symbiosis.core_lib.utilities.CommonUtilities.isNullOrEmpty;
import static net.symbiosis.core_lib.utilities.ReferenceGenerator.Gen;
import static net.symbiosis.core_lib.utilities.ReferenceGenerator.GenPin;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;
import static net.symbiosis.persistence.helper.DaoManager.*;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;

/**
 * Created with IntelliJ IDEA.
 * User: tkaviya
 * Date: 8/6/13
 * Time: 7:06 PM
 */
@Service
public class SymbiosisAuthenticator {

    private static Logger logger = Logger.getLogger(SymbiosisAuthenticator.class.getSimpleName());

    static SymResponseObject<sym_session> startSession(sym_channel channel,
                                                       String deviceId, String username, String password, boolean searchAllUsernameTypes) {

        if (channel == null) {
            return new SymResponseObject<sym_session>(INPUT_INVALID_REQUEST).setMessage("Invalid Channel");
        } else if (deviceId != null && !isValidAuthData(deviceId)) {
            return new SymResponseObject<sym_session>(INPUT_INVALID_REQUEST).setMessage("Invalid Device Id: " + deviceId);
        } else if (!isValidUsername(username)) {
            return new SymResponseObject<>(INPUT_INVALID_USERNAME);
        } else if (!isValidPassword(password) && !isValidPin(password)) {
            return new SymResponseObject<>(INPUT_INVALID_PASSWORD);
        }

        logger.info(format("Finding user %s on channel %s ", username, channel.getName()));
        SymResponseObject<sym_auth_user> userResponse = getUserByUsername(username, channel, searchAllUsernameTypes);
        if (!userResponse.getResponseCode().equals(SUCCESS)) {
            return new SymResponseObject<>(userResponse.getResponseCode());
        }

        logger.info(format("Authenticating user %s onto channel %s ", username, channel.getName()));
        userResponse = authenticateUser(channel, userResponse.getResponseObject(), password);

        if (!userResponse.getResponseCode().equals(SUCCESS)) {
            logger.info(format("Authentication failed with response code %s ", userResponse.getResponseCode().name()));
            return new SymResponseObject<>(userResponse.getResponseCode());
        }

        logger.info("Authenticated " + username);
        sym_auth_user authUser = userResponse.getResponseObject();
        authUser.setLast_login_date(authUser.getLast_auth_date() != null ? authUser.getLast_auth_date() : new Date());
        authUser.setLast_auth_date(new Date());

        logger.info(format("Creating new session for user %s ", username));
        sym_session newSession = new sym_session(authUser, deviceId, generateIV(), new Date(), null).save();

        logger.info("Returning SUCCESS response");
        return new SymResponseObject<>(SUCCESS, newSession);
    }

    public static SymResponseObject<sym_session> verifyLogin(Long authUserId, String deviceId, String authToken) {

        logger.info(format("Verifying auth token %s for auth user %s with imei %s", authToken, authUserId, deviceId));

        if (authUserId == null) {
            logger.info("Login verification failed! authUserId cannot be null");
            return new SymResponseObject<>(AUTH_AUTHENTICATION_FAILED);
        }

        if (!isValidAuthData(authToken)) {
            logger.info(format("Login verification failed! Invalid auth token %s", authToken));
            return new SymResponseObject<>(AUTH_AUTHENTICATION_FAILED);
        }

        sym_session authSession = getEntityManagerRepo().findLast(sym_session.class, asList(
            new Pair<>("auth_user_id", authUserId),
            new Pair<>("device_id", deviceId),
            new Pair<>("auth_token", authToken)
        ));

        if (authSession == null || authSession.getEnd_time() != null) {
            logger.info(format("Login verification failed! Session does not exist for auth user %s with deviceId %s", authToken, deviceId));
            return new SymResponseObject<>(AUTH_AUTHENTICATION_FAILED);
        }

        authSession.getAuth_user().setLast_auth_date(new Date()).save();
        return new SymResponseObject<>(SUCCESS, authSession);
    }

    static SymResponseObject endSession(Long sessionId,
                                        sym_channel channel, String deviceId, String authToken) {

        if (sessionId == null) {
            return new SymResponseObject<>(INPUT_INVALID_REQUEST).setMessage("Invalid Session ID");
        } else if (channel == null) {
            return new SymResponseObject<>(INPUT_INVALID_REQUEST).setMessage("Invalid Channel");
        } else if (!isValidAuthData(deviceId)) {
            return new SymResponseObject<>(INPUT_INVALID_REQUEST).setMessage("Invalid Device Id");
        } else if (!isValidAuthData(authToken)) {
            return new SymResponseObject<>(INPUT_INVALID_REQUEST).setMessage("Invalid Auth Token");
        }

        logger.info(format("Got end session request for session %s on channel %s by device %s",
                sessionId, channel.getName(), deviceId));

        sym_session currentSession = getEntityManagerRepo().findById(sym_session.class, sessionId);

        if (currentSession == null) {
            return new SymResponseObject<>(DATA_NOT_FOUND);
        }

        if (!currentSession.getDevice_id().equals(deviceId) ||
                !currentSession.getAuth_token().equals(authToken) ||
                !currentSession.getAuth_user().getChannel().getId().equals(channel.getId())) {
            return new SymResponseObject<>(AUTH_INSUFFICIENT_PRIVILEGES);
        }

        terminateSession(currentSession);

        return new SymResponseObject<>(SUCCESS);
    }

    static void terminateSession(sym_session currentSession) {
        if (currentSession == null) {
            return;
        }
        logger.info(format("Updating session end time to current time and terminating session %s", currentSession.getId()));
        currentSession.setEnd_time(new Date());
        getEntityManagerRepo().saveOrUpdate(currentSession);
    }

    static SymResponseObject<sym_auth_user> authenticateUser(sym_channel channel,
                                                             String username, String password, boolean optimistic) {

        logger.info(format("Authenticating user %s with password %s on channel %s ", username, password, channel.getName()));
        SymResponseObject<sym_auth_user> response = getUserByUsername(username, channel, optimistic);

        if (!response.getResponseCode().equals(SUCCESS)) {
            logger.info(format("Authentication failed! %s : %s", response.getResponseCode().name(), response.getMessage()));
            return new SymResponseObject<>(response.getResponseCode());
        }

        if (AuthenticationHelper.isPinChannel(channel)) {
            return validatePin(response.getResponseObject(), password);
        } else {
            return validatePassword(response.getResponseObject(), password);
        }
    }

    static SymResponseObject<sym_auth_user> authenticateUser(sym_channel channel,
                                                             sym_auth_user user, String password) {

        if (user == null) {
            return new SymResponseObject<>(INPUT_INCOMPLETE_REQUEST);
        }

        logger.info(format("Authenticating user %s with password %s on channel %s ",
                user.getUser().getUsername(), password, channel.getName()));

        if (AuthenticationHelper.isPinChannel(channel)) {
            return validatePin(user, password);
        } else {
            return validatePassword(user, password);
        }
    }

    static SymResponseObject<sym_auth_user> setResponse(sym_auth_user authUser) {

        if (authUser == null) {
            return new SymResponseObject<>(AUTH_NON_EXISTENT);
        }

        logger.info(format("Returning auth user %s in status %s ", authUser.getId(), authUser.getUser().getUser_status().getName()));
        return new SymResponseObject<>(SUCCESS, authUser);
    }

    /* When optimistic boolean is set, the function will try to find a user based on username only
     * When optimistic boolean is set, the function will try to find a user based on username, email or msisdn */
    static SymResponseObject<sym_auth_user> getUserByUsername(
            String username, sym_channel channel, boolean optimistic) {

        if (username == null || channel == null) {
            logger.info("Invalid request specified for username=" + username + " and channel=" + channel);
            return new SymResponseObject<>(INPUT_INVALID_REQUEST);
        }

        logger.info("Searching for system user by username " + username + " on channel " + channel.getName());
        logger.info("Optimistic mode enabled ? " + optimistic);

        if (!isValidUsername(username)) {
            if (!optimistic || (!isValidEmail(username) && !isValidMsisdn(username))) {
                logger.info("Invalid username \"" + username + "\" specified");
                return new SymResponseObject<>(INPUT_INVALID_USERNAME);
            }
        }

        logger.info("Attempting to find user by username");
        sym_auth_user authUser = getAuthUserDao().findByUsernameAndChannel(username, channel);

        if (authUser == null && optimistic) {
            logger.info("Attempting to find user by email");
            authUser = getAuthUserDao().findByEmailAndChannel(username, channel);
        }
        if (authUser == null && optimistic) {
            logger.info("Attempting to find user by msisdn");
            authUser = getAuthUserDao().findByMsisdnAndChannel(username, channel);
        }
        return setResponse(authUser);
    }

    static SymResponseObject<sym_auth_user> getUserByUsername(
            String username, sym_channel symChannel) {
        return getUserByUsername(username, symChannel, false);
    }

    static SymResponseObject<sym_auth_user> getUserByEmail(
            String email, sym_channel channel) {

        if (email == null || channel == null) {
            logger.info("Invalid request specified for username=" + email + " and channel=" + channel);
            return new SymResponseObject<>(INPUT_INVALID_REQUEST);
        }

        logger.info("Searching for system user by email " + email + " on channel " + channel.getName());

        if (isValidEmail(email)) {
            return setResponse(getAuthUserDao().findByEmailAndChannel(email, channel));
        } else return new SymResponseObject<>(INPUT_INVALID_EMAIL);
    }

    public static SymResponseObject<sym_auth_user> getUserByMsisdn(
            String msisdn, sym_channel channel) {

        if (msisdn == null || channel == null) {
            logger.info("Invalid request specified for username=" + msisdn + " and channel=" + channel);
            return new SymResponseObject<>(INPUT_INVALID_REQUEST);
        }

        logger.info("Searching for system user by msisdn " + msisdn + " on channel " + channel.getName());

        if (isValidTenDigitMsisdn(msisdn)) {
            return setResponse(getAuthUserDao().findByMsisdnAndChannel(msisdn, channel));
        } else {
            return new SymResponseObject<>(INPUT_INVALID_MSISDN);
        }
    }

    static SymResponseObject<sym_auth_user> registerUser(
            sym_user newUser, sym_channel channel, sym_auth_group authGroup, String deviceId) {

        if (channel == null) {
            logger.info("Registration failed! Invalid channel (null) specified");
            return new SymResponseObject<>(INPUT_INVALID_REQUEST);
        } else if (newUser == null) {
            logger.info("Registration failed! Invalid user (null) specified");
            return new SymResponseObject<>(INPUT_INVALID_REQUEST);
        }

        if (isNullOrEmpty(newUser.getUsername()) && isNullOrEmpty(newUser.getEmail()) && isNullOrEmpty(newUser.getMsisdn())) {
            logger.info("Registration failed! User has no valid identifier (username/email/phone) specified.");
            return new SymResponseObject<>(INPUT_INVALID_REQUEST);
        }

        if (newUser.getCountry() == null || !isValidName(newUser.getCountry().getName())) {
            return new SymResponseObject<sym_auth_user>(INPUT_INVALID_REQUEST).setMessage("Invalid country specified");
        }

        newUser.setMsisdn(formatFullMsisdn(newUser.getMsisdn(), newUser.getCountry().getDialing_code()));

        SymResponseObject<sym_user> validationResponse = validateMandatoryChannelData(newUser, channel);
        if (!validationResponse.getResponseCode().equals(SUCCESS)) {
            logger.info("Registration failed! User data incomplete for registration on channel " + channel.getName());
            logger.info(validationResponse.getMessage());
            return new SymResponseObject<>(validationResponse.getResponseCode());
        }

        //valid data supplied, now check for previous registration
        List<Pair<String, ?>> conditionList = new ArrayList<>();
        if (!isNullOrEmpty(newUser.getEmail())) {
            conditionList.add(new Pair<>("email", newUser.getEmail()));
        }
        if (!isNullOrEmpty(newUser.getMsisdn())) {
            conditionList.add(new Pair<>("msisdn", newUser.getMsisdn()));
        }
        if (!isNullOrEmpty(newUser.getUsername())) {
            conditionList.add(new Pair<>("username", newUser.getUsername()));
        }
        List<sym_user> existingUser = getEntityManagerRepo().findWhere(sym_user.class, conditionList,
                1, false, false, true, false
        );

        if (existingUser != null && existingUser.size() >= 1) {

            if (existingUser.get(0).getEmail() != null && existingUser.get(0).getEmail().equalsIgnoreCase(newUser.getEmail())) {
                logger.info("Registration failed! User with email \"" + newUser.getEmail() + "\" already exists.");
                return new SymResponseObject<>(PREVIOUS_EMAIL_FOUND);
            }

            if (existingUser.get(0).getMsisdn() != null && existingUser.get(0).getMsisdn().equals(newUser.getMsisdn())) {
                logger.info("Registration failed! User with MSISDN \"" + newUser.getMsisdn() + "\" already exists.");
                return new SymResponseObject<>(PREVIOUS_MSISDN_FOUND);
            }

            if (existingUser.get(0).getUsername() != null && existingUser.get(0).getUsername().equalsIgnoreCase(newUser.getUsername())) {
                logger.info("Registration failed! User with username \"" + newUser.getUsername() + "\" already exists.");
                return new SymResponseObject<>(PREVIOUS_USERNAME_FOUND);
            }
        }

        //No for previous registration, proceed to register user
        newUser.setUser_status(fromEnum(ACC_ACTIVE));
        newUser.setSalt(generateIV());

        if (AuthenticationHelper.isPinChannel(channel)) {
            newUser.setPin(hashPassword(newUser.getPin(), newUser.getSalt()));
        } else {
            newUser.setPassword(hashPassword(newUser.getPassword(), newUser.getSalt()));
        }

        newUser.save();

        return new SymResponseObject<>(SUCCESS, new sym_auth_user(newUser, channel, authGroup, deviceId,
                newUser.getSalt(), new Date(), null, null).save());
    }

    static SymResponseObject<sym_auth_user> assignChannel(
            sym_user user, sym_channel channel, sym_auth_group auth_group, String deviceId) {

        if (channel == null) {
            logger.info("Channel assignment failed! Invalid channel (null) specified");
            return new SymResponseObject<>(INPUT_INVALID_REQUEST);
        } else if (user == null) {
            logger.info("Channel assignment failed! Invalid user (null) specified");
            return new SymResponseObject<>(INPUT_INVALID_REQUEST);
        }

        //valid data supplied, now check for previous registration
        List<sym_auth_user> existingUsers = getEntityManagerRepo().findWhere(sym_auth_user.class, asList(
                new Pair<>("user", user.getId()),
                new Pair<>("channel", channel.getId())
        ));

        if (existingUsers != null && existingUsers.size() > 0) {
            logger.info("Channel assignment failed! Auth User with username \"" + user.getUsername()
                    + "\" already exists on channel " + channel.getName());
            return new SymResponseObject<>(PREVIOUS_USERNAME_FOUND);
        }

        user.save();

        return new SymResponseObject<>(SUCCESS, new sym_auth_user(
                user, channel, auth_group, deviceId, null, new Date(), null, null
        ).save());
    }

    static SymResponseObject<sym_auth_user> confirmUserRegistration(Long userId, String authToken,
                                                                    String deviceId, String username,
                                                                    String password, sym_channel channel) {
        if (!isValidAuthData(authToken)) {
            logger.info(format("Registration failed! authToken is invalid (%s).", authToken));
            return new SymResponseObject<sym_auth_user>(INPUT_INVALID_REQUEST)
                    .setMessage("Registration failed! Auth token is invalid (null).");
        }
        if (!isValidAuthData(deviceId)) {
            logger.info(format("Registration failed! deviceId is invalid (%s).", deviceId));
            return new SymResponseObject<sym_auth_user>(INPUT_INVALID_REQUEST)
                    .setMessage("Registration failed! Invalid device id");
        }
        if (!isValidUsername(username)) {
            logger.info(format("Registration failed! Username is invalid (%s).", username));
            return new SymResponseObject<>(INPUT_INVALID_USERNAME);
        }
        if (!isValidPassword(password)) {
            logger.info(format("Registration failed! Password is invalid (%s).", password));
            return new SymResponseObject<>(INPUT_INVALID_PASSWORD);
        }
        if (channel == null) {
            logger.info("Registration failed! Invalid channel (null) specified");
            return new SymResponseObject<>(INPUT_INVALID_REQUEST);
        }

        //verify that user has entered a valid username and auth token
        SymResponseObject<sym_auth_user> symAuthUserResponse = getUserByUsername(username, channel);
        if (symAuthUserResponse.getResponseObject() == null || !symAuthUserResponse.getResponseObject().getUser().getId().equals(userId)) {
            logger.info("Registration failed! User " + username + " with id " + userId + " does not exist.");
            return new SymResponseObject<>(AUTH_NON_EXISTENT);
        }

        sym_user regUser = symAuthUserResponse.getResponseObject().getUser();

        if (!regUser.getUser_status().getId().equals(fromEnum(ACC_INACTIVE).getId())) {
            logger.info("Registration failed! Previous registration found for user " + username + " in status "
                    + regUser.getUser_status().getName());
            return new SymResponseObject<>(PREVIOUS_USERNAME_FOUND);
        }


        if (!regUser.getSalt().equals(authToken)) {
            logger.info("Registration failed! Invalid authToken " + authToken);
            return new SymResponseObject<>(AUTH_AUTHENTICATION_FAILED);
        }

        regUser.setUser_status(fromEnum(ACC_ACTIVE));
        regUser.setSalt(generateSalt());
        regUser.setPassword(hashPassword(password, regUser.getSalt()));
        regUser.setPassword_tries(0);
        regUser.save();
        return new SymResponseObject<>(SUCCESS, symAuthUserResponse.getResponseObject());
    }

    static SymResponseObject<sym_auth_user> changePin(sym_auth_user authUser,
                                                      String newPin, boolean validatePrevious, String oldPassword) {

        logger.info(format("Changing pin for user %s ", authUser.getUser().getUsername()));
        SymResponseObject<sym_auth_user> response;

        if (validatePrevious) {
            response = validatePin(authUser, oldPassword);
            if (!response.getResponseCode().equals(SUCCESS)) {
                return response;
            }
        }

        if (newPin == null) {
            newPin = GenPin();
        }

        if (!isValidPin(newPin)) {
            logger.severe(format("Failed to change user %s pin. Pin was not valid", authUser.getUser().getUsername()));
            return new SymResponseObject<>(INPUT_INVALID_PASSWORD);
        }

        sym_user user = authUser.getUser();

        if (user.getSalt() == null) {
            user.setSalt(generateSalt());
        }

        user.setPin(hashPassword(newPin, authUser.getUser().getSalt()));
        user.setPin_tries(0);
        if (user.getUser_status().equals(findByName(sym_response_code.class, ACC_PASSWORD_TRIES_EXCEEDED.name()))) {
            user.setUser_status(findByName(sym_response_code.class, ACC_ACTIVE.name()));
        }
        user.save().refresh();

        logger.info(format("Pin change response: %s", SUCCESS.getMessage()));
        return new SymResponseObject<>(SUCCESS, authUser);
    }

    static SymResponseObject<sym_auth_user> changePassword(sym_auth_user authUser,
                                                           String newPassword, boolean validatePrevious, String oldPassword) {

        logger.info(format("Changing password for user %s ", authUser.getUser().getUsername()));
        SymResponseObject<sym_auth_user> response;

        if (validatePrevious) {
            response = validatePassword(authUser, oldPassword);
            if (!response.getResponseCode().equals(SUCCESS)) {
                return response;
            }
        }

        if (newPassword == null) {
            newPassword = Gen();
        }

        if (!isValidPassword(newPassword)) {
            logger.severe(format("Failed to change user %s pin. Password was not valid", authUser.getUser().getUsername()));
            return new SymResponseObject<>(INPUT_INVALID_PASSWORD);
        }

        sym_user user = authUser.getUser();

        if (user.getSalt() == null) {
            user.setSalt(generateSalt());
        }

        user.setPassword(hashPassword(newPassword, authUser.getUser().getSalt()));
        user.setPassword_tries(0);
        if (user.getUser_status().equals(findByName(sym_response_code.class, ACC_PASSWORD_TRIES_EXCEEDED.name()))) {
            user.setUser_status(findByName(sym_response_code.class, ACC_ACTIVE.name()));
        }
        user.save().refresh();

        logger.info(format("Password change response: %s", SUCCESS.getMessage()));
        return new SymResponseObject<>(SUCCESS, authUser);

    }

    static String hashPassword(String rawPassword, String salt) {
        logger.info(format("Hashing password %s with salt %s", rawPassword, salt));
        return hashWithSalt(rawPassword, DEFAULT_SECURITY_HASH, salt.getBytes());
    }

    static SymResponseObject<sym_auth_user> validatePassword(sym_auth_user authUser, String password) {

        logger.info(format("Validating password for auth user %s ", authUser.getId()));
        SymResponseObject<sym_auth_user> response = new SymResponseObject<>(GENERAL_ERROR, authUser);

        sym_user symUser = authUser.getUser();

        authUser.setLast_auth_date(new Date());
        if (!symUser.getUser_status().getId().equals(fromEnum(ACC_ACTIVE).getId())) {
            logger.info("Account is not active. Setting response to " + authUser.getUser().getUser_status().getName());
            response.setResponseCode(SymResponseCode.valueOf(symUser.getUser_status().getId()));
        } else {
            int passwordTries = symUser.getPassword_tries();

            if (passwordTries >= parseInt(getSymConfigDao().getConfig(CONFIG_MAX_PASSWORD_TRIES))) {
                logger.info("Authentication failed! Password tries exceeded");
                response.setResponseCode(ACC_PASSWORD_TRIES_EXCEEDED);
            } else if (!isValidPassword(password)) {
                logger.info("Authentication failed! Password format was invalid");
                response.setResponseCode(INPUT_INVALID_REQUEST).setMessage("Password format was invalid");
                symUser.setPassword_tries(++passwordTries);
                if (symUser.getPassword_tries() >= parseInt(getSymConfigDao().getConfig(CONFIG_MAX_PASSWORD_TRIES))) {
                    logger.info("Authentication failed! Password format was invalid. Password tries exceeded");
                    symUser.setUser_status(fromEnum(ACC_PASSWORD_TRIES_EXCEEDED));
                }
            } else if (symUser.getPassword().equals(hashPassword(password, symUser.getSalt()))) {
                logger.info("Authentication success");
                response.setResponseCode(SUCCESS);
                logger.info("Updating password tries to 0");
                symUser.setPassword_tries(0);
            } else {
                logger.info("Authentication failed! Incorrect password");
                response.setResponseCode(AUTH_INCORRECT_PASSWORD);
                symUser.setPassword_tries(++passwordTries);
                authUser.setLast_login_date(new Date());
                if (symUser.getPassword_tries() >= parseInt(getSymConfigDao().getConfig(CONFIG_MAX_PASSWORD_TRIES))) {
                    logger.info("Password tries exceeded");
                    symUser.setUser_status(fromEnum(ACC_PASSWORD_TRIES_EXCEEDED));
                }
            }
        }

        symUser.save().refresh();
        authUser.save();

        logger.info("Returning response " + response.getResponseCode().name());
        return response;
    }

    static SymResponseObject<sym_auth_user> validatePin(sym_auth_user authUser, String pin) {

        logger.info(format("Validating pin for auth user %s ", authUser.getId()));
        SymResponseObject<sym_auth_user> response = new SymResponseObject<>(GENERAL_ERROR, authUser);

        sym_user symUser = authUser.getUser();

        authUser.setLast_auth_date(new Date());
        if (!symUser.getUser_status().getId().equals(fromEnum(ACC_ACTIVE).getId())) {
            logger.info("Account is not active. Setting response to " + authUser.getUser().getUser_status().getName());
            response.setResponseCode(SymResponseCode.valueOf(symUser.getUser_status().getId()));
        } else {
            int pinTries = symUser.getPin_tries();

            if (pinTries >= parseInt(getSymConfigDao().getConfig(CONFIG_MAX_PASSWORD_TRIES))) {
                logger.info("Authentication failed! Pin tries exceeded");
                response.setResponseCode(ACC_PASSWORD_TRIES_EXCEEDED);
            } else if (!isValidPin(pin)) {
                logger.info("Authentication failed! Pin format was invalid");
                response.setResponseCode(INPUT_INVALID_REQUEST).setMessage("Pin format was invalid");
                symUser.setPin_tries(++pinTries);
                if (symUser.getPin_tries() >= parseInt(getSymConfigDao().getConfig(CONFIG_MAX_PASSWORD_TRIES))) {
                    logger.info("Authentication failed! Pin format was invalid. Pin tries exceeded");
                    symUser.setUser_status(fromEnum(ACC_PASSWORD_TRIES_EXCEEDED));
                }
            } else if (symUser.getPin().equals(hashPassword(pin, symUser.getSalt()))) {
                logger.info("Authentication success");
                response.setResponseCode(SUCCESS);
                logger.info("Updating pin tries to 0");
                symUser.setPin_tries(0);
            } else {
                logger.info("Authentication failed! Incorrect pin");
                response.setResponseCode(AUTH_INCORRECT_PASSWORD);
                symUser.setPin_tries(++pinTries);
                authUser.setLast_login_date(new Date());
                if (symUser.getPin_tries() >= parseInt(getSymConfigDao().getConfig(CONFIG_MAX_PASSWORD_TRIES))) {
                    logger.info("Pin tries exceeded");
                    symUser.setUser_status(fromEnum(ACC_PASSWORD_TRIES_EXCEEDED));
                }
            }
        }

        symUser.save().refresh();
        authUser.save();

        logger.info("Returning response " + response.getResponseCode().name());
        return response;
    }

    static String generateSalt() {
        return generateIV();
    }
}
