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
import static net.symbiosis.common.configuration.Configuration.getProperty;
import static net.symbiosis.common.configuration.NetworkUtilities.sendEmail;
import static net.symbiosis.common.configuration.NetworkUtilities.sendEmailAlert;
import static net.symbiosis.common.mail.EMailer.CONTENT_TYPE_HTML;
import static net.symbiosis.common.utilities.ReferenceGenerator.GenPin;
import static net.symbiosis.core_lib.enumeration.SymChannel.POS_MACHINE;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static net.symbiosis.core_lib.enumeration.SystemType.SYMBIOSIS;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;
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
            findByName(sym_auth_group.class, authGroup == null ? getProperty("DefaultPOSGroup") : authGroup.getName()),
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
            String registrationEmail = "";
            br = new BufferedReader(fr);

            while ((line = br.readLine()) != null) {
                registrationEmail += line.replaceAll("%reg_fname%", user.getFirst_name())
                        .replaceAll("%reg_lname%", user.getLast_name())
                        .replaceAll("%reg_username%", user.getUsername())
                        .replaceAll("%reg_pin%", newPin)
                        .replaceAll("%contact_address%", getProperty("ContactAddress"))
                        .replaceAll("%support_phone%", getProperty("SupportPhone"))
                        .replaceAll("%support_email%", getProperty("SupportEmail")) + "\r\n";
            }
            sendEmail(SYMBIOSIS.name(), new String[] {user.getEmail(), getProperty("AlertEmail")},
                    "Welcome to Empower Jarvis Control Center!", registrationEmail, CONTENT_TYPE_HTML);
        }
        catch (Exception e) {
            e.printStackTrace();
            sendEmailAlert(SYMBIOSIS.name(), "Failed to send registration email! \r\n", e.getMessage());
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
                String registrationEmail = "";
                br = new BufferedReader(fr);

                while ((line = br.readLine()) != null) {
                    registrationEmail += line.replaceAll("%auth_fname%", authUser.getUser().getFirst_name())
                            .replaceAll("%auth_lname%", authUser.getUser().getLast_name())
                            .replaceAll("%auth_username%", authUser.getUser().getUsername())
                            .replaceAll("%auth_pin%", newPin)
                            .replaceAll("%contact_address%", getProperty("ContactAddress"))
                            .replaceAll("%support_phone%", getProperty("SupportPhone"))
                            .replaceAll("%support_email%", getProperty("SupportEmail")) + "\r\n";
                }
                sendEmail(SYMBIOSIS.name(), new String[] {authUser.getUser().getEmail(), getProperty("AlertEmail")},
                        "Pin Changed on Jarvis Control Center", registrationEmail, CONTENT_TYPE_HTML);
            }
            catch (Exception e) {
                e.printStackTrace();
                sendEmailAlert(SYMBIOSIS.name(), "Failed to send pin changed email! \r\n", e.getMessage());
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
