package net.beyondtelecom.gopay.bt_authentication.authentication;

/* *************************************************************************
 * Created:     2016/01/01                                                 *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 *                                      *
 * Website:     http://www.beyondtelecom.net                                    *
 * Contact:     beyondtelecom@gmail.com                                         *
*/

import net.beyondtelecom.bt_core_lib.response.BTResponseObject;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.*;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_request_response_log;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_auth_group;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_channel;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static net.beyondtelecom.bt_core_lib.enumeration.BTChannel.SMART_PHONE;
import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.SUCCESS;
import static net.beyondtelecom.bt_core_lib.enumeration.BTSystemType.BEYOND_TELECOMS;
import static net.beyondtelecom.gopay.bt_authentication.authentication.BTAuthenticator.registerUser;
import static net.beyondtelecom.gopay.bt_common.configuration.Configuration.getProperty;
import static net.beyondtelecom.gopay.bt_common.configuration.NetworkUtilities.sendEmail;
import static net.beyondtelecom.gopay.bt_common.configuration.NetworkUtilities.sendEmailAlert;
import static net.beyondtelecom.gopay.bt_common.mail.EMailer.CONTENT_TYPE_HTML;
import static net.beyondtelecom.gopay.bt_persistence.dao.EnumEntityRepoManager.findByName;
import static net.beyondtelecom.gopay.bt_persistence.helper.BTEnumHelper.fromEnum;

public class GoPayAuthenticationProvider extends BTChainAuthenticationProvider {

    private static final bt_channel CHANNEL = fromEnum(SMART_PHONE);

	@Override
	protected void initializeAuthenticationChain() {
		authenticationChain.put(CHANNEL, new ArrayList<>(singletonList(this::startSession)));
	}

    public GoPayAuthenticationProvider(bt_request_response_log requestResponseLog, String username, String pin, String deviceId) {
		super(requestResponseLog, CHANNEL);
		setAuthUsername(username);
		setAuthPassword(pin);
		setDeviceId(deviceId);
	}

    public GoPayAuthenticationProvider(bt_request_response_log requestResponseLog, bt_auth_user authUser) {
		super(requestResponseLog, CHANNEL);
		setUser(authUser.getUser());
		setAuthUser(authUser);
	}

    public BTResponseObject<bt_auth_user> registerMobileUser(bt_user newUser, bt_auth_group authGroup, bt_company company) {

        logger.info(format("Performing MOBILE registration for %s %s", newUser.getFirst_name(), newUser.getLast_name()));

        BTResponseObject<bt_auth_user> registrationResponse = registerUser(newUser, fromEnum(SMART_PHONE),
                findByName(bt_auth_group.class, authGroup == null ? getProperty("DefaultMobileGroup") : authGroup.getName()),
                null);

        if (registrationResponse.getResponseCode().getCode() != SUCCESS.getCode()) {
            return registrationResponse;
        }

        btAuthUser = registrationResponse.getResponseObject();
        btUser = registrationResponse.getResponseObject().getUser();

        if (company != null) {
            logger.info(format("Saving company for user %s", newUser.getUsername()));
            company.save();
        }

        logger.info(format("Creating wallet for user %s", newUser.getUsername()));
        bt_wallet wallet = new bt_wallet(new BigDecimal(0), newUser,
            findByName(bt_wallet_group.class, getProperty("DefaultWalletGroup")), company).save();

        newUser.setWallet(wallet).save();

        if (newUser.getEmail() == null) { return registrationResponse; }

        logger.info("Sending MOBILE registration email");

        FileReader fr = null;
        BufferedReader br = null;

        try {

            ClassPathResource resource = new ClassPathResource("authentication/app_reg_success.html" );

            fr = new FileReader(resource.getFile().getAbsolutePath());
            br = new BufferedReader(fr);
            String line;
            StringBuilder registrationEmail = new StringBuilder();
            br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {
                registrationEmail.append(line.replaceAll("%reg_fname%", newUser.getFirst_name())
                        .replaceAll("%reg_lname%", newUser.getLast_name())
                        .replaceAll("%reg_username%", newUser.getUsername())
                        .replaceAll("%reg_userId%", String.valueOf(registrationResponse.getResponseObject().getId().intValue()))
                        .replaceAll("%reg_authToken%", registrationResponse.getResponseObject().getAuth_token())
                        .replaceAll("%contact_address%", getProperty("ContactAddress"))
                        .replaceAll("%support_phone%", getProperty("SupportPhone"))
                        .replaceAll("%support_email%", getProperty("SupportEmail"))).append("\r\n");
            }
            sendEmail(BEYOND_TELECOMS.name(), new String[] {newUser.getEmail(), getProperty("AlertEmail")},
                    "Welcome to Symbiosis' Wallet Payment Platform", registrationEmail.toString(), CONTENT_TYPE_HTML);
        }
        catch (Exception e) {
            e.printStackTrace();
            sendEmailAlert(BEYOND_TELECOMS.name(), "Failed to send registration email! \r\n", e.getMessage());
        }
        finally {
            try {
                if (br != null) { br.close(); }
                if (fr != null) { fr.close(); }
            }
            catch (Exception ex) { ex.printStackTrace(); }
        }

        return registrationResponse;
    }

    public BTResponseObject<bt_auth_user> validatePin(bt_auth_user authUser, String pin) {
	    return BTAuthenticator.validatePin(authUser, pin);
    }

    public BTResponseObject<bt_auth_user> changePin(bt_auth_user authUser, String newPin, boolean validatePrevious, String oldPin) {

        BTResponseObject<bt_auth_user> passResponse =
                BTAuthenticator.changePin(authUser, newPin, validatePrevious, oldPin);

        if (passResponse.getResponseCode().equals(SUCCESS)) {
            logger.info("Sending pin changed email");

            FileReader fr = null;
            BufferedReader br = null;
            try {

                ClassPathResource resource = new ClassPathResource("authentication/pin_change_success.html");

                fr = new FileReader(resource.getFile().getAbsolutePath());
                br = new BufferedReader(fr);
                String line;
                StringBuilder registrationEmail = new StringBuilder();
                br = new BufferedReader(fr);

                while ((line = br.readLine()) != null) {
                    registrationEmail.append(line.replaceAll("%auth_fname%", authUser.getUser().getFirst_name())
                            .replaceAll("%auth_lname%", authUser.getUser().getLast_name())
                            .replaceAll("%auth_username%", authUser.getUser().getUsername())
                            .replaceAll("%auth_pin%", newPin)
                            .replaceAll("%contact_address%", getProperty("ContactAddress"))
                            .replaceAll("%support_phone%", getProperty("SupportPhone"))
                            .replaceAll("%support_email%", getProperty("SupportEmail"))).append("\r\n");
                }
                sendEmail(BEYOND_TELECOMS.name(), new String[] {authUser.getUser().getEmail(), getProperty("AlertEmail")},
                        "Pin Changed on Symbiosis' Wallet Platform", registrationEmail.toString(), CONTENT_TYPE_HTML);
            }
            catch (Exception e) {
                e.printStackTrace();
                sendEmailAlert(BEYOND_TELECOMS.name(), "Failed to send pin changed email! \r\n", e.getMessage());
            }
            finally {
                try {
                    if (br != null) { br.close(); }
                    if (fr != null) { fr.close(); }
                }
                catch (Exception ex) { ex.printStackTrace(); }
            }
        }

        return passResponse;
    }
}
