package net.symbiosis.core.integration.voucher;

import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher_provider;

import java.math.BigDecimal;

/***************************************************************************
 *                                                                         *
 * Created:     29 / 04 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public interface VoucherPurchaseIntegration {
    String getIntegrationName();
    sym_voucher_provider getVoucherProvider();
    SymResponseObject<String> purchaseVoucher(Long senderReference, Long voucherId, BigDecimal amount, String recipient);
}
