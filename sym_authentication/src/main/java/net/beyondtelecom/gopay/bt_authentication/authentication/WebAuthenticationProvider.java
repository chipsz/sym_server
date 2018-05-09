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
import static net.beyondtelecom.bt_core_lib.enumeration.BTChannel.WEB;
import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.SUCCESS;
import static net.beyondtelecom.bt_core_lib.enumeration.BTSystemType.BEYOND_TELECOMS;
import static net.beyondtelecom.gopay.bt_authentication.authentication.BTAuthenticator.registerUser;
import static net.beyondtelecom.gopay.bt_common.configuration.Configuration.getProperty;
import static net.beyondtelecom.gopay.bt_common.configuration.NetworkUtilities.sendEmail;
import static net.beyondtelecom.gopay.bt_common.configuration.NetworkUtilities.sendEmailAlert;
import static net.beyondtelecom.gopay.bt_common.mail.EMailer.CONTENT_TYPE_HTML;
import static net.beyondtelecom.gopay.bt_persistence.dao.EnumEntityRepoManager.findByName;
import static net.beyondtelecom.gopay.bt_persistence.helper.BTEnumHelper.fromEnum;

public class WebAuthenticationProvider extends BTChainAuthenticationProvider {

    private static final bt_channel CHANNEL = fromEnum(WEB);

	@Override
	protected void initializeAuthenticationChain() {
		authenticationChain.put(CHANNEL, new ArrayList<>(singletonList(this::startSession)));
	}

    public WebAuthenticationProvider(bt_request_response_log requestResponseLog, String username, String password, String deviceId) {
		super(requestResponseLog, CHANNEL);
		setAuthUsername(username);
		setAuthPassword(password);
		setDeviceId(deviceId);
	}

    public WebAuthenticationProvider(bt_request_response_log requestResponseLog, bt_auth_user authUser) {
		super(requestResponseLog, CHANNEL);
		setUser(authUser.getUser());
		setAuthUser(authUser);
	}

    public BTResponseObject<bt_auth_user> registerWebUser(bt_user newUser,
                                                          bt_company newCompany, bt_auth_group authGroup, bt_wallet wallet) {

        logger.info(format("Performing WEB registration for %s %s", newUser.getFirst_name(), newUser.getLast_name()));

        BTResponseObject<bt_auth_user> registrationResponse = registerUser(newUser, fromEnum(WEB),
                findByName(bt_auth_group.class, authGroup == null ? getProperty("DefaultWebGroup") : authGroup.getName()),
                null);

        if (registrationResponse.getResponseCode().getCode() != SUCCESS.getCode()) {
            return registrationResponse;
        }

        btAuthUser = registrationResponse.getResponseObject();
        btUser = registrationResponse.getResponseObject().getUser();

        logger.info(format("Saving user company %s", newCompany.getCompany_name()));
        newCompany.save();

        if (wallet == null) {
            logger.info(format("Creating wallet for user %s", newUser.getUsername()));
            wallet = new bt_wallet(new BigDecimal(0), newUser,
                    findByName(bt_wallet_group.class, getProperty("DefaultWalletGroup")), newCompany).save();
        } else {
            logger.info(format("Setting user wallet to %s for user %s", wallet.getId(), newUser.getUsername()));
        }

        newUser.setWallet(wallet).save();

        logger.info("Sending WEB registration email");

        FileReader fr = null;
        BufferedReader br = null;
        try {

            ClassPathResource resource = new ClassPathResource("authentication/web_reg_success.html" );

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
                    "Welcome to Symbiosis' Control Center!", registrationEmail.toString(), CONTENT_TYPE_HTML);
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

    public BTResponseObject<bt_auth_user> changePassword(bt_auth_user authUser,
                                                            String newPassword, boolean validatePrevious, String oldPassword) {

        BTResponseObject<bt_auth_user> passResponse =
                BTAuthenticator.changePassword(authUser, newPassword, validatePrevious, oldPassword);

        if (passResponse.getResponseCode().equals(SUCCESS)) {
            logger.info("Sending password changed email");

            FileReader fr = null;
            BufferedReader br = null;
            try {

                ClassPathResource resource = new ClassPathResource("authentication/pass_change_success.html");

                fr = new FileReader(resource.getFile().getAbsolutePath());
                br = new BufferedReader(fr);
                String line;
                String registrationEmail = "";
                br = new BufferedReader(fr);

                while ((line = br.readLine()) != null) {
                    registrationEmail += line.replaceAll("%auth_fname%", authUser.getUser().getFirst_name())
                            .replaceAll("%auth_lname%", authUser.getUser().getLast_name())
                            .replaceAll("%auth_username%", authUser.getUser().getUsername())
                            .replaceAll("%auth_password%", newPassword)
                            .replaceAll("%contact_address%", getProperty("ContactAddress"))
                            .replaceAll("%support_phone%", getProperty("SupportPhone"))
                            .replaceAll("%support_email%", getProperty("SupportEmail")) + "\r\n";
                }
                sendEmail(BEYOND_TELECOMS.name(), new String[] {authUser.getUser().getEmail(), getProperty("AlertEmail")},
                        "Password Changed on Symbiosis Control Center", registrationEmail, CONTENT_TYPE_HTML);
            }
            catch (Exception e) {
                e.printStackTrace();
                sendEmailAlert(BEYOND_TELECOMS.name(), "Failed to send password changed email! \r\n", e.getMessage());
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
