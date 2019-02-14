package net.symbiosis.authentication.session;

/**
 * Created with IntelliJ IDEA.
 * User: tkaviya
 * Date: 2013/07/08
 * Time: 11:32 PM
 */
public class UserSession {
    protected String username;
    protected String password;

    public UserSession(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
