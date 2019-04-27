package net.symbiosis.web_ui.session;

import net.symbiosis.authentication.authentication.WebAuthenticationProvider;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.entity.enumeration.*;
import net.symbiosis.web_ui.common.JSFLoggable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIInput;
import javax.faces.event.AjaxBehaviorEvent;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.symbiosis.core_lib.enumeration.DBConfigVars.*;
import static net.symbiosis.core_lib.enumeration.SymChannel.WEB;
import static net.symbiosis.core_lib.enumeration.SymEventType.USER_CREATE;
import static net.symbiosis.core_lib.enumeration.SymEventType.USER_REGISTRATION;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.AUTH_AUTHENTICATION_FAILED;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.DaoManager.getSymConfigDao;
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
public class MerchantCreateBean implements JSFLoggable {

    private static final Logger logger = Logger.getLogger(MerchantCreateBean.class.getSimpleName());
    private sym_user newUser;
    private List<sym_wallet> wallets;
    private sym_wallet selectedWallet;
    private String confirmPassword;
    private SessionBean sessionBean;

    @Autowired
    public MerchantCreateBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
        initializeWallets();
    }

    @PostConstruct
    public void init() { initDefaultValues(); }

    private void initDefaultValues() {
        newUser = new sym_user();
        newUser.setCountry(findByName(sym_country.class, getSymConfigDao().getConfig(CONFIG_DEFAULT_COUNTRY)));
        newUser.setLanguage(findByName(sym_language.class, getSymConfigDao().getConfig(CONFIG_DEFAULT_LANGUAGE)));
        newUser.setPassword_tries(0);
        newUser.setPin_tries(0);
    }

    public sym_user getNewUser() { return newUser; }

    public void setNewUser(sym_user newUser) { this.newUser = newUser; }

    public String getConfirmPassword() { return confirmPassword; }

    public void setConfirmPassword(String confirmPassword) { this.confirmPassword = confirmPassword; }

    private void initializeWallets() {
        wallets = getEntityManagerRepo().findAll(sym_wallet.class, true);
        if (wallets != null && wallets.size() > 0) { selectedWallet = wallets.get(0); }
    }

    public List<sym_wallet> getWallets() { initializeWallets(); return wallets; }

    public void setWallets(List<sym_wallet> wallets) { this.wallets = wallets; }

    public sym_wallet getSelectedWallet() {
        if (selectedWallet == null) {
            selectedWallet = wallets.get(0);
        }
        return selectedWallet; }

    public void setSelectedWallet(sym_wallet selectedWallet) { this.selectedWallet = selectedWallet; }

    public void changeWallet(AjaxBehaviorEvent event) {
        selectedWallet = (sym_wallet) ((UIInput) event.getSource()).getValue();
    }

    public void createNewUser() {
        Date requestTime = new Date();

        String userDesc = String.format("[%s] %s %s (%s, %s, %s)",
                selectedWallet.getCompany().getCompany_name(),
                newUser.getFirst_name(),
                newUser.getLast_name(),
                newUser.getUsername(),
                newUser.getEmail(),
                newUser.getMsisdn()
        );

        logger.info("Creating user from existing merchant: " + userDesc);

        if (!confirmPassword.equals(newUser.getPassword())) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                "Registration Failed", "Password and password confirmation must match"));
            logger.warning("Registration failed: Password and password confirmation must match");
            log(fromEnum(USER_CREATE), sessionBean.getSymbiosisAuthUser(), fromEnum(AUTH_AUTHENTICATION_FAILED),
                requestTime, new Date(), "CREATE USER " + userDesc,
                "Registration failed: Password and password confirmation must match");
            return;
        }

        sym_request_response_log requestResponseLog = new sym_request_response_log(
                findByName(sym_channel.class, WEB.name()),
                findByName(sym_event_type.class, USER_REGISTRATION.name()),
                newUser.toPrintableString()).save();

        WebAuthenticationProvider webAuthenticationProvider = new WebAuthenticationProvider(
                requestResponseLog, newUser.getUsername(), newUser.getPassword(), null);

        SymResponseObject<sym_auth_user> registrationResponse =
            webAuthenticationProvider.registerWebUser(newUser, selectedWallet.getCompany(),
                findByName(sym_auth_group.class, getSymConfigDao().getConfig(CONFIG_DEFAULT_WEB_AUTH_GROUP)), selectedWallet);

        if (registrationResponse.getResponseCode().equals(SUCCESS)) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO, "Registration Successful",
                    "Registration Successful. Confirmation email sent to " + newUser.getEmail()));

            log(findByName(sym_event_type.class, USER_CREATE.name()), sessionBean.getSymbiosisAuthUser(),
                findByName(sym_response_code.class, SUCCESS.name()),
                requestTime, new Date(), "CREATE USER " + userDesc,
                "Registration Successful. Confirmation email sent to " + newUser.getEmail());

            initDefaultValues();
        } else {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR, "Registration Failed",
                    "Registration Failed. " + registrationResponse.getMessage()));

            log(findByName(sym_event_type.class, USER_CREATE.name()), sessionBean.getSymbiosisAuthUser(),
                    findByName(sym_response_code.class, registrationResponse.getResponseCode().name()),
                    requestTime, new Date(), "CREATE USER " + userDesc,
                    "Registration Failed. " + registrationResponse.getMessage());
        }
    }
}

