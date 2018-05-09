package net.beyondtelecom.web_ui.session;

import net.beyondtelecom.gopay.bt_common.structure.Pair;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_request_response_log;
import net.beyondtelecom.web_ui.common.JSFReportable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;
import static net.beyondtelecom.gopay.bt_common.utilities.SymTransformer.dateToString;
import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class SystemReportBean extends JSFReportable {

    private static final String TABLE_NAME = "System Logs";
    private List<bt_request_response_log> systemLogs;

    public void initializeSystemLogs() {
        systemLogs = getEntityManagerRepo().findWhere(bt_request_response_log.class, asList(
            new Pair<>("incoming_request_time >", dateToString(reportStartDate)),
            new Pair<>("incoming_request_time <", dateToString(reportEndDate))
        ), true, false, false, true);
    }

    public List<bt_request_response_log> getSystemLogs() { initializeSystemLogs(); return systemLogs; }

    public String getTableDescription() { return TABLE_NAME; }
}

