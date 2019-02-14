package net.symbiosis.web_ui.session;

import net.symbiosis.authentication.authentication.WebAuthenticationProvider;
import net.symbiosis.common.structure.Pair;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.enumeration.sym_channel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.symbiosis.common.configuration.Configuration.getCountryCodePrefix;
import static net.symbiosis.common.utilities.ReferenceGenerator.Gen;
import static net.symbiosis.core_lib.enumeration.SymChannel.WEB;
import static net.symbiosis.core_lib.enumeration.SymEventType.RESET_PASSWORD;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static net.symbiosis.core_lib.utilities.CommonUtilities.formatFullMsisdn;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;
import static net.symbiosis.web_ui.common.SystemPages.PAGE_LOGIN;
import static net.symbiosis.web_ui.common.SystemPages.PAGE_RESET_PASSWORD;

/***************************************************************************
 *                                                                         *
 * Created:     09 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/
@Component
@Scope("session")
public class ResetPasswordBean implements Serializable {

    private static final Logger logger = Logger.getLogger(ResetPasswordBean.class.getSimpleName());
    private final SessionBean sessionBean;
    private String msisdn;
    private String email;
    private String username;

    @Autowired
    public ResetPasswordBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public String getMsisdn() {
        return msisdn;
    }

    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String resetPassword() {
        logger.info(format("Resetting password for %s %s %s", username, email, msisdn));

        List<sym_auth_user> results = getEntityManagerRepo().findWhere(sym_auth_user.class, new ArrayList<>(asList(
                new Pair<>("channel", findByName(sym_channel.class, WEB.name()).getId()),
                new Pair<>("user.username", username),
                new Pair<>("user.email", email)
        )));

        logger.info(format("Found %s users", results == null ? 0 : results.size()));

        if (results == null || results.size() != 1) {
            logger.info(format("No user found for username %s and email %s", username, email));
            getCurrentInstance().addMessage(null,
                    new FacesMessage(SEVERITY_ERROR, "Password reset failed",
                            "No user found matching the credentials provided"));
            return PAGE_RESET_PASSWORD.getBaseXHTML();
        }

        sym_auth_user resetUser = results.get(0);
        logger.info(format("Validating MSISDN %s", formatFullMsisdn(msisdn, getCountryCodePrefix())));

        if (!resetUser.getUser().getMsisdn().equals(formatFullMsisdn(msisdn, getCountryCodePrefix())) &&
                !resetUser.getUser().getMsisdn2().equals(formatFullMsisdn(msisdn, getCountryCodePrefix()))) {
            logger.info(format("No user found for username %s and email %s", username, email));
            getCurrentInstance().addMessage(null,
                    new FacesMessage(SEVERITY_ERROR, "Password reset failed",
                            "No user found matching the credentials provided"));
            return PAGE_RESET_PASSWORD.getBaseXHTML();
        }

        sym_request_response_log log = new sym_request_response_log(
                fromEnum(WEB), fromEnum(RESET_PASSWORD),
                format("Reset password for %s : %s : %s", username, email, msisdn)).save();

        WebAuthenticationProvider authProvider = new WebAuthenticationProvider(log, resetUser);

        SymResponseObject<sym_auth_user> result = authProvider.changePassword(resetUser, Gen(), false, null);

        log.setAuth_user(resetUser);
        log.setSystem_user(resetUser.getUser());
        log.setResponse_code(fromEnum(result.getResponseCode()));
        log.setOutgoing_response(result.getMessage());
        log.setOutgoing_response_time(new Date());
        log.save();

        if (!result.getResponseCode().equals(SUCCESS)) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Password reset failed",
                    "Password reset failed: " + result.getMessage()));
            return PAGE_RESET_PASSWORD.getBaseXHTML();
        }

        getCurrentInstance().addMessage(null,
                new FacesMessage(SEVERITY_INFO,
                        format("Successfully reset password for %s. Email with password sent to %s", username, email),
                        "Password reset successful"));
        return sessionBean.setCurrentPage(PAGE_LOGIN).getBaseXHTML();
    }
}
