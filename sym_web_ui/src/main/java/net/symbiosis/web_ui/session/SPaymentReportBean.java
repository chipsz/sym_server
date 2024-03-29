package net.symbiosis.web_ui.session;

import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.log.sym_incoming_payment;
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
public class SPaymentReportBean extends JSFReportable {

    private static final String TABLE_NAME = "Payments";
    private final SessionBean sessionBean;
    private List<sym_incoming_payment> payments;

    @Autowired
    public SPaymentReportBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public void initializePayments() {
        payments = getEntityManagerRepo().findWhere(sym_incoming_payment.class, asList(
            new Pair<>("wallet", sessionBean.getSymbiosisAuthUser().getUser().getWallet().getId()),
            new Pair<>("time_loaded >", dateToString(reportStartDate)),
            new Pair<>("time_loaded <", dateToString(reportEndDate))
        ), true, false, false, true);
    }

    public List<sym_incoming_payment> getPayments() { initializePayments(); return payments; }

    @Override
    public String getTableDescription() { return TABLE_NAME; }

}

