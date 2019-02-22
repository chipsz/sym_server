package net.symbiosis.web_ui.session;

import net.symbiosis.authentication.authentication.WebAuthenticationProvider;
import net.symbiosis.common.structure.Pair;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_company;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.enumeration.sym_auth_group;
import net.symbiosis.persistence.entity.enumeration.sym_country;
import net.symbiosis.web_ui.request.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.symbiosis.common.configuration.Configuration.getCountryCodePrefix;
import static net.symbiosis.common.configuration.Configuration.getProperty;
import static net.symbiosis.core_lib.enumeration.SymChannel.WEB;
import static net.symbiosis.core_lib.enumeration.SymEventType.USER_REGISTRATION;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.*;
import static net.symbiosis.core_lib.utilities.CommonUtilities.formatFullMsisdn;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.SymEnumHelper.*;
import static net.symbiosis.web_ui.common.SystemPages.PAGE_LOGIN;
import static net.symbiosis.web_ui.common.SystemPages.PAGE_REGISTRATION;

/***************************************************************************
 *                                                                         *
 * Created:     09 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/
@Component
@Scope("session")
public class RegistrationBean implements Serializable {

    private final Registration registrationData;
    private final SessionBean sessionBean;

    private static final Logger logger = Logger.getLogger(RegistrationBean.class.getSimpleName());

    @Autowired
    public RegistrationBean(Registration registrationData, SessionBean sessionBean) {
        this.registrationData = registrationData;
        this.sessionBean = sessionBean;
    }

    public String goToLogin() {
        logger.info("Going to login page");
        return sessionBean.setCurrentPage(PAGE_LOGIN).getBaseXHTML();
    }

    public String register() {
        logger.info(format("Performing registration for %s %s", registrationData.getFirstName(), registrationData.getLastName()));

        sym_request_response_log requestResponseLog = new sym_request_response_log(
                fromEnum(WEB), fromEnum(USER_REGISTRATION), registrationData.toPrintableString()).save();

        if (!registrationData.getPassword().equals(registrationData.getConfirmPassword())) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                    "Registration Failed: Password and password confirmation must match",
                    "Registration Failed: Password and password confirmation must match"));
            logger.warning("Registration failed: Password and password confirmation must match");
            requestResponseLog.setOutgoing_response("Registration Failed: Password and password confirmation must match");
            requestResponseLog.setOutgoing_response_time(new Date());
            requestResponseLog.setResponse_code(fromEnum(INPUT_INVALID_PASSWORD));
            requestResponseLog.save();
            return PAGE_REGISTRATION.getBaseXHTML();
        }

        List<sym_user> existingUser = getEntityManagerRepo().findWhere(sym_user.class,
                new Pair<>("username", registrationData.getUsername())
        );
        if (existingUser != null && existingUser.size() > 0) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                    "Registration Failed: Username " + registrationData.getUsername() + " is already registered",
                    "Registration Failed: Username " + registrationData.getUsername() + " is already registered"));
            logger.warning("Registration failed: Username " + registrationData.getUsername() + " is already registered");
            requestResponseLog.setOutgoing_response("Registration Failed: Username " + registrationData.getUsername() + " is already registered");
            requestResponseLog.setOutgoing_response_time(new Date());
            requestResponseLog.setResponse_code(fromEnum(PREVIOUS_USERNAME_FOUND));
            requestResponseLog.save();
            return PAGE_REGISTRATION.getBaseXHTML();
        }

        existingUser = getEntityManagerRepo().findWhere(sym_user.class, new Pair<>("email", registrationData.getEmail()));
        if (existingUser != null && existingUser.size() > 0) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                    "Registration Failed: Email address " + registrationData.getEmail() + " is already registered",
                    "Registration Failed: Email address " + registrationData.getEmail() + " is already registered"));
            logger.warning("Registration failed: Email address " + registrationData.getEmail() + " is already registered");
            requestResponseLog.setOutgoing_response("Registration Failed: Email address " + registrationData.getEmail() + " is already registered");
            requestResponseLog.setOutgoing_response_time(new Date());
            requestResponseLog.setResponse_code(fromEnum(PREVIOUS_EMAIL_FOUND));
            requestResponseLog.save();
            return PAGE_REGISTRATION.getBaseXHTML();
        }

        registrationData.setMsisdn(formatFullMsisdn(registrationData.getMsisdn(), getCountryCodePrefix()));
        registrationData.setMsisdn2(formatFullMsisdn(registrationData.getMsisdn2(), getCountryCodePrefix()));

        existingUser = getEntityManagerRepo().findWhere(sym_user.class, new Pair<>("msisdn", registrationData.getMsisdn()));
        if (existingUser != null && existingUser.size() > 0) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                    "Registration Failed: Phone number " + registrationData.getMsisdn() + " is already registered",
                    "Registration Failed: Phone number " + registrationData.getMsisdn() + " is already registered"));
            logger.warning("Registration failed: Phone number " + registrationData.getMsisdn() + " is already registered");
            requestResponseLog.setOutgoing_response("Registration Failed: Phone number " + registrationData.getMsisdn() + " is already registered");
            requestResponseLog.setOutgoing_response_time(new Date());
            requestResponseLog.setResponse_code(fromEnum(PREVIOUS_MSISDN_FOUND));
            requestResponseLog.save();
            return PAGE_REGISTRATION.getBaseXHTML();
        }

        sym_user newUser = new sym_user(registrationData.getFirstName(), registrationData.getLastName(),
                null, registrationData.getUsername(), registrationData.getPassword(),
                null, 0, 0, null, registrationData.getEmail(), registrationData.getMsisdn(),
                registrationData.getMsisdn2(), fromEnum(ACC_INACTIVE), countryFromString(getProperty("DefaultCountry")),
                languageFromString(getProperty("DefaultLanguage")));

        sym_company newCompany = new sym_company(registrationData.getCompanyName(),
                registrationData.getAddressLine1(), registrationData.getAddressLine2(), registrationData.getAddressCity(),
                findByName(sym_country.class, getProperty("DefaultCountry")), registrationData.getPhone1(),
                registrationData.getPhone2(), registrationData.getVatNumber(), registrationData.getRegistrationNumber());

        WebAuthenticationProvider webAuthenticationProvider = new WebAuthenticationProvider(
                requestResponseLog, newUser.getUsername(), newUser.getPassword(), null);

        SymResponseObject<sym_auth_user> registrationResponse =
                webAuthenticationProvider.registerWebUser(newUser, newCompany,
                        findByName(sym_auth_group.class, getProperty("DefaultWebGroup")), null);

        if (registrationResponse.getResponseCode().equals(SUCCESS)) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO,
                    "Registration Successful. Confirmation email sent to " + newUser.getEmail(), "Registration Successful"));
            requestResponseLog.setOutgoing_response("Registration Successful. Confirmation email sent to " + newUser.getEmail());
            requestResponseLog.setOutgoing_response_time(new Date());
            requestResponseLog.setResponse_code(fromEnum(SUCCESS));
            requestResponseLog.save();
            return PAGE_LOGIN.getBaseXHTML();
        } else {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                    "Registration Failed: " + registrationResponse.getMessage(),
                    "Registration Failed: " + registrationResponse.getMessage()));
            requestResponseLog.setOutgoing_response("Registration Failed: " + registrationResponse.getMessage());
            requestResponseLog.setOutgoing_response_time(new Date());
            requestResponseLog.setResponse_code(fromEnum(registrationResponse.getResponseCode()));
            requestResponseLog.save();
            return PAGE_REGISTRATION.getBaseXHTML();
        }
    }
}
