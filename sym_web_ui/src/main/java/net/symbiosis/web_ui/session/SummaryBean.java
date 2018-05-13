package net.symbiosis.web_ui.session;

import net.symbiosis.common.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.log.sym_incoming_payment;
import net.symbiosis.persistence.entity.complex_type.sym_company;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_pinbased_voucher;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_service_provider;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher_purchase;
import net.symbiosis.web_ui.common.JSFReportable;
import net.symbiosis.web_ui.data.VoucherStockData;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.logging.Logger;

import static java.util.Arrays.asList;
import static java.util.Calendar.*;
import static java.util.Collections.singletonList;
import static net.symbiosis.common.utilities.SymTransformer.dateToString;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.*;
import static net.symbiosis.core_lib.enumeration.SymVoucherStatus.NEW;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findEnabled;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 03 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class SummaryBean extends JSFReportable {

    private static final Logger logger = Logger.getLogger(SummaryBean.class.getSimpleName());
    private static final String TABLE_NAME = "Voucher Stock";
    private List<VoucherStockData> voucherStockList = new ArrayList<>();
    private String todayStartTime = null;

    private void initializeVoucherStock() {
        List<sym_service_provider> serviceProviders = findEnabled(sym_service_provider.class);
        voucherStockList.clear();
        logger.info(String.format("Found %s enabled service providers", serviceProviders.size()));

        for (sym_service_provider serviceProvider : serviceProviders) {

            logger.info(String.format("Processing service provider %s", serviceProvider.getName()));

            if (serviceProvider.getVouchers() == null) { continue; }

            for (sym_voucher voucher : serviceProvider.getVouchers()) {

                if (!voucher.getIs_active() || !voucher.getVoucher_provider().getIs_enabled()) { continue; }

                Long stockQuantity = getEntityManagerRepo().countWhere(sym_pinbased_voucher.class, asList(
                        new Pair<String, Object>("voucher", voucher.getId()),
                        new Pair<String, Object>("voucher_status", fromEnum(NEW).getId()))
                );

                String voucherDescription = (voucher.getUnits() == null ? "" : voucher.getUnits())
                        + voucher.getVoucher_value() + " "
                        + voucher.getService_provider().getName() + " "
                        + voucher.getVoucher_type().getName();

                logger.info(String.format("Processing voucher %s", voucherDescription));
                logger.info(String.format("Found %s vouchers", stockQuantity));

                voucherStockList.add(new VoucherStockData(voucher, stockQuantity.intValue()));
            }
        }
    }

    public List<VoucherStockData> getVoucherStockList() {
        initializeVoucherStock();
        return voucherStockList;
    }

    public void setVoucherStockList(List<VoucherStockData> voucherStockList) {
        this.voucherStockList = voucherStockList;
    }

    private String getTodayStartTime() {
        if (todayStartTime == null) {
            Calendar date = Calendar.getInstance();
            date.set(HOUR_OF_DAY, 0);
            date.set(MINUTE, 0);
            date.set(SECOND, 0);
            date.set(MILLISECOND, 0);
            logger.info("Using start time as " + date.getTime());
            todayStartTime = dateToString(date.getTime());
        }
        return todayStartTime;
    }

    public Long getTotalTransactions() {
        return getEntityManagerRepo().countAll(sym_voucher_purchase.class);
    }

    public Long getTotalTransactionsToday() {
        return getEntityManagerRepo().countWhere(sym_voucher_purchase.class, singletonList(
                new Pair<>("transaction_time >", getTodayStartTime())
        ));
    }

    public Long getTotalPayments() {
        return getEntityManagerRepo().countAll(sym_incoming_payment.class);
    }

    public Long getTotalPaymentsToday() {
        return getEntityManagerRepo().countWhere(sym_incoming_payment.class, singletonList(
                new Pair<>("time_loaded >", getTodayStartTime())
        ));
    }

    public Long getTotalSuccessfulTransactions() {
        return getEntityManagerRepo().countWhere(sym_voucher_purchase.class, singletonList(
                new Pair<>("response_code_id", fromEnum(SUCCESS).getId())
        ));
    }

    public Long getTotalSuccessfulTransactionsToday() {
        return getEntityManagerRepo().countWhere(sym_voucher_purchase.class, asList(
                new Pair<>("transaction_time >", getTodayStartTime()),
                new Pair<>("response_code_id", fromEnum(SUCCESS).getId())
        ));
    }

    public Long getTotalErrorsToday() {
        return getEntityManagerRepo().countWhere(sym_voucher_purchase.class, asList(
                new Pair<>("transaction_time >", getTodayStartTime()),
                new Pair<>("response_code !", fromEnum(SUCCESS))
        ));
    }

    public Long getStockErrorsToday() {
        return getEntityManagerRepo().countWhere(sym_voucher_purchase.class, asList(
                new Pair<>("transaction_time >", getTodayStartTime()),
                new Pair<>("response_code_id", fromEnum(INSUFFICIENT_STOCK).getId())
        ));
    }

    public Long getFundsErrorsToday() {
        return getEntityManagerRepo().countWhere(sym_voucher_purchase.class, asList(
                new Pair<>("transaction_time >", getTodayStartTime()),
                new Pair<>("response_code_id", fromEnum(INSUFFICIENT_FUNDS).getId())
        ));
    }

    public Long getSystemErrorsToday() {
        return getEntityManagerRepo().countWhere(sym_voucher_purchase.class, asList(
                new Pair<>("transaction_time >", getTodayStartTime()),
                new Pair<>("response_code_id", fromEnum(GENERAL_ERROR).getId())
        ));
    }

    public Long getTotalMerchants() {
        return getEntityManagerRepo().countAll(sym_company.class);
    }

    public Long getTotalUsers() {
        return getEntityManagerRepo().countAll(sym_user.class);
    }

    public Long getActiveVouchers() {
        return getEntityManagerRepo().countWhere(sym_voucher.class, asList(
                new Pair<>("is_active", 1),
                new Pair<>("voucher_provider.is_enabled", 1),
                new Pair<>("service_provider.is_enabled", 1)
        ));
    }

    @Override
    public String getTableDescription() { return TABLE_NAME; }
}
