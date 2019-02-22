package net.symbiosis.web_ui.session;

import net.symbiosis.authentication.authentication.PosAuthenticationProvider;
import net.symbiosis.common.structure.Pair;
import net.symbiosis.core.service.WalletManager;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.log.sym_incoming_payment;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.entity.enumeration.sym_auth_group;
import net.symbiosis.persistence.entity.enumeration.sym_deposit_type;
import net.symbiosis.web_ui.common.JSFUpdatable;
import net.symbiosis.web_ui.common.UpdateOptions;
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

import static java.lang.String.format;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.symbiosis.common.configuration.Configuration.getProperty;
import static net.symbiosis.common.utilities.ReferenceGenerator.GenPin;
import static net.symbiosis.core_lib.enumeration.SymChannel.POS_MACHINE;
import static net.symbiosis.core_lib.enumeration.SymEventType.USER_REGISTRATION;
import static net.symbiosis.core_lib.enumeration.SymEventType.WALLET_LOAD;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.GENERAL_ERROR;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;
import static net.symbiosis.web_ui.common.DataTableHeaders.*;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class MerchantBean extends JSFUpdatable {

    private static final Logger logger = Logger.getLogger(MerchantBean.class.getSimpleName());
    private static final String TABLE_NAME = "Merchants";
    private final UpdateOptions updateOptions;
    private final WalletManager walletManager;
    private List<sym_wallet> wallets;
    private sym_wallet selectedWallet;
    private sym_wallet selectedUpdateWallet;
    private sym_incoming_payment newIncomingPayment = new sym_incoming_payment();
    private sym_deposit_type newDepositType;

    @Autowired
    public MerchantBean(SessionBean sessionBean, UpdateOptions updateOptions, WalletManager walletManager) {
        super(sessionBean);
        this.updateOptions = updateOptions;
        this.walletManager = walletManager;
    }

    @PostConstruct
    public void init() {
        notUpdatableColumns.add(HEADER_TEXT_ID);
        updatableColumns.add(HEADER_TEXT_CNAME);
        notUpdatableColumns.add(HEADER_TEXT_ADMIN_NAME);
        notUpdatableColumns.add(HEADER_TEXT_BALANCE);
        updatableColumns.add(HEADER_TEXT_W_GROUP);
        updatableColumns.add(HEADER_TEXT_ADDRESS1);
        updatableColumns.add(HEADER_TEXT_ADDRESS2);
        updatableColumns.add(HEADER_TEXT_CITY);
        updatableColumns.add(HEADER_TEXT_COMPANY_PHONE);
        updatableColumns.add(HEADER_TEXT_COMPANY_PHONE2);
        updatableColumns.add(HEADER_TEXT_COMPANY_VAT_NUM);
        updatableColumns.add(HEADER_TEXT_COMPANY_REG_NUM);
        notUpdatableColumns.add(HEADER_TEXT_LINKED_USERS);
        initializeWallets();
    }

    public void initializeWallets() {
        wallets = getEntityManagerRepo().findAll(sym_wallet.class, true);
        if (wallets != null && wallets.size() > 0) { selectedWallet = wallets.get(0); }
    }

    public List<sym_wallet> getWallets() { return wallets; }

    public void setWallets(List<sym_wallet> wallets) { this.wallets = wallets; }

    public sym_wallet getSelectedWallet() {
        if (selectedWallet == null) {
            selectedWallet = wallets.get(0);
        }
        return selectedWallet;
    }

    public void setSelectedWallet(sym_wallet selectedWallet) { this.selectedWallet = selectedWallet; }

    public sym_wallet getSelectedUpdateWallet() {
        return selectedUpdateWallet;
    }

    public void setSelectedUpdateWallet(sym_wallet selectedUpdateWallet) {
        this.selectedUpdateWallet = selectedUpdateWallet;
    }

    public void changeWallet(AjaxBehaviorEvent event) {
        selectedWallet = (sym_wallet) ((UIInput) event.getSource()).getValue();
    }

    public void loadWallet() {
        Date requestTime = new Date();
        getNewIncomingPayment().setDeposit_type(getNewDepositType());
        logger.info("Loading wallet " + getSelectedWallet().getId() +
            " with amount " + getNewIncomingPayment().getPayment_amount() +
            " deposited from " + getNewIncomingPayment().getDeposit_type().getName());
        try {
            SymResponseObject<sym_wallet> loadResult = walletManager.updateWalletBalance(selectedWallet, newIncomingPayment.getPayment_amount());
            if (loadResult.getResponseCode().equals(SUCCESS)) {
                newIncomingPayment.setTime_loaded(new Date()).setWallet(selectedWallet).save();
                getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO,
                    "Funds Loaded Successfully",
                    format("Loaded wallet %s with amount %s", selectedWallet.getCompany().getCompany_name(),
                        getNewIncomingPayment().getPayment_amount())));

                log(fromEnum(WALLET_LOAD), sessionBean.getSymbiosisAuthUser(), fromEnum(loadResult.getResponseCode()),
                    requestTime, new Date(), "LOAD WALLET " + getSelectedWallet().getId() +
                        " WITH " + getNewIncomingPayment().getPayment_amount() +
                        " FROM " + getNewIncomingPayment().getDeposit_type().getName(),
                    format("Loaded wallet %s with amount %s", selectedWallet.getCompany().getCompany_name(),
                        getNewIncomingPayment().getPayment_amount()));
                newIncomingPayment = new sym_incoming_payment().setDeposit_type(getNewDepositType());
            } else {
                getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                        "Loading wallet failed!", loadResult.getMessage()));

                log(fromEnum(WALLET_LOAD), sessionBean.getSymbiosisAuthUser(), fromEnum(loadResult.getResponseCode()),
                    requestTime, new Date(), "LOAD WALLET " + getSelectedWallet().getId() +
                        " WITH " + getNewIncomingPayment().getPayment_amount() +
                        " FROM " + getNewIncomingPayment().getDeposit_type().getName(),
                    loadResult.getMessage());

            }
        } catch (Exception ex) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                "Loading wallet  failed!", "Loading failed with error: " + ex.getMessage()));
            log(fromEnum(WALLET_LOAD), sessionBean.getSymbiosisAuthUser(), fromEnum(GENERAL_ERROR),
                requestTime, new Date(), "LOAD WALLET " + getSelectedWallet().getId() +
                    " WITH " + getNewIncomingPayment().getPayment_amount() +
                    " FROM " + getNewIncomingPayment().getDeposit_type().getName(),
                "Loading failed with error: " + ex.getMessage());
        }
    }

    public sym_incoming_payment getNewIncomingPayment() {
        if (newIncomingPayment == null) {
            newIncomingPayment = new sym_incoming_payment().setDeposit_type(getNewDepositType());
        }
        if (newIncomingPayment.getDeposit_type() == null) {
            newIncomingPayment.setDeposit_type(getNewDepositType());
        }
        return newIncomingPayment;
    }

    public void setNewIncomingPayment(sym_incoming_payment newIncomingPayment) {
        this.newIncomingPayment = newIncomingPayment;
    }

    public sym_deposit_type getNewDepositType() {
        if (newDepositType == null) {
            newDepositType = updateOptions.getDepositTypes().get(0);
        }
        return newDepositType;
    }

    public void setNewDepositType(sym_deposit_type newDepositType) {
        this.newDepositType = newDepositType;
    }

    public List<sym_user> getMerchantUsers() {
        if (selectedUpdateWallet == null) { return null; }
        return getEntityManagerRepo().findWhere(sym_user.class,
            new Pair<>("wallet", selectedUpdateWallet.getId())
        );
    }

    public void registerPOSUser(sym_user updateUser) {
        logger.info("Registering POS user " + updateUser.getUsername());

        sym_request_response_log registrationLog = new sym_request_response_log(
            fromEnum(POS_MACHINE), fromEnum(USER_REGISTRATION), format("Registering %s to channel POS_MACHINE", updateUser.getUsername())
        );

        PosAuthenticationProvider authenticationProvider = new PosAuthenticationProvider(
            registrationLog, null, updateUser.getUsername(), GenPin()
        );

        SymResponseObject<sym_auth_user> assignResponse = authenticationProvider.assignPOSChannel(
            updateUser, findByName(sym_auth_group.class, getProperty("DefaultPOSGroup"))
        );

        registrationLog.setSystem_user(updateUser);
        registrationLog.setAuth_user(assignResponse.getResponseObject());
        registrationLog.setOutgoing_response_time(new Date());
        registrationLog.setResponse_code(fromEnum(assignResponse.getResponseCode()));

        String responseString;

        if (assignResponse.getResponseCode().equals(SUCCESS)) {
            responseString = format("Successfully registered %s for ETTL Falcon POS Software", updateUser.getUsername());
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO,
                "Registration successful", responseString)
            );
        } else {
            responseString = format("Registration for ETTL Falcon POS Software failed for user %s: %s" ,
                updateUser.getUsername(), assignResponse.getMessage());
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                    "Registration failed", responseString)
            );
        }

        registrationLog.setOutgoing_response(responseString);
        registrationLog.save();
    }

    @Override
    public String getTableDescription() { return TABLE_NAME; }
}

