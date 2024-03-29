package net.symbiosis.web_ui.session;

import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.log.sym_voucher_purchase;
import net.symbiosis.web_ui.common.JSFReportable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static net.symbiosis.common.utilities.SymTransformer.dateToString;
import static net.symbiosis.core_lib.utilities.CommonUtilities.formatDoubleToMoney;
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
public class STransactionReportBean extends JSFReportable {

    private final SessionBean sessionBean;
    private static final String TABLE_NAME = "Transactions";
    private List<sym_voucher_purchase> voucherPurchases;
    private List<sym_voucher_purchase> filteredVoucherPurchases;
    private List<String> cashiers = new ArrayList<>();
    private String amountTotal, profitTotal, costTotal;

    @Autowired
    public STransactionReportBean(SessionBean sessionBean) {
        this.sessionBean = sessionBean;
    }

    public void initializeVoucherPurchases() {
        voucherPurchases = getEntityManagerRepo().findWhere(sym_voucher_purchase.class, asList(
            new Pair<>("wallet", sessionBean.getSymbiosisAuthUser().getUser().getWallet().getId()),
            new Pair<>("transaction_time >", dateToString(reportStartDate)),
            new Pair<>("transaction_time <", dateToString(reportEndDate))
        ),true, false, false, true);

        setFilteredVoucherPurchases(voucherPurchases);
        
        cashiers.clear();
        for (sym_voucher_purchase voucherPurchase : voucherPurchases) {
            if (!cashiers.contains(voucherPurchase.getCashier_name())) {
                cashiers.add(voucherPurchase.getCashier_name());
            }
        }

        amountTotal = calculateAmountTotal();
        costTotal = calculateCostTotal();
        profitTotal = calculateProfitTotal();
    }

    public List<sym_voucher_purchase> getVoucherPurchases() {
        if (voucherPurchases == null) { initializeVoucherPurchases(); }
        return voucherPurchases;
    }

    public List<String> getCashiers() { return cashiers; }

    public List<sym_voucher_purchase> getFilteredVoucherPurchases() { return filteredVoucherPurchases; }

    public void setFilteredVoucherPurchases(List<sym_voucher_purchase> filteredVoucherPurchases) {
        if (filteredVoucherPurchases == null) { return; }
        this.filteredVoucherPurchases = filteredVoucherPurchases;
        amountTotal = calculateAmountTotal();
        costTotal = calculateCostTotal();
        profitTotal = calculateProfitTotal();
    }

    public String getAmountTotal() { return amountTotal; }

    private String calculateAmountTotal() {
        BigDecimal total = new BigDecimal(0);
        for (sym_voucher_purchase voucherPurchase : filteredVoucherPurchases) {
            total = total.add(voucherPurchase.getVoucher_value());
        }
        return "Total Amount: " + formatDoubleToMoney(total.doubleValue());
    }

    public String getCostTotal() { return costTotal; }

    private String calculateCostTotal() {
        BigDecimal total = new BigDecimal(0);
        for (sym_voucher_purchase voucherPurchase : filteredVoucherPurchases) {
            total = total.add(voucherPurchase.getWallet_cost());
        }
        return "Total Cost: " + formatDoubleToMoney(total.doubleValue());
    }

    public String getProfitTotal() { return profitTotal; }

    private String calculateProfitTotal() {
        BigDecimal total = new BigDecimal(0);
        for (sym_voucher_purchase voucherPurchase : filteredVoucherPurchases) {
            total = total.add(voucherPurchase.getVoucher_value().subtract(voucherPurchase.getWallet_cost()));
        }
        return "Total Profit: " + formatDoubleToMoney(total.doubleValue());
    }

    public String getDifferenceBetween(BigDecimal value1, BigDecimal value2) {
        return formatDoubleToMoney(value1.subtract(value2).doubleValue());
    }

    @Override
    public String getTableDescription() { return TABLE_NAME; }
}

