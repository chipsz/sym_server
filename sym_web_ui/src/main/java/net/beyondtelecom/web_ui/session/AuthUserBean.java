package net.beyondtelecom.web_ui.session;

import net.beyondtelecom.bt_core_lib.response.BTResponseObject;
import net.beyondtelecom.gopay.bt_authentication.authentication.GoPayAuthenticationProvider;
import net.beyondtelecom.gopay.bt_authentication.authentication.WebAuthenticationProvider;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_request_response_log;
import net.beyondtelecom.web_ui.common.JSFUpdatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.beyondtelecom.bt_core_lib.enumeration.BTChannel.WEB;
import static net.beyondtelecom.bt_core_lib.enumeration.BTEventType.RESET_PASSWORD;
import static net.beyondtelecom.bt_core_lib.enumeration.BTEventType.RESET_PIN;
import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.SUCCESS;
import static net.beyondtelecom.gopay.bt_common.utilities.ReferenceGenerator.Gen;
import static net.beyondtelecom.gopay.bt_common.utilities.ReferenceGenerator.GenPin;
import static net.beyondtelecom.gopay.bt_persistence.helper.BTEnumHelper.fromEnum;
import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEntityManagerRepo;
import static net.beyondtelecom.web_ui.common.DataTableHeaders.*;

/***************************************************************************
 *                                                                         *
 * Created:     16 / 04 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class AuthUserBean extends JSFUpdatable {

    private static final Logger logger = Logger.getLogger(AuthUserBean.class.getSimpleName());
    private static final String TABLE_NAME = "User Authentication";
    private List<bt_auth_user> authUsers;
    private bt_auth_user selectedAuthUser;

    @Autowired
    public AuthUserBean(SessionBean sessionBean) { super(sessionBean); }

    @PostConstruct
    public void init() {
        notUpdatableColumns.add(HEADER_TEXT_ID);
        updatableColumns.add(HEADER_TEXT_USERNAME);
        updatableColumns.add(HEADER_TEXT_FIRST_NAME);
        updatableColumns.add(HEADER_TEXT_LAST_NAME);
        updatableColumns.add(HEADER_TEXT_AUTH_GROUP);
        notUpdatableColumns.add(HEADER_TEXT_CHANNEL);
        updatableColumns.add(HEADER_TEXT_USER_STATUS);
        updatableColumns.add(HEADER_TEXT_PASSWORD_TRIES);
        updatableColumns.add(HEADER_TEXT_PIN_TRIES);
        notUpdatableColumns.add(HEADER_TEXT_REG_DATE);
        notUpdatableColumns.add(HEADER_TEXT_LAST_AUTH_DATE);
        initializeUsers();
    }

    public void initializeUsers() { authUsers = getEntityManagerRepo().findAll(bt_auth_user.class, true); }

    public List<bt_auth_user> getAuthUsers() { return authUsers; }

    public void setAuthUsers(List<bt_auth_user> authUsers) { this.authUsers = authUsers; }

    public bt_auth_user getSelectedAuthUser() { return selectedAuthUser; }

    public void setSelectedAuthUser(bt_auth_user selectedAuthUser) { this.selectedAuthUser = selectedAuthUser; }

    public void resetPassword() {
        logger.info(format("Resetting password for %s", selectedAuthUser.getUser().getUsername()));

        bt_request_response_log passChangeLog = new bt_request_response_log(
                fromEnum(WEB), fromEnum(RESET_PASSWORD),
                format("Reset password for %s", selectedAuthUser.getUser().getUsername())).save();

        WebAuthenticationProvider authenticationProvider = new WebAuthenticationProvider(passChangeLog, selectedAuthUser);

        BTResponseObject<bt_auth_user> passResponse = authenticationProvider.
                changePassword(selectedAuthUser, Gen(), false, null);

        if (passResponse.getResponseCode().equals(SUCCESS)) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO,
                "Password Reset Successful",
                "Password Reset Successful. New password emailed to " + selectedAuthUser.getUser().getEmail()));
            passChangeLog.setOutgoing_response("Password Reset Successful. New password emailed to " + selectedAuthUser.getUser().getEmail());

        } else {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                "Failed to Reset Password",
                "Failed to Reset Password! " + passResponse.getMessage()));
            passChangeLog.setOutgoing_response("Failed to Reset Password! " + passResponse.getMessage());
        }
        passChangeLog.setResponse_code(fromEnum(passResponse.getResponseCode()));
        passChangeLog.setSystem_user(selectedAuthUser.getUser());
        passChangeLog.setAuth_user(selectedAuthUser);
        passChangeLog.setOutgoing_response_time(new Date());
        passChangeLog.save();
        logger.info("Password change result: " + passResponse.getMessage());
    }

    public void resetPin() {
        logger.info(format("Resetting pin for %s", selectedAuthUser.getUser().getUsername()));

        bt_request_response_log pinChangeLog = new bt_request_response_log(
                fromEnum(WEB), fromEnum(RESET_PIN),
                format("Reset pin for %s", selectedAuthUser.getUser().getUsername())).save();

        GoPayAuthenticationProvider authenticationProvider = new GoPayAuthenticationProvider(pinChangeLog,
            selectedAuthUser.getDevice_id(), selectedAuthUser.getUser().getUsername(), null);

        BTResponseObject<bt_auth_user> pinResponse = authenticationProvider.
                changePin(selectedAuthUser, GenPin(), false, null);

        if (pinResponse.getResponseCode().equals(SUCCESS)) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO,
                "Pin Reset Successful",
                "Pin Reset Successful. New pin emailed to " + selectedAuthUser.getUser().getEmail()));
            pinChangeLog.setOutgoing_response("Pin Reset Successful. New pin emailed to " + selectedAuthUser.getUser().getEmail());
        } else {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                    "Failed to Reset Pin",
                    "Failed to Reset Pin! " + pinResponse.getMessage()));
            pinChangeLog.setOutgoing_response("Failed to Reset Pin! " + pinResponse.getMessage());
        }
        pinChangeLog.setResponse_code(fromEnum(pinResponse.getResponseCode()));
        pinChangeLog.setSystem_user(selectedAuthUser.getUser());
        pinChangeLog.setAuth_user(selectedAuthUser);
        pinChangeLog.setOutgoing_response_time(new Date());
        pinChangeLog.save();
        logger.info("Pin change result: " + pinResponse.getMessage());
    }

    @Override
    public String getTableDescription() { return TABLE_NAME; }
}

