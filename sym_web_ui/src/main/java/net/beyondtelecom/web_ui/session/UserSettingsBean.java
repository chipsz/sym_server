package net.beyondtelecom.web_ui.session;

import net.beyondtelecom.bt_core_lib.response.BTResponseObject;
import net.beyondtelecom.gopay.bt_authentication.authentication.WebAuthenticationProvider;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_request_response_log;
import net.beyondtelecom.web_ui.common.JSFLoggable;
import net.beyondtelecom.web_ui.request.NewCredentials;
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
import static net.beyondtelecom.bt_core_lib.enumeration.BTChannel.WEB;
import static net.beyondtelecom.bt_core_lib.enumeration.BTEventType.UPDATE_PASSWORD;
import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.AUTH_AUTHENTICATION_FAILED;
import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.SUCCESS;
import static net.beyondtelecom.gopay.bt_persistence.helper.BTEnumHelper.fromEnum;

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
        Date requestTime =  new Date();
        bt_auth_user authUser = sessionBean.getSymbiosisAuthUser();
        bt_user user = sessionBean.getSymbiosisAuthUser().getUser();
        logger.info(format("Changing password for %s", user.getUsername()));

        if (!newCredentials.getNewPassword().equals(newCredentials.getNewConfirmPassword())) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                "Change Password Failed","Password and password confirmation must match"));
            logger.warning("Change Password Failed: Password and password confirmation must match");
            log(fromEnum(UPDATE_PASSWORD), sessionBean.getSymbiosisAuthUser(), fromEnum(AUTH_AUTHENTICATION_FAILED),
                    requestTime, new Date(), "UPDATE PASSWORD",
                    "Change Password Failed: Password and password confirmation must match");
            return;
        }

        String request = format("Change password: %s", user.getUsername());
        bt_request_response_log requestResponseLog = new bt_request_response_log(
                fromEnum(WEB), fromEnum(UPDATE_PASSWORD), request).save();

        requestResponseLog.setAuth_user(authUser);
        requestResponseLog.setSystem_user(authUser.getUser());
        requestResponseLog.save();

        WebAuthenticationProvider authenticationProvider = new WebAuthenticationProvider(requestResponseLog, authUser);
        BTResponseObject<bt_auth_user> passwordResponse = authenticationProvider.changePassword(
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

