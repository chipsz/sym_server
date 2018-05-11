package net.symbiosis.web_ui.session;

import net.symbiosis.web_ui.common.JSFUpdatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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
public class WalletGroupVoucherBean extends JSFUpdatable {

    private static final String TABLE_NAME = "Group Vouchers";

    @Autowired
    public WalletGroupVoucherBean(SessionBean sessionBean) {
        super(sessionBean);
    }

    @PostConstruct
    public void init() {
        notUpdatableColumns.add(HEADER_TEXT_ID);
        notUpdatableColumns.add(HEADER_TEXT_W_GROUP);
        notUpdatableColumns.add(HEADER_TEXT_V_DESCRIPTION);
        updatableColumns.add(HEADER_TEXT_M_DISCOUNT);
    }

    @Override
    public String getTableDescription() { return TABLE_NAME; }
}

