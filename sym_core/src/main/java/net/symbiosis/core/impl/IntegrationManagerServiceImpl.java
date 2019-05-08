package net.symbiosis.core.impl;

import net.symbiosis.core.integration.voucher.VoucherPurchaseIntegration;
import net.symbiosis.core.service.IntegrationManagerService;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/***************************************************************************
 *                                                                         *
 * Created:     29 / 04 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Service
public class IntegrationManagerServiceImpl implements IntegrationManagerService {

    private HashMap<Long, VoucherPurchaseIntegration> voucherPurchaseIntegrations = new HashMap<>();

    @Override
    public void registerVoucherPurchaseIntegration(Long voucherProviderId, VoucherPurchaseIntegration integration) {
        voucherPurchaseIntegrations.put(voucherProviderId, integration);
    }

    @Override
    public VoucherPurchaseIntegration getVoucherPurchaseIntegration(Long voucherProviderId) {
        return voucherPurchaseIntegrations.get(voucherProviderId);
    }
}
