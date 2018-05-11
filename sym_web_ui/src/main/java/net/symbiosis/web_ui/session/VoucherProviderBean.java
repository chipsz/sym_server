package net.symbiosis.web_ui.session;

import net.symbiosis.common.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher_provider;
import net.symbiosis.persistence.entity.enumeration.sym_response_code;
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
import static net.symbiosis.core_lib.enumeration.SymEventType.CREATE_VOUCHER_PROVIDER;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.EXISTING_DATA_FOUND;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findByName;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;
import static net.symbiosis.web_ui.common.DataTableHeaders.*;

/***************************************************************************
 *                                                                         *
 * Created:     09 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class VoucherProviderBean extends JSFUpdatable {

    private static final String TABLE_NAME = "Voucher Providers";
    private final UpdateOptions updateOptions;
    private sym_voucher_provider newVoucherProvider = new sym_voucher_provider();

    @Autowired
    public VoucherProviderBean(SessionBean sessionBean, UpdateOptions updateOptions) {
        super(sessionBean);
        this.updateOptions = updateOptions;
    }

    @PostConstruct
    public void init() {
        notUpdatableColumns.add(HEADER_TEXT_ID);
        updatableColumns.add(HEADER_TEXT_NAME);
        updatableColumns.add(HEADER_TEXT_ENABLED);
        notUpdatableColumns.add(HEADER_TEXT_BALANCE);
    }

    public sym_voucher_provider getNewVoucherProvider() { return newVoucherProvider; }

    public void setNewVoucherProvider(sym_voucher_provider newVoucherProvider) {
        this.newVoucherProvider = newVoucherProvider;
    }

    public void createVoucherProvider() throws IOException {
        Date requestTime = new Date();
        List<sym_voucher_provider> existingVP = getEntityManagerRepo().findWhere(
            sym_voucher_provider.class, new Pair<>("name", newVoucherProvider.getName()),
            false, false, false, false
        );

        if (existingVP != null && existingVP.size() > 0) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                "Failed to create voucher provider",
                "Voucher Provider with name " + newVoucherProvider.getName() + " already exists"));
            log(fromEnum(CREATE_VOUCHER_PROVIDER), sessionBean.getSymbiosisAuthUser(),
                findByName(sym_response_code.class, EXISTING_DATA_FOUND.name()), requestTime, new Date(),
                "CREATE VOUCHER PROVIDER " + newVoucherProvider.getName() + " | ENABLED = " + newVoucherProvider.getIs_enabled(),
                "Voucher provider with name " + newVoucherProvider.getName() + " already exists");
            return;
        }
        updateOptions.getVoucherProviders().add(newVoucherProvider.save());
        getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO,
                "Successfully Created Voucher Provider",
                "Created new voucher provider " + newVoucherProvider.getName()));
        log(fromEnum(CREATE_VOUCHER_PROVIDER), sessionBean.getSymbiosisAuthUser(),
            findByName(sym_response_code.class, SUCCESS.name()), requestTime, new Date(),
            "CREATE VOUCHER PROVIDER " + newVoucherProvider.getName() + " | ENABLED = " + newVoucherProvider.getIs_enabled(),
            "Created new voucher provider " + newVoucherProvider.getName());
        newVoucherProvider = new sym_voucher_provider();

    }

    @Override
    public String getTableDescription() { return TABLE_NAME; }
}
