package net.beyondtelecom.web_ui.common;

import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.*;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_event_type;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_response_code;
import net.beyondtelecom.gopay.bt_persistence.entity.super_class.bt_entity;
import net.beyondtelecom.web_ui.session.SessionBean;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static javax.faces.application.FacesMessage.SEVERITY_FATAL;
import static javax.faces.application.FacesMessage.SEVERITY_INFO;
import static javax.faces.context.FacesContext.getCurrentInstance;
import static net.beyondtelecom.bt_core_lib.enumeration.BTEventType.*;
import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.GENERAL_ERROR;
import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.SUCCESS;
import static net.beyondtelecom.bt_core_lib.utilities.CommonUtilities.getRealThrowable;
import static net.beyondtelecom.gopay.bt_persistence.helper.BTEnumHelper.fromEnum;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public abstract class JSFUpdatable extends JSFExportable implements JSFLoggable, Serializable {

    private static final Logger logger = Logger.getLogger(JSFUpdatable.class.getSimpleName());
    protected final SessionBean sessionBean;
    protected List<String> updatableColumns = new ArrayList<>();
    protected List<String> notUpdatableColumns = new ArrayList<>();
    private bt_response_code SUCCESS_RESPONSE = fromEnum(SUCCESS);
    private bt_response_code FAILURE_RESPONSE = fromEnum(GENERAL_ERROR);

    @Autowired
    public JSFUpdatable(SessionBean sessionBean) { this.sessionBean = sessionBean; }

    //populate updatable and unUpdatable column lists
    @PostConstruct public abstract void init();

    private bt_event_type getEventType(bt_entity updateTable) {
        if (updateTable instanceof bt_company || updateTable instanceof bt_wallet) { return fromEnum(UPDATE_MERCHANT); }
        if (updateTable instanceof bt_user || updateTable instanceof bt_auth_user) { return fromEnum(UPDATE_USER); }
        if (updateTable instanceof bt_wallet_group) { return fromEnum(UPDATE_WALLET_GROUP); }
        return null;
    }

    public void onRowEdit(RowEditEvent event) {}

    public void onRowCancel(RowEditEvent event) {}

    public void onCellEdit(CellEditEvent event) {

        Date requestTime = new Date();

        //TODO check permissions
        Object oldValue = event.getOldValue(), newValue = event.getNewValue();
        String columnName = event.getColumn().getHeaderText();

        logger.info(format("Got request to update \"%s\" column from \"%s\" to \"%s\"",
                columnName, oldValue, newValue));

        if(newValue != null && !newValue.equals(oldValue)) {

            if (notUpdatableColumns.contains(columnName)) {
                getCurrentInstance().addMessage(null, new FacesMessage(SEVERITY_FATAL,
                        "Manual update not permitted",
                        format("The value of the column %s cannot be manually modified", columnName)));
                logger.info("The value of the column %s cannot be manually modified");
                return;
            } else if (updatableColumns.contains(columnName)) {

                bt_response_code responseCode;
                try {
                    ((bt_entity) ((DataTable) event.getComponent()).getRowData()).save();
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

                bt_event_type eventType = getEventType((bt_entity)((DataTable)event.getComponent()).getRowData());

                String incomingRequest = format("UPDATE \"%s\" from \"%s\" to \"%s\"", columnName, oldValue, newValue);
                log(eventType, sessionBean.getSymbiosisAuthUser(), responseCode, requestTime,
                        new Date(), incomingRequest, responseCode.getResponse_message());

            }
        }
    }

}
