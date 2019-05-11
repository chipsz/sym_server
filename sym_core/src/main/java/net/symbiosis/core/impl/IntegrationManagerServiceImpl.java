package net.symbiosis.core.impl;

import net.symbiosis.core.integration.voucher.VoucherPurchaseIntegration;
import net.symbiosis.core.service.IntegrationManagerService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.logging.Logger;

import static java.lang.String.format;

/***************************************************************************
 *                                                                         *
 * Created:     29 / 04 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Service
public class IntegrationManagerServiceImpl implements IntegrationManagerService {

    private Logger logger = Logger.getLogger(this.getClass().getSimpleName());
    private HashMap<Long, VoucherPurchaseIntegration> voucherPurchaseIntegrations = new HashMap<>();

    @Override
    public void registerVoucherPurchaseIntegration(Long voucherProviderId, VoucherPurchaseIntegration integration) {
        voucherPurchaseIntegrations.put(voucherProviderId, integration);
        logger.info(format("Added provider %s to list of integrations with class %s for voucher provider %s:%s ",
            integration.getIntegrationName(), integration.getClass().getSimpleName(),
            voucherProviderId, integration.getVoucherProvider().getName()));

        logger.info("Displaying available voucher provider integrations");
        for (Long vpId : voucherPurchaseIntegrations.keySet()) {
            logger.info(format("Voucher Provider %s has integration %s", vpId, voucherPurchaseIntegrations.get(vpId).getIntegrationName()));
        }
    }

    @Override
    public VoucherPurchaseIntegration getVoucherPurchaseIntegration(Long voucherProviderId) {
        logger.info("Displaying available voucher provider integrations");
        for (Long vpId : voucherPurchaseIntegrations.keySet()) {
            logger.info(format("Voucher Provider %s has integration %s", vpId, voucherPurchaseIntegrations.get(vpId).getIntegrationName()));
        }
        return voucherPurchaseIntegrations.get(voucherProviderId);
    }
}
