package net.symbiosis.authentication.authentication;

/* *************************************************************************
 * Created:     2016/01/01                                                 *
 * Author:      Tich de Blak (Tsungai Kaviya)                              *
 */

import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_company;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet_group;
import net.symbiosis.persistence.entity.enumeration.sym_auth_group;
import net.symbiosis.persistence.entity.enumeration.sym_channel;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.math.BigDecimal;
import java.util.ArrayList;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static net.symbiosis.authentication.authentication.SymbiosisAuthenticator.registerUser;
import static net.symbiosis.common.configuration.NetworkUtilities.sendEmail;
import static net.symbiosis.common.configuration.NetworkUtilities.sendEmailAlert;
import static net.symbiosis.common.mail.EMailer.CONTENT_TYPE_HTML;
import static net.symbiosis.core_lib.enumeration.DBConfigVars.*;
import static net.symbiosis.core_lib.enumeration.SymChannel.WEB;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;
import static net.symbiosis.persistence.helper.DaoManager.getSymConfigDao;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;

public class WebAuthenticationProvider extends SymChainAuthenticationProvider {

    private static final sym_channel CHANNEL = fromEnum(WEB);

    @Override
    protected void initializeAuthenticationChain() {
        authenticationChain.put(CHANNEL, new ArrayList<>(singletonList(this::startSession)));
    }

    public WebAuthenticationProvider(sym_request_response_log requestResponseLog, String username, String password, String deviceId) {
        super(requestResponseLog, CHANNEL);
        setAuthUsername(username);
        setAuthPassword(password);
        setDeviceId(deviceId);
    }

    public WebAuthenticationProvider(sym_request_response_log requestResponseLog, sym_auth_user authUser) {
        super(requestResponseLog, CHANNEL);
        setUser(authUser.getUser());
        setAuthUser(authUser);
    }

    public SymResponseObject<sym_auth_user> registerWebUser(sym_user newUser,
                                                            sym_company newCompany, sym_auth_group authGroup, sym_wallet wallet) {

        logger.info(format("Performing WEB registration for %s %s", newUser.getFirst_name(), newUser.getLast_name()));

        
        
        SymResponseObject<sym_auth_user> registrationResponse = registerUser(newUser, fromEnum(WEB),
                findByName(sym_auth_group.class, authGroup == null ? getSymConfigDao().getConfig(CONFIG_DEFAULT_WEB_AUTH_GROUP) : authGroup.getName()),
                null);

        if (registrationResponse.getResponseCode().getCode() != SUCCESS.getCode()) {
            return registrationResponse;
        }

        symAuthUser = registrationResponse.getResponseObject();
        symUser = registrationResponse.getResponseObject().getUser();

        logger.info(format("Saving user company %s", newCompany.getCompany_name()));
        newCompany.save();

        if (wallet == null) {
            logger.info(format("Creating wallet for user %s", newUser.getUsername()));
            wallet = new sym_wallet(new BigDecimal(0), newUser,
                    findByName(sym_wallet_group.class, getSymConfigDao().getConfig(CONFIG_DEFAULT_WALLET_GROUP)), newCompany).save();
        } else {
            logger.info(format("Setting user wallet to %s for user %s", wallet.getId(), newUser.getUsername()));
        }

        newUser.setWallet(wallet).save();

        logger.info("Sending WEB registration email");

        FileReader fr = null;
        BufferedReader br = null;
        try {

            ClassPathResource resource = new ClassPathResource("authentication/web_reg_success.html");

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
                        .replaceAll("%contact_address%", getSymConfigDao().getConfig(CONFIG_CONTACT_ADDRESS))
                        .replaceAll("%support_phone%", getSymConfigDao().getConfig(CONFIG_SUPPORT_PHONE))
                        .replaceAll("%support_email%", getSymConfigDao().getConfig(CONFIG_SUPPORT_EMAIL))).append("\r\n");
            }
            sendEmail(getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME), new String[]{newUser.getEmail(),
                      getSymConfigDao().getConfig(CONFIG_EMAIL_ALERT_TO)}, "Welcome to " +
                      getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME) + " Control Center!", registrationEmail.toString(), CONTENT_TYPE_HTML);
        } catch (Exception e) {
            e.printStackTrace();
            sendEmailAlert(getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME), "Failed to send registration email! \r\n", e.getMessage());
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
                if (fr != null) {
                    fr.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        return registrationResponse;
    }

    public SymResponseObject<sym_auth_user> changePassword(sym_auth_user authUser,
                                                           String newPassword, boolean validatePrevious, String oldPassword) {

        SymResponseObject<sym_auth_user> passResponse =
                SymbiosisAuthenticator.changePassword(authUser, newPassword, validatePrevious, oldPassword);

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
                            .replaceAll("%contact_address%", getSymConfigDao().getConfig(CONFIG_CONTACT_ADDRESS))
                            .replaceAll("%support_phone%", getSymConfigDao().getConfig(CONFIG_SUPPORT_PHONE))
                            .replaceAll("%support_email%", getSymConfigDao().getConfig(CONFIG_SUPPORT_EMAIL)) + "\r\n";
                }
                sendEmail(getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME), new String[]{authUser.getUser().getEmail(),
		                  getSymConfigDao().getConfig(CONFIG_EMAIL_ALERT_TO)}, "Password Changed on " +
		                  getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME) + " Control Center", registrationEmail, CONTENT_TYPE_HTML);
            } catch (Exception e) {
                e.printStackTrace();
                sendEmailAlert(getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME), "Failed to send password changed email! \r\n", e.getMessage());
            } finally {
                try {
                    if (br != null) {
                        br.close();
                    }
                    if (fr != null) {
                        fr.close();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        return passResponse;
    }
}
