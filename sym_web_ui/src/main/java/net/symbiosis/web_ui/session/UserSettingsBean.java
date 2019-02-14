package net.symbiosis.web_ui.session;

import net.symbiosis.authentication.authentication.WebAuthenticationProvider;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.web_ui.common.JSFLoggable;
import net.symbiosis.web_ui.request.NewCredentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.Date;
import java.util.logging.Logger;

import static java.lang.String.format;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.symbiosis.core_lib.enumeration.SymChannel.WEB;
import static net.symbiosis.core_lib.enumeration.SymEventType.UPDATE_PASSWORD;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.AUTH_AUTHENTICATION_FAILED;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class UserSettingsBean implements JSFLoggable, Serializable {

    private static final Logger logger = Logger.getLogger(UserSettingsBean.class.getSimpleName());
    private final SessionBean sessionBean;
    private final NewCredentials newCredentials;

    @Autowired
    public UserSettingsBean(SessionBean sessionBean, NewCredentials newCredentials) {
        this.sessionBean = sessionBean;
        this.newCredentials = newCredentials;
    }

    public NewCredentials getNewCredentials() {
        return newCredentials;
    }

    public void changePassword() {
        Date requestTime = new Date();
        sym_auth_user authUser = sessionBean.getSymbiosisAuthUser();
        sym_user user = sessionBean.getSymbiosisAuthUser().getUser();
        logger.info(format("Changing password for %s", user.getUsername()));

        if (!newCredentials.getNewPassword().equals(newCredentials.getNewConfirmPassword())) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                    "Change Password Failed", "Password and password confirmation must match"));
            logger.warning("Change Password Failed: Password and password confirmation must match");
            log(fromEnum(UPDATE_PASSWORD), sessionBean.getSymbiosisAuthUser(), fromEnum(AUTH_AUTHENTICATION_FAILED),
                    requestTime, new Date(), "UPDATE PASSWORD",
                    "Change Password Failed: Password and password confirmation must match");
            return;
        }

        String request = format("Change password: %s", user.getUsername());
        sym_request_response_log requestResponseLog = new sym_request_response_log(
                fromEnum(WEB), fromEnum(UPDATE_PASSWORD), request).save();

        requestResponseLog.setAuth_user(authUser);
        requestResponseLog.setSystem_user(authUser.getUser());
        requestResponseLog.save();

        WebAuthenticationProvider authenticationProvider = new WebAuthenticationProvider(requestResponseLog, authUser);
        SymResponseObject<sym_auth_user> passwordResponse = authenticationProvider.changePassword(
                authUser, newCredentials.getNewPassword(), true, newCredentials.getOldPassword());

        requestResponseLog.setOutgoing_response(passwordResponse.getMessage());
        requestResponseLog.setOutgoing_response_time(new Date());
        requestResponseLog.setResponse_code(fromEnum(passwordResponse.getResponseCode()));
        requestResponseLog.save();

        if (!passwordResponse.getResponseCode().equals(SUCCESS)) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                    "Change Password Failed", passwordResponse.getMessage()));
            logger.warning("Change Password Failed: " + passwordResponse.getMessage());
            log(fromEnum(UPDATE_PASSWORD), sessionBean.getSymbiosisAuthUser(), fromEnum(passwordResponse.getResponseCode()),
                    requestTime, new Date(), "UPDATE PASSWORD",
                    "Change Password Failed: " + passwordResponse.getMessage());
            return;
        }

        getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO,
                "Password changed successfully", "Password changed successfully"));
        logger.info("Password changed successfully");
        log(fromEnum(UPDATE_PASSWORD), sessionBean.getSymbiosisAuthUser(), fromEnum(SUCCESS),
                requestTime, new Date(), "UPDATE PASSWORD",
                "Password changed successfully");
    }
}

