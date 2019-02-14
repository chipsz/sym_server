package net.symbiosis.web_ui.session;

import net.symbiosis.authentication.authentication.WebAuthenticationProvider;
import net.symbiosis.common.structure.Pair;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_user_preference;
import net.symbiosis.persistence.entity.enumeration.sym_auth_group;
import net.symbiosis.persistence.entity.enumeration.sym_response_code;
import net.symbiosis.persistence.entity.enumeration.sym_role;
import net.symbiosis.web_ui.common.SystemPage;
import net.symbiosis.web_ui.request.Credentials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static javax.faces.application.FacesMessage.*;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.symbiosis.common.configuration.Configuration.getProperty;
import static net.symbiosis.core_lib.enumeration.SymAuthGroup.*;
import static net.symbiosis.core_lib.enumeration.SymChannel.WEB;
import static net.symbiosis.core_lib.enumeration.SymEventType.LOGIN;
import static net.symbiosis.core_lib.enumeration.SymPreference.PF_WEB_THEME;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;
import static net.symbiosis.web_ui.common.SystemPages.*;

/***************************************************************************
 *                                                                         *
 * Created:     09 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/
@Component
@Scope("session")
public class SessionBean implements Serializable {

    private final Credentials credentials;
    private static final Logger logger = Logger.getLogger(SessionBean.class.getSimpleName());
    private sym_auth_user symbiosisAuthUser;
    private SystemPage currentPage = PAGE_LOGIN;
    private static final HashMap<String, SystemPage> pageHandlers = new HashMap<>();

    private WebAuthenticationProvider authProvider;

    @Autowired
    public SessionBean(Credentials credentials) {
        this.credentials = credentials;
        registerPageHandler(PAGE_LOGIN);
        registerPageHandler(PAGE_REGISTRATION);
        registerPageHandler(PAGE_RESET_PASSWORD);
        registerPageHandler(PAGE_SUMMARY);
        registerPageHandler(PAGE_V_IMPORT);
        registerPageHandler(PAGE_WGV_UPDATE);
        registerPageHandler(PAGE_V_UPDATE);
        registerPageHandler(PAGE_V_CREATE);
        registerPageHandler(PAGE_M_CREATE);
        registerPageHandler(PAGE_WG_UPDATE);
        registerPageHandler(PAGE_VT_UPDATE);
        registerPageHandler(PAGE_SP_UPDATE);
        registerPageHandler(PAGE_VP_UPDATE);
        registerPageHandler(PAGE_M_UPDATE);
        registerPageHandler(PAGE_USER_UPDATE);
        registerPageHandler(PAGE_AU_UPDATE);
        registerPageHandler(PAGE_PM_UPDATE);
        registerPageHandler(PAGE_TRAN_REPORT);
        registerPageHandler(PAGE_PMNT_REPORT);
        registerPageHandler(PAGE_AUTH_REPORT);
        registerPageHandler(PAGE_SYS_REPORT);
        registerPageHandler(PAGE_S_TRAN_REPORT);
        registerPageHandler(PAGE_S_PMNT_REPORT);
        registerPageHandler(PAGE_S_AUTH_REPORT);
        registerPageHandler(PAGE_U_SETTINGS);
    }

    static void registerPageHandler(SystemPage systemPage) {
        logger.info("Registering new page " + systemPage.getIdString());
        pageHandlers.put(systemPage.getIdString(), systemPage);
    }

    SystemPage setCurrentPage(SystemPage systemPage) {
        currentPage = systemPage;
        return currentPage;
    }

    public SystemPage getCurrentPage() {
        return currentPage;
    }

    private SystemPage getDefaultStartPage(sym_auth_group group) {
        if (group.equals(fromEnum(SUPER_USER)) ||
            group.equals(fromEnum(WEB_ADMIN)) ||
            group.equals(fromEnum(WEB_CLERK))) {
            return PAGE_SUMMARY;
        } else {
            return PAGE_S_TRAN_REPORT;
        }
    }

    public String login() {

        logger.info(format("Authenticating user %s to channel WEB", credentials.getUsername()));
        sym_request_response_log authLog = new sym_request_response_log(
                fromEnum(WEB), fromEnum(LOGIN), format("Authenticating user %s to channel WEB",
                credentials.getUsername())).save();

        ExternalContext externalContext = getCurrentInstance().getExternalContext();
        String userAgent = externalContext.getRequestHeaderMap().get("User-Agent");


        authProvider = new WebAuthenticationProvider(
                authLog, credentials.getUsername(), credentials.getPassword(), userAgent
        );

        SymResponseObject<sym_auth_user> authResponse = authProvider.authenticateUser();

        authLog.setAuth_user(authResponse.getResponseObject());
        authLog.setSystem_user(authResponse.getResponseObject() == null ? null : authResponse.getResponseObject().getUser());
        authLog.setResponse_code(findByName(sym_response_code.class, authResponse.getResponseCode().name()));
        authLog.setOutgoing_response(authResponse.getMessage());
        authLog.setOutgoing_response_time(new Date());
        authLog.save();

        if (authResponse.getResponseCode().equals(SUCCESS)) {
            symbiosisAuthUser = authResponse.getResponseObject();
            getCurrentInstance().addMessage(null,
                    new FacesMessage(SEVERITY_INFO, "Authentication successful",
                            format("Successfully logged in %s %s", symbiosisAuthUser.getAuth_group().getName(), credentials.getUsername())));
            return setCurrentPage(getDefaultStartPage(symbiosisAuthUser.getAuth_group())).getBaseXHTML();
        } else {
            getCurrentInstance().addMessage(null,
                    new FacesMessage(SEVERITY_ERROR, format("Authentication failed: %s", authResponse.getMessage()), authResponse.getMessage()));
            return setCurrentPage(PAGE_LOGIN).getBaseXHTML();
        }
    }

    public String logout() {
        logger.info("Logout invoked by user " + (symbiosisAuthUser == null ? null : symbiosisAuthUser.getUser().getUsername()));
        if (symbiosisAuthUser != null && authProvider != null) {
            authProvider.endSession();
            getCurrentInstance().addMessage(null,
                    new FacesMessage(SEVERITY_INFO, format("Logged out %s", symbiosisAuthUser.getUser().getUsername()), ""));
            symbiosisAuthUser = null;
        }
        return setCurrentPage(PAGE_LOGIN).getBaseXHTML();
    }

    public String goToRegistration() {
        logger.info("Going to registration page");
        return setCurrentPage(PAGE_REGISTRATION).getBaseXHTML();
    }

    public String goToResetPassword() {
        logger.info("Going to reset password page");
        return setCurrentPage(PAGE_RESET_PASSWORD).getBaseXHTML();
    }

    private boolean isLoggedIn() {
        return symbiosisAuthUser != null;
    }

    public WebAuthenticationProvider getAuthProvider() {
        return authProvider;
    }

    public sym_auth_user getSymbiosisAuthUser() {
        return symbiosisAuthUser;
    }

    public String currentTheme() {

        if (symbiosisAuthUser == null) {
            return getProperty("DefaultTheme");
        }

        List<sym_user_preference> results = getEntityManagerRepo().findWhere(sym_user_preference.class, new ArrayList<>(
                asList(
                        new Pair<>("user", symbiosisAuthUser.getUser().getId()),
                        new Pair<>("preference", fromEnum(PF_WEB_THEME).getId())
                )
        ));

        if (results == null || results.size() < 1) {
            return getProperty("DefaultTheme");
        }

        return results.get(0).getPreference_value();
    }

    public void checkValidSession() throws IOException {
        if (!isLoggedIn()) {
            logger.warning("*** User login required to access the page. *** ");
            ExternalContext context = getCurrentInstance().getExternalContext();
            context.redirect("login.xhtml");
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_WARN,
                    "User login required to access admin page", ""));

        }
    }

    public void changeDisplay(String display) {
        if (pageHandlers.get(display) == null) {
            logger.severe("Display " + display + " is not mapped in pageHandlers");
            return;
        }
        setCurrentPage(pageHandlers.get(display));
        logger.info("Displayed page " + currentPage.getIdString());
    }

    public boolean isCurrentPage(String display) {
        return currentPage.getIdString().equals(display);
    }

    public boolean hasRole(String permission) {
        logger.info("Checking for permission: " + permission);
        return isLoggedIn() && authProvider.hasRole(findByName(sym_role.class, permission));
    }
}
