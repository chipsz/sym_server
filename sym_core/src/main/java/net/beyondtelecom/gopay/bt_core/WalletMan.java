package net.beyondtelecom.gopay.bt_core;

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_wallet;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static java.lang.String.format;
import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.*;

/***************************************************************************
 * Created:     23 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

public class WalletMan {

    private static final Logger logger = Logger.getLogger(WalletMan.class.getSimpleName());

    public static synchronized BTResponseCode updateWalletBalance(bt_wallet wallet, BigDecimal amount) {

        if (wallet == null || amount == null) { return INPUT_INCOMPLETE_REQUEST; }

        logger.info(format("Updating wallet %s balance (%s) with amount %s",
                wallet.getId(), wallet.getCurrent_balance().toPlainString(), amount.toPlainString()));

        try {
            //make sure we are using an up to date balance
            wallet.refresh();
            //check if new balance will be greater than or equal to 0
            if (wallet.getCurrent_balance().add(amount).compareTo(new BigDecimal(0.0)) < 0) {
                return INSUFFICIENT_FUNDS;
            }

            wallet.setCurrent_balance(wallet.getCurrent_balance().add(amount)).save();

            logger.info(format("New balance for wallet %s is %s", wallet.getId(),
                    wallet.getCurrent_balance().toPlainString()));

            return SUCCESS;
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return GENERAL_ERROR;
        }
    }
}
