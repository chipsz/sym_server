package net.symbiosis.web_ui.session;

import net.symbiosis.common.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.log.sym_session;
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
public class AuthReportBean extends JSFReportable {

    private static final String TABLE_NAME = "Authentications";
    private List<sym_session> sessions;

    public void initializeSessions() {
        sessions = getEntityManagerRepo().findWhere(sym_session.class, asList(
                new Pair<>("start_time >", dateToString(reportStartDate)),
                new Pair<>("start_time <", dateToString(reportEndDate))
        ), true, false, false, true);
    }

    public List<sym_session> getSessions() {
        initializeSessions();
        return sessions;
    }

    @Override
    public String getTableDescription() {
        return TABLE_NAME;
    }
}

