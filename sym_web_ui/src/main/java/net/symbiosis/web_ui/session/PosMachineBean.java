package net.symbiosis.web_ui.session;

import net.symbiosis.persistence.entity.complex_type.device.sym_device_pos_machine;
import net.symbiosis.web_ui.common.JSFUpdatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
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
public class PosMachineBean extends JSFUpdatable {

    private static final String TABLE_NAME = "Pos Machines";
    private List<sym_device_pos_machine> posMachines;

    @Autowired
    public PosMachineBean(SessionBean sessionBean) {
        super(sessionBean);
    }

    @PostConstruct
    public void init() {
        notUpdatableColumns.add(HEADER_TEXT_ID);
        notUpdatableColumns.add(HEADER_TEXT_CNAME);
        notUpdatableColumns.add(HEADER_TEXT_BNAME);
        notUpdatableColumns.add(HEADER_TEXT_MNAME);
        updatableColumns.add(HEADER_TEXT_MSISDN);
        updatableColumns.add(HEADER_TEXT_MSISDN2);
        notUpdatableColumns.add(HEADER_TEXT_IMEI1);
        notUpdatableColumns.add(HEADER_TEXT_IMEI2);
        notUpdatableColumns.add(HEADER_TEXT_IMSI1);
        notUpdatableColumns.add(HEADER_TEXT_IMSI2);
        initializePosMachines();
    }

    private void initializePosMachines() {
        posMachines = getEntityManagerRepo().findAll(sym_device_pos_machine.class, true);
    }

    public List<sym_device_pos_machine> getPosMachines() { return posMachines; }

    public void setPosMachines(List<sym_device_pos_machine> posMachines) { this.posMachines = posMachines; }

    @Override
    public String getTableDescription() { return TABLE_NAME; }
}

