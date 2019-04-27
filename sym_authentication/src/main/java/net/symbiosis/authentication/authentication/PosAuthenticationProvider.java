package net.symbiosis.authentication.authentication;

/* *************************************************************************
 * Created:     2016/01/01                                                 *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
*/

import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.enumeration.sym_auth_group;
import net.symbiosis.persistence.entity.enumeration.sym_channel;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static net.symbiosis.authentication.authentication.SymbiosisAuthenticator.assignChannel;
import static net.symbiosis.common.configuration.NetworkUtilities.sendEmail;
import static net.symbiosis.common.configuration.NetworkUtilities.sendEmailAlert;
import static net.symbiosis.common.mail.EMailer.CONTENT_TYPE_HTML;
import static net.symbiosis.core_lib.enumeration.DBConfigVars.*;
import static net.symbiosis.core_lib.enumeration.SymChannel.POS_MACHINE;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static net.symbiosis.core_lib.utilities.ReferenceGenerator.GenPin;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;
import static net.symbiosis.persistence.helper.DaoManager.getSymConfigDao;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;

public class PosAuthenticationProvider extends SymChainAuthenticationProvider {

    private static final sym_channel CHANNEL = fromEnum(POS_MACHINE);

	@Override
	protected void initializeAuthenticationChain() {
		authenticationChain.put(CHANNEL, new ArrayList<>(singletonList(this::validatePin)));
	}

	public PosAuthenticationProvider(sym_request_response_log requestResponseLog,
                                     String deviceId, String username, String pin) {
		super(requestResponseLog, CHANNEL);
        setDeviceId(deviceId);
        setAuthUsername(username);
        setAuthPassword(pin);
    }

    public SymResponseObject<sym_auth_user> assignPOSChannel(sym_user user, sym_auth_group authGroup) {

        logger.info(format("Performing POS registration for %s %s", user.getFirst_name(), user.getLast_name()));

        SymResponseObject<sym_auth_user> registrationResponse = assignChannel(user, fromEnum(POS_MACHINE),
            findByName(sym_auth_group.class, authGroup == null ? getSymConfigDao().getConfig(CONFIG_DEFAULT_POS_MACHINE_AUTH_GROUP) : authGroup.getName()),
            null);

        if (registrationResponse.getResponseCode().getCode() != SUCCESS.getCode()) {
            return registrationResponse;
        }

        symAuthUser = registrationResponse.getResponseObject();
        symUser = registrationResponse.getResponseObject().getUser();

        String newPin = GenPin();
        logger.info("Setting channel pin to " + newPin);
        SymbiosisAuthenticator.changePin(symAuthUser, newPin, false, null);

        logger.info("Sending POS registration email");

        FileReader fr = null;
        BufferedReader br = null;
        try {

            ClassPathResource resource = new ClassPathResource("authentication/pos_reg_success.html" );

            fr = new FileReader(resource.getFile().getAbsolutePath());
            br = new BufferedReader(fr);
            String line;
            StringBuilder registrationEmail = new StringBuilder();
            br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {
                registrationEmail.append(line.replaceAll("%reg_fname%", user.getFirst_name())
                        .replaceAll("%reg_lname%", user.getLast_name())
                        .replaceAll("%reg_username%", user.getUsername())
                        .replaceAll("%reg_pin%", newPin)
                        .replaceAll("%contact_address%", getSymConfigDao().getConfig(CONFIG_CONTACT_ADDRESS))
                        .replaceAll("%support_phone%", getSymConfigDao().getConfig(CONFIG_SUPPORT_PHONE))
                        .replaceAll("%support_email%", getSymConfigDao().getConfig(CONFIG_SUPPORT_EMAIL))).append("\r\n");
            }
            sendEmail(getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME), new String[] {user.getEmail(), getSymConfigDao().getConfig(CONFIG_EMAIL_ALERT_TO)},
                    "Welcome to " + getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME), registrationEmail.toString(), CONTENT_TYPE_HTML);
        }
        catch (Exception e) {
            e.printStackTrace();
            sendEmailAlert(getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME), "Failed to send registration email! \r\n", e.getMessage());
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

    public SymResponseObject<sym_auth_user> changePin(sym_auth_user authUser,
                                                      String newPin, boolean validatePrevious, String oldPin) {

        SymResponseObject<sym_auth_user> passResponse =
                SymbiosisAuthenticator.changePin(authUser, newPin, validatePrevious, oldPin);

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
                            .replaceAll("%contact_address%", getSymConfigDao().getConfig(CONFIG_CONTACT_ADDRESS))
                            .replaceAll("%support_phone%", getSymConfigDao().getConfig(CONFIG_SUPPORT_PHONE))
                            .replaceAll("%support_email%", getSymConfigDao().getConfig(CONFIG_SUPPORT_EMAIL))).append("\r\n");
                }
                sendEmail(getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME), new String[] {authUser.getUser().getEmail(), getSymConfigDao().getConfig(CONFIG_EMAIL_ALERT_TO)},
                        "Pin changed on " + getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME), registrationEmail.toString(), CONTENT_TYPE_HTML);
            }
            catch (Exception e) {
                e.printStackTrace();
                sendEmailAlert(getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME), "Failed to send pin changed email! \r\n", e.getMessage());
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
