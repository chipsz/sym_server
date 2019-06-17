package net.symbiosis.web_ui.common;

import net.symbiosis.persistence.entity.complex_type.device.sym_device_phone;
import net.symbiosis.persistence.entity.complex_type.device.sym_device_pos_machine;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.sym_company;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_service_provider;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher_provider;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet_group;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet_group_voucher_discount;
import net.symbiosis.persistence.entity.enumeration.sym_event_type;
import net.symbiosis.persistence.entity.enumeration.sym_response_code;
import net.symbiosis.persistence.entity.enumeration.sym_voucher_type;
import net.symbiosis.persistence.entity.super_class.sym_entity;
import net.symbiosis.web_ui.session.SessionBean;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static javax.faces.application.FacesMessage.SEVERITY_FATAL;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.symbiosis.core_lib.enumeration.SymEventType.*;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.GENERAL_ERROR;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static net.symbiosis.core_lib.utilities.CommonUtilities.getRealThrowable;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@ManagedBean
public abstract class JSFUpdatable extends JSFExportable implements JSFLoggable, Serializable {

    private static final Logger logger = Logger.getLogger(JSFUpdatable.class.getSimpleName());
    protected final SessionBean sessionBean;
    protected List<String> updatableColumns = new ArrayList<>();
    protected List<String> notUpdatableColumns = new ArrayList<>();
    private sym_response_code SUCCESS_RESPONSE = fromEnum(SUCCESS);
    private sym_response_code FAILURE_RESPONSE = fromEnum(GENERAL_ERROR);

    @Autowired
    public JSFUpdatable(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    //populate updatable and unUpdatable column lists
    @PostConstruct
    public abstract void init();

    private sym_event_type getEventType(sym_entity updateTable) {
        if (updateTable instanceof sym_company || updateTable instanceof sym_wallet) { return fromEnum(WALLET_UPDATE); }
        if (updateTable instanceof sym_user || updateTable instanceof sym_auth_user) { return fromEnum(USER_UPDATE); }
        if (updateTable instanceof sym_voucher) { return fromEnum(VOUCHER_UPDATE); }
        if (updateTable instanceof sym_voucher_type) { return fromEnum(VOUCHER_TYPE_UPDATE); }
        if (updateTable instanceof sym_service_provider) { return fromEnum(SERVICE_PROVIDER_UPDATE); }
        if (updateTable instanceof sym_voucher_provider) { return fromEnum(VOUCHER_PROVIDER_UPDATE); }
        if (updateTable instanceof sym_wallet_group) { return fromEnum(WALLET_GROUP_UPDATE); }
        if (updateTable instanceof sym_wallet_group_voucher_discount) { return fromEnum(WALLET_GROUP_VOUCHER_DISCOUNT_UPDATE); }
        if (updateTable instanceof sym_device_pos_machine) { return fromEnum(DEVICE_POS_MACHINE_UPDATE); }
        if (updateTable instanceof sym_device_phone) { return fromEnum(DEVICE_PHONE_UPDATE); }
        return null;
    }

    public void onRowEdit(RowEditEvent event) {
    }

    public void onRowCancel(RowEditEvent event) {
    }

    public void onCellEdit(CellEditEvent event) {

        Date requestTime = new Date();

        //TODO check permissions
        Object oldValue = event.getOldValue(), newValue = event.getNewValue();
        String columnName = event.getColumn().getHeaderText();

        logger.info(format("Got request to update \"%s\" column from \"%s\" to \"%s\"",
                columnName, oldValue, newValue));

        if (newValue != null && !newValue.equals(oldValue)) {

            if (notUpdatableColumns.contains(columnName)) {
                getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_FATAL,
                        "Manual update not permitted",
                        format("The value of the column %s cannot be manually modified", columnName)));
                logger.info("The value of the column %s cannot be manually modified");
                return;
            } else if (updatableColumns.contains(columnName)) {

                sym_response_code responseCode;
                try {
                    ((sym_entity) ((DataTable) event.getComponent()).getRowData()).save();
                    logger.info(format("Data updated successfully. Old Value: %s, New Value: %s", oldValue, newValue));
                    getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_INFO,
                            "Data updated successfully",
                            "Successfully updated Old Value: '" + oldValue + "' to New Value: '" + newValue + "'"));
                    responseCode = SUCCESS_RESPONSE;
                } catch (Exception ex) {
                    logger.info(format("Failed to update %s from %s to %s! %s", columnName, oldValue, newValue, ex.getMessage()));
                    getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_FATAL,
                            "Failed to update %s",
                            format("Failed to update %s from %s to %s! %s", columnName, oldValue, newValue, ex.getMessage())));
                    responseCode = FAILURE_RESPONSE;
                    responseCode.setResponse_message(getRealThrowable(ex).getMessage());
//                    DataTable eventTable = (DataTable) event.getComponent();
                }

                sym_event_type eventType = getEventType((sym_entity) ((DataTable) event.getComponent()).getRowData());

                String incomingRequest = format("UPDATE \"%s\" from \"%s\" to \"%s\"", columnName, oldValue, newValue);
                log(eventType, sessionBean.getSymbiosisAuthUser(), responseCode, requestTime,
                        new Date(), incomingRequest, responseCode.getResponse_message());

            }
        }
    }

}
