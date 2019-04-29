package net.symbiosis.web_ui.session;

import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet_group;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet_group_voucher_discount;
import net.symbiosis.web_ui.common.JSFUpdatable;
import net.symbiosis.web_ui.common.UpdateOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.symbiosis.core_lib.enumeration.SymEventType.WALLET_GROUP_CREATE;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.EXISTING_DATA_FOUND;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
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
public class WalletGroupBean extends JSFUpdatable {

    private static final String TABLE_NAME = "Wallet Groups";
    private final UpdateOptions updateOptions;
    private sym_wallet_group newWalletGroup = new sym_wallet_group();

    @Autowired
    public WalletGroupBean(SessionBean sessionBean, UpdateOptions updateOptions) {
        super(sessionBean);
        this.updateOptions = updateOptions;
    }

    @PostConstruct
    public void init() {
        notUpdatableColumns.add(HEADER_TEXT_ID);
        updatableColumns.add(HEADER_TEXT_NAME);
        updatableColumns.add(HEADER_TEXT_ENABLED);
        updatableColumns.add(HEADER_TEXT_DISCOUNT);
    }

    public sym_wallet_group getNewWalletGroup() { return newWalletGroup; }

    public void setNewWalletGroup(sym_wallet_group newWalletGroup) {
        this.newWalletGroup = newWalletGroup;
    }

    public void createWalletGroup() throws IOException {
        Date requestTime = new Date();
        List<sym_wallet_group> existingWG = getEntityManagerRepo().findWhere(
            sym_wallet_group.class, new Pair<>("name", newWalletGroup.getName()),
            false, false, false, false
        );

        if (existingWG != null && existingWG.size() > 0) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                "Failed to create wallet group",
                "Wallet Group with name " + newWalletGroup.getName() + " already exists"));

            log(fromEnum(WALLET_GROUP_CREATE), sessionBean.getSymbiosisAuthUser(),
                fromEnum(EXISTING_DATA_FOUND), requestTime, new Date(),
                "CREATE WALLET GROUP " + newWalletGroup.getName() + " | ENABLED = " + newWalletGroup.getIs_enabled(),
                "Wallet Group with name " + newWalletGroup.getName() + " already exists");
            return;
        }

        updateOptions.getWalletGroups().add(newWalletGroup.save());

        /* create a wallet group voucher for each voucher */
        for (sym_voucher voucher : updateOptions.getVouchers()) {
            updateOptions.getWalletGroupVouchers().add(
                new sym_wallet_group_voucher_discount(newWalletGroup, voucher, newWalletGroup.getDefault_discount()).save()
            );
        }

        getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO,
                "Successfully Created Wallet Group " + newWalletGroup.getName(),
                "Created new wallet group " + newWalletGroup.getName()));
        log(fromEnum(WALLET_GROUP_CREATE), sessionBean.getSymbiosisAuthUser(),
            fromEnum(SUCCESS), requestTime, new Date(),
            "CREATE WALLET GROUP " + newWalletGroup.getName() + " | ENABLED = " + newWalletGroup.getIs_enabled(),
            "Created new wallet group " + newWalletGroup.getName());
        newWalletGroup = new sym_wallet_group();
    }

    @Override
    public String getTableDescription() { return TABLE_NAME; }

}

