package net.symbiosis.web_ui.session;

import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher;
import net.symbiosis.web_ui.common.JSFUpdatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.web_ui.common.DataTableHeaders.*;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class VoucherBean extends JSFUpdatable {

    private static final String TABLE_NAME = "Voucher Configuration";
    private List<sym_voucher> vouchers;

    @Autowired
    public VoucherBean(SessionBean sessionBean) { super(sessionBean); }

    @PostConstruct
    public void init() {
        notUpdatableColumns.add(HEADER_TEXT_ID);
        notUpdatableColumns.add(HEADER_TEXT_VP);
        notUpdatableColumns.add(HEADER_TEXT_SP);
        notUpdatableColumns.add(HEADER_TEXT_VALUE);
        notUpdatableColumns.add(HEADER_TEXT_VTYPE);
        updatableColumns.add(HEADER_TEXT_UNITS);
        updatableColumns.add(HEADER_TEXT_ENABLED);
        updatableColumns.add(HEADER_TEXT_VP_DISCOUNT);
        notUpdatableColumns.add(HEADER_TEXT_IS_FIXED);
        updatableColumns.add(HEADER_TEXT_IS_PINBASED);
        initializeVouchers();
    }

    public void initializeVouchers() {
        vouchers = getEntityManagerRepo().findWhere(
            sym_voucher.class, new ArrayList<>(
                asList(
                    new Pair<>("voucher_type.is_enabled", 1),
                    new Pair<>("voucher_provider.is_enabled", 1),
                    new Pair<>("service_provider.is_enabled", 1)
                )
            )
        );
    }

    public List<sym_voucher> getVouchers() { return vouchers; }

    public void setVouchers(List<sym_voucher> vouchers) { this.vouchers = vouchers; }

    @Override
    public String getTableDescription() { return TABLE_NAME; }
}

