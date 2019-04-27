package net.symbiosis.web_ui.session;

import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_wallet_group_voucher;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet_group;
import net.symbiosis.web_ui.common.JSFLoggable;
import net.symbiosis.web_ui.common.UpdateOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.symbiosis.core_lib.enumeration.DBConfigVars.CONFIG_DEFAULT_CURRENCY_SYMBOL;
import static net.symbiosis.core_lib.enumeration.SymEventType.VOUCHER_CREATE;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.EXISTING_DATA_FOUND;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.DaoManager.getSymConfigDao;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;
import static net.symbiosis.persistence.helper.SymEnumHelper.voucherTypeFromString;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class VoucherCreateBean implements JSFLoggable {

    private static final Logger logger = Logger.getLogger(VoucherCreateBean.class.getSimpleName());
    private sym_voucher newVoucher;
    private final UpdateOptions updateOptions;
    private SessionBean sessionBean;

    @Autowired
    public VoucherCreateBean(SessionBean sessionBean, UpdateOptions updateOptions) {
        this.sessionBean = sessionBean;
        this.updateOptions = updateOptions;
    }

    @PostConstruct
    public void init() {
        newVoucher = initNewVoucher();
    }

    private sym_voucher initNewVoucher() {
        return new sym_voucher(
            new BigDecimal(0), false,null, voucherTypeFromString("AIRTIME"),
            0.0, getSymConfigDao().getConfig(CONFIG_DEFAULT_CURRENCY_SYMBOL),true, true
        );
    }

    public sym_voucher getNewVoucher() { return newVoucher; }

    public void setNewVoucher(sym_voucher newVoucher) { this.newVoucher = newVoucher; }

    public void createNewVoucher() {
        Date requestTime = new Date();
        String voucherDesc = format("%s %s%s %s %s",
            newVoucher.getVoucher_provider().getName(), newVoucher.getUnits(), newVoucher.getVoucher_value(),
            newVoucher.getService_provider().getName(), newVoucher.getVoucher_type());

        logger.info("Creating new voucher " + voucherDesc);

        List<sym_voucher> existingVoucher = getEntityManagerRepo().findWhere(sym_voucher.class,
                new ArrayList<>(asList(
                    new Pair<>("voucher_provider", newVoucher.getVoucher_provider().getId()),
                    new Pair<>("service_provider", newVoucher.getService_provider().getId()),
                    new Pair<>("voucher_type", newVoucher.getVoucher_type().getId()),
                    new Pair<>("voucher_value", newVoucher.getVoucher_value())
                )
            ),false, false, false, false
        );

        if (existingVoucher != null && existingVoucher.size() > 0) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                "Failed to create voucher",
                "Voucher + '" + voucherDesc + "' already exists"));
            log(fromEnum(VOUCHER_CREATE), sessionBean.getSymbiosisAuthUser(),
                    fromEnum(EXISTING_DATA_FOUND),
                    requestTime, new Date(), "CREATE VOUCHER " + voucherDesc,
                    "Voucher '" + voucherDesc + "' already exists");
            return;
        }

        updateOptions.getVouchers().add(newVoucher.save());
        getEntityManagerRepo().refresh(newVoucher.getService_provider());
        getEntityManagerRepo().refresh(newVoucher.getVoucher_provider());

        //create wallet group voucher for new voucher
        for (sym_wallet_group walletGroup : updateOptions.getWalletGroups()) {
            updateOptions.getWalletGroupVouchers().add(
                new sym_wallet_group_voucher(walletGroup, newVoucher, walletGroup.getDefault_discount()).save()
            );
        }
        getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO,
                "Successfully created new voucher", "Created new voucher " + voucherDesc));
        log(fromEnum(VOUCHER_CREATE), sessionBean.getSymbiosisAuthUser(),
                fromEnum(SUCCESS),
                requestTime, new Date(), "CREATE VOUCHER " + voucherDesc,
                "Created new voucher " + voucherDesc);
        newVoucher = initNewVoucher();
    }
}

