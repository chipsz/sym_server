package net.symbiosis.web_ui.session;

import net.symbiosis.web_ui.common.JSFUpdatable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import static net.symbiosis.web_ui.common.DataTableHeaders.*;

/***************************************************************************
 *                                                                         *
 * Created:     18 / 06 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class WalletGroupTransferChargeBean extends JSFUpdatable {

    private static final String TABLE_NAME = "Transfer Charges";

    @Autowired
    public WalletGroupTransferChargeBean(SessionBean sessionBean) {
        super(sessionBean);
    }

    @PostConstruct
    public void init() {
        notUpdatableColumns.add(HEADER_TEXT_ID);
        updatableColumns.add(HEADER_TEXT_NAME);
        updatableColumns.add(HEADER_TEXT_ENABLED);
        updatableColumns.add(HEADER_TEXT_STARTING_VALUE);
        updatableColumns.add(HEADER_TEXT_ENDING_VALUE);
        updatableColumns.add(HEADER_TEXT_TRANSFER_CHARGE);
    }

    @Override
    public String getTableDescription() { return TABLE_NAME; }
}

