package net.symbiosis.web_ui.session;

import net.symbiosis.common.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.web_ui.common.JSFReportable;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.Arrays.asList;
import static net.symbiosis.common.utilities.SymTransformer.dateToString;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;

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
    private List<sym_request_response_log> systemLogs;

    public void initializeSystemLogs() {
        systemLogs = getEntityManagerRepo().findWhere(sym_request_response_log.class, asList(
                new Pair<>("incoming_request_time >", dateToString(reportStartDate)),
                new Pair<>("incoming_request_time <", dateToString(reportEndDate))
        ), false, false, false, true);
    }

    public List<sym_request_response_log> getSystemLogs() {
        initializeSystemLogs();
        return systemLogs;
    }

    public String getTableDescription() {
        return TABLE_NAME;
    }
}

