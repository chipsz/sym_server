package net.symbiosis.web_ui.request;

import net.symbiosis.web_ui.annotations.PasswordConstraint;
import net.symbiosis.web_ui.annotations.UsernameConstraint;
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
public class Credentials implements Serializable {

    @UsernameConstraint
    private String username;
    @PasswordConstraint
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
