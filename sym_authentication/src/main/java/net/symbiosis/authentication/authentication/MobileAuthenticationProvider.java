package net.symbiosis.authentication.authentication;

/**************************************************************************
 *                                                                        *
 * Created:     2016/01/01                                                *
 * Author:      Tich de Blak (Tsungai Kaviya)                             *
 *                                                                        *
 *************************************************************************/

import net.symbiosis.common.structure.Pair;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.core_lib.utilities.SymValidator;
import net.symbiosis.persistence.entity.complex_type.device.sym_device_phone;
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
import java.util.Date;
import java.util.List;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static net.symbiosis.authentication.authentication.SymbiosisAuthenticator.registerUser;
import static net.symbiosis.common.configuration.Configuration.getProperty;
import static net.symbiosis.common.configuration.NetworkUtilities.sendEmail;
import static net.symbiosis.common.configuration.NetworkUtilities.sendEmailAlert;
import static net.symbiosis.common.mail.EMailer.CONTENT_TYPE_HTML;
import static net.symbiosis.core_lib.enumeration.SymChannel.SMART_PHONE;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static net.symbiosis.core_lib.enumeration.SystemType.SYMBIOSIS;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;

public class MobileAuthenticationProvider extends SymChainAuthenticationProvider {

    private static final sym_channel CHANNEL = fromEnum(SMART_PHONE);

    @Override
    protected void initializeAuthenticationChain() {
        authenticationChain.put(CHANNEL, new ArrayList<>(singletonList(this::startSession)));
    }

    public MobileAuthenticationProvider(sym_request_response_log requestResponseLog, String username, String pin, String deviceId) {
        super(requestResponseLog, CHANNEL);
        setAuthUsername(username);
        setAuthPassword(pin);
        setDeviceId(deviceId);
    }

    public MobileAuthenticationProvider(sym_request_response_log requestResponseLog, sym_auth_user authUser) {
        super(requestResponseLog, CHANNEL);
        setUser(authUser.getUser());
        setAuthUser(authUser);
    }

    public SymResponseObject<sym_auth_user> registerMobileUser(sym_user newUser, String imei, sym_auth_group authGroup, sym_company company) {

        logger.info(format("Performing MOBILE registration for %s %s", newUser.getFirst_name(), newUser.getLast_name()));

        SymResponseObject<sym_auth_user> registrationResponse = registerUser(newUser, fromEnum(SMART_PHONE),
                findByName(sym_auth_group.class, authGroup == null ? getProperty("DefaultMobileGroup") : authGroup.getName()),
                null);

        if (registrationResponse.getResponseCode().getCode() != SUCCESS.getCode()) {
            return registrationResponse;
        }

        symAuthUser = registrationResponse.getResponseObject();
        symUser = registrationResponse.getResponseObject().getUser();

        if (company != null) {
            logger.info(format("Saving company for user %s", newUser.getUsername()));
            company.save();
        }

        logger.info(format("Creating wallet for user %s", newUser.getUsername()));
        sym_wallet wallet = new sym_wallet(new BigDecimal(0), newUser,
                findByName(sym_wallet_group.class, getProperty("DefaultWalletGroup")), company).save();

        newUser.setWallet(wallet).save();

        sym_device_phone registrationPhone;

        if (!SymValidator.isNullOrEmpty(imei)) {
            List<sym_device_phone> existingPhones = getEntityManagerRepo().findWhere(sym_device_phone.class,
                    asList(new Pair<>("imei1", imei), new Pair<>("imei2", imei)),
                    false, false, true, false);
            if (existingPhones != null && existingPhones.size() > 0) {
                for (sym_device_phone existingPhone : existingPhones) {
                    if (!existingPhone.getAuth_user().getId().equals(symAuthUser.getId())) {
                        //new user using this device, disable old user
                        existingPhone.setIs_active(false);
                    }
                }
            }
            registrationPhone = new sym_device_phone(symAuthUser, true, new Date(), new Date(),
                    imei, null,null, null, null, null
            ).save();
        }

        if (newUser.getEmail() == null) {
            return registrationResponse;
        }

        logger.info("Sending MOBILE registration email");

        FileReader fr = null;
        BufferedReader br = null;

        try {

            ClassPathResource resource = new ClassPathResource("authentication/app_reg_success.html");

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
            sendEmail(SYMBIOSIS.name(), new String[]{newUser.getEmail(), getProperty("AlertEmail")},
                    "Welcome to Symbiosis Wallet Payment Platform", registrationEmail.toString(), CONTENT_TYPE_HTML);
        } catch (Exception e) {
            e.printStackTrace();
            sendEmailAlert(SYMBIOSIS.name(), "Failed to send registration email! \r\n", e.getMessage());
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

    public SymResponseObject<sym_auth_user> validatePin(sym_auth_user authUser, String pin) {
        return SymbiosisAuthenticator.validatePin(authUser, pin);
    }

    public SymResponseObject<sym_auth_user> changePin(sym_auth_user authUser, String newPin, boolean validatePrevious, String oldPin) {

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
                            .replaceAll("%contact_address%", getProperty("ContactAddress"))
                            .replaceAll("%support_phone%", getProperty("SupportPhone"))
                            .replaceAll("%support_email%", getProperty("SupportEmail"))).append("\r\n");
                }
                sendEmail(SYMBIOSIS.name(), new String[]{authUser.getUser().getEmail(), getProperty("AlertEmail")},
                        "Pin Changed on Symbiosis' Wallet Platform", registrationEmail.toString(), CONTENT_TYPE_HTML);
            } catch (Exception e) {
                e.printStackTrace();
                sendEmailAlert(SYMBIOSIS.name(), "Failed to send pin changed email! \r\n", e.getMessage());
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
