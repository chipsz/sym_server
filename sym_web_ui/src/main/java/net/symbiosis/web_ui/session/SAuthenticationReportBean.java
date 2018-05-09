package net.symbiosis.web_ui.session;

import net.symbiosis.common.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.log.sym_session;
import net.symbiosis.web_ui.common.JSFReportable;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SAuthenticationReportBean extends JSFReportable {

    private static final String TABLE_NAME = "Authentications";
    private final SessionBean sessionBean;

    private List<sym_session> sessions;

    @Autowired
    public SAuthenticationReportBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public void initializeSessions() {
        sessions = getEntityManagerRepo().findWhere(sym_session.class, asList(
                new Pair<>("auth_user.user", sessionBean.getSymbiosisAuthUser().getUser().getId()),
                new Pair<>("start_time >", dateToString(reportStartDate)),
                new Pair<>("start_time <", dateToString(reportEndDate))
        ), false, false, false, true);
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

