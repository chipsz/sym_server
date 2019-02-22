package net.symbiosis.web_ui.session;

import net.symbiosis.common.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_service_provider;
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
import static net.symbiosis.core_lib.enumeration.SymEventType.SERVICE_PROVIDER_CREATE;
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
public class ServiceProviderBean extends JSFUpdatable {

    private static final String TABLE_NAME = "Service Providers";
    private final UpdateOptions updateOptions;
    private sym_service_provider newServiceProvider = new sym_service_provider();

    @Autowired
    public ServiceProviderBean(SessionBean sessionBean, UpdateOptions updateOptions) {
        super(sessionBean);
        this.updateOptions = updateOptions;
    }

    @PostConstruct
    public void init() {
        notUpdatableColumns.add(HEADER_TEXT_ID);
        updatableColumns.add(HEADER_TEXT_NAME);
        updatableColumns.add(HEADER_TEXT_ENABLED);
    }

    public sym_service_provider getNewServiceProvider() { return newServiceProvider; }

    public void setNewServiceProvider(sym_service_provider newServiceProvider) {
        this.newServiceProvider = newServiceProvider;
    }

    public void createServiceProvider() {
        Date requestTime = new Date();
        List<sym_service_provider> existingSP = getEntityManagerRepo().findWhere(
            sym_service_provider.class, new Pair<>("name", newServiceProvider.getName()),
            false, false, false, false
        );

        if (existingSP != null && existingSP.size() > 0) {
            getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_ERROR,
                "Failed to create service provider",
                "Service Provider with name " + newServiceProvider.getName() + " already exists"));
            log(fromEnum(SERVICE_PROVIDER_CREATE), sessionBean.getSymbiosisAuthUser(), fromEnum(EXISTING_DATA_FOUND), requestTime, new Date(),
                "CREATE SERVICE PROVIDER " + newServiceProvider.getName() + " | ENABLED = " + newServiceProvider.getIs_enabled(),
                "Service Provider with name " + newServiceProvider.getName() + " already exists");
            return;
        }

        updateOptions.getServiceProviders().add(newServiceProvider.save());
        getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO,
                "Successfully Created Service Provider",
                "Created new service provider " + newServiceProvider.getName()));
        log(fromEnum(SERVICE_PROVIDER_CREATE), sessionBean.getSymbiosisAuthUser(), fromEnum(SUCCESS), requestTime, new Date(),
            "CREATE SERVICE PROVIDER TYPE " + newServiceProvider.getName() + " | ENABLED = " + newServiceProvider.getIs_enabled(),
            "Created new service provider " + newServiceProvider.getName());
        newServiceProvider = new sym_service_provider();

    }

    @Override
    public String getTableDescription() { return TABLE_NAME; }
}

