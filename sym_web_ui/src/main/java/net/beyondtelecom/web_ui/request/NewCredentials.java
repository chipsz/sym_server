package net.beyondtelecom.web_ui.request;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/***************************************************************************
 *                                                                         *
 * Created:     09 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope(proxyMode = ScopedProxyMode.TARGET_CLASS, value = "request")
public class NewCredentials implements Serializable {

    private String oldPassword;
    private String newPassword;
    private String newConfirmPassword;

    public String getOldPassword() { return oldPassword; }

    public void setOldPassword(String oldPassword) { this.oldPassword = oldPassword; }

    public String getNewPassword() { return newPassword; }

    public void setNewPassword(String newPassword) { this.newPassword = newPassword; }

    public String getNewConfirmPassword() { return newConfirmPassword; }

    public void setNewConfirmPassword(String newConfirmPassword) { this.newConfirmPassword = newConfirmPassword; }

}
