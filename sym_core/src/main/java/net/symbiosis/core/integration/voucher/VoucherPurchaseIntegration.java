package net.symbiosis.core.integration.voucher;

import net.symbiosis.core_lib.response.SymResponseObject;

import java.math.BigDecimal;

/***************************************************************************
 *                                                                         *
 * Created:     29 / 04 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public interface VoucherPurchaseIntegration {
    SymResponseObject<String> purchaseVoucher(Long senderReference, Long voucherId, BigDecimal amount, String recipient);
}
