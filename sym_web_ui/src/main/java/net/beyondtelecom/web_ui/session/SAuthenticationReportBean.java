package net.beyondtelecom.web_ui.session;

import net.beyondtelecom.gopay.bt_common.structure.Pair;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_session;
import net.beyondtelecom.web_ui.common.JSFReportable;
import org.springframework.beans.factory.annotation.Autowired;
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
public class SAuthenticationReportBean extends JSFReportable {

    private static final String TABLE_NAME = "Authentications";
    private final SessionBean sessionBean;

    private List<bt_session> sessions;

    @Autowired
    public SAuthenticationReportBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public void initializeSessions() {
        sessions = getEntityManagerRepo().findWhere(bt_session.class, asList(
            new Pair<>("auth_user.user", sessionBean.getSymbiosisAuthUser().getUser().getId()),
            new Pair<>("start_time >", dateToString(reportStartDate)),
            new Pair<>("start_time <", dateToString(reportEndDate))
        ), true, false, false, true);
    }

    public List<bt_session> getSessions() { initializeSessions(); return sessions; }

    @Override
    public String getTableDescription() { return TABLE_NAME; }
}

