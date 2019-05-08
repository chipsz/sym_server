package net.symbiosis.core.service;

import net.symbiosis.core.integration.voucher.VoucherPurchaseIntegration;

/***************************************************************************
 *                                                                         *
 * Created:     29 / 04 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public interface IntegrationManagerService {

    void registerVoucherPurchaseIntegration(Long voucherProviderId, VoucherPurchaseIntegration integration);

    VoucherPurchaseIntegration getVoucherPurchaseIntegration(Long voucherProviderId);
}
