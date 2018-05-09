package net.symbiosis.core.implementation;

import net.symbiosis.core.service.WalletManager;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.sym_wallet;

import java.math.BigDecimal;
import java.util.logging.Logger;

import static java.lang.String.format;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.*;

/***************************************************************************
 * Created:     23 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 ***************************************************************************/

public class WalletManagerImpl implements WalletManager {

    private static final Logger logger = Logger.getLogger(WalletManagerImpl.class.getSimpleName());

    @Override
    public synchronized SymResponseObject<sym_wallet> updateWalletBalance(sym_wallet wallet, BigDecimal amount) {

        if (wallet == null || amount == null) {
            return new SymResponseObject<>(INPUT_INCOMPLETE_REQUEST, wallet);
        }

        logger.info(format("Updating wallet %s balance (%s) with amount %s",
                wallet.getId(), wallet.getCurrent_balance().toPlainString(), amount.toPlainString()));

        try {
            //make sure we are using an up to date balance
            wallet.refresh();
            //check if new balance will be greater than or equal to 0
            if (wallet.getCurrent_balance().add(amount).compareTo(new BigDecimal(0.0)) < 0) {
                return new SymResponseObject<>(INSUFFICIENT_FUNDS, wallet);
            }

            wallet.setCurrent_balance(wallet.getCurrent_balance().add(amount)).save();

            logger.info(format("New balance for wallet %s is %s", wallet.getId(),
                    wallet.getCurrent_balance().toPlainString()));

            return new SymResponseObject<>(SUCCESS, wallet);
        } catch (Exception ex) {
            ex.printStackTrace();
            return new SymResponseObject<>(GENERAL_ERROR, wallet);
        }
    }
}
