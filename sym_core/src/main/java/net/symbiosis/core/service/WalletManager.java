package net.symbiosis.core.service;

import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.log.sym_wallet_transaction;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;

/***************************************************************************
 * Created:     9/5/2018                                                  *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     kaviyat@payserv.co.zw                                      *
 **************************************************************************/

public interface WalletManager {
    SymResponseObject<sym_wallet> updateWalletBalance(sym_wallet_transaction walletTransactionDetails);
    SymResponseObject<sym_wallet> transferWalletBalanceWithCharges(sym_wallet_transaction fromWalletDetails, sym_wallet_transaction toWalletDetails);
}
