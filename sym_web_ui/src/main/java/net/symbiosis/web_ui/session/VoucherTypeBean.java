package net.symbiosis.web_ui.session;

import net.symbiosis.common.structure.Pair;
import net.symbiosis.persistence.entity.enumeration.sym_voucher_type;
import net.symbiosis.web_ui.common.JSFUpdatable;
import net.symbiosis.web_ui.common.UpdateOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import java.util.Date;
import java.util.List;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.symbiosis.core_lib.enumeration.SymEventType.CREATE_VOUCHER_TYPE;
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
public class VoucherTypeBean extends JSFUpdatable {

    private sym_voucher_type newVoucherType = new sym_voucher_type();
    private final UpdateOptions updateOptions;

    @Autowired
    public VoucherTypeBean(SessionBean sessionBean, UpdateOptions updateOptions) {
        super(sessionBean);
        this.updateOptions = updateOptions;
    }

    @PostConstruct
    public void init() {
        notUpdatableColumns.add(HEADER_TEXT_ID);
        updatableColumns.add(HEADER_TEXT_NAME);
        updatableColumns.add(HEADER_TEXT_ENABLED);
    }

    public sym_voucher_type getNewVoucherType() { return newVoucherType; }

    public void setNewVoucherType(sym_voucher_type newVoucherType) { this.newVoucherType = newVoucherType; }

    public void createNewVoucherType() {
        Date requestTime = new Date();
        List<sym_voucher_type> existingVT = getEntityManagerRepo().findWhere(
            sym_voucher_type.class, new Pair<>("name", newVoucherType.getName()),
            false, false, false, false
        );

        if (existingVT != null && existingVT.size() > 0) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                "Failed to create voucher type",
                "Voucher type with name " + newVoucherType.getName() + " already exists"));
            log(fromEnum(CREATE_VOUCHER_TYPE), sessionBean.getSymbiosisAuthUser(),
                fromEnum(EXISTING_DATA_FOUND), requestTime, new Date(),
                "CREATE VOUCHER TYPE " + newVoucherType.getName() + " | ENABLED = " + newVoucherType.getIs_enabled(),
                "Voucher type with name " + newVoucherType.getName() + " already exists");
            return;
        }
        updateOptions.getVoucherTypes().add(newVoucherType.save());
        getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO,
            "Successfully created new voucher type " + newVoucherType.getName(),
            "Created voucher type " + newVoucherType.getName()));
        log(fromEnum(CREATE_VOUCHER_TYPE), sessionBean.getSymbiosisAuthUser(),
            fromEnum(SUCCESS), requestTime, new Date(),
            "CREATE VOUCHER TYPE " + newVoucherType.getName() + " | ENABLED = " + newVoucherType.getIs_enabled(),
            "Created voucher type " + newVoucherType.getName());
        newVoucherType = new sym_voucher_type();
    }

    @Override
    public String getTableDescription() {
        return null;
    }
}

