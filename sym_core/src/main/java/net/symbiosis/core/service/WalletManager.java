package net.symbiosis.core.service;

import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;

import java.math.BigDecimal;

/***************************************************************************
 * Created:     9/5/2018                                                  *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     kaviyat@payserv.co.zw                                      *
 **************************************************************************/

public interface WalletManager {
    SymResponseObject<sym_wallet> updateWalletBalance(sym_wallet wallet, BigDecimal amount);
}
