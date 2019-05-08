package net.symbiosis.core.integration.voucher.glo;

import net.symbiosis.core.integration.voucher.VoucherPurchaseIntegration;
import net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.*;
import net.symbiosis.core.service.IntegrationManagerService;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher_provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.Long.parseLong;
import static net.symbiosis.core_lib.enumeration.DBConfigVars.CONFIG_GLO_SERVICE_REQUEST_TIMEOUT;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.GENERAL_ERROR;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.DaoManager.getSymConfigDao;

/***************************************************************************
 *                                                                         *
 * Created:     29 / 04 / 2019                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Service
public class GloIntegrationService implements VoucherPurchaseIntegration {

    private static Logger logger = Logger.getLogger(GloIntegrationService.class.getSimpleName());
    private static final String INTEGRATION_ID = "GloSeamless";

    private static ERSWSTopupServiceImplServiceStub topupService = null;

    @Autowired
    GloIntegrationService(IntegrationManagerService integrationManagerService) {

        List<sym_voucher_provider> voucher_providers = getEntityManagerRepo()
            .findWhere(sym_voucher_provider.class, new Pair<>("integration_id", INTEGRATION_ID));

        if (voucher_providers != null && voucher_providers.size() > 0) {
            integrationManagerService.registerVoucherPurchaseIntegration(voucher_providers.get(0).getId(), this);
        }
    }

    private static SymResponseObject<ERSWSTopupServiceImplServiceStub> getTopupService() {
        if (topupService == null) {
            try {
                topupService = new ERSWSTopupServiceImplServiceStub();
            } catch (Exception ex) {
                ex.printStackTrace();
                return new SymResponseObject<ERSWSTopupServiceImplServiceStub>(GENERAL_ERROR).setMessage(ex.getMessage());
            }
        }
        return new SymResponseObject<>(SUCCESS, topupService);
    }

    @Override
    public SymResponseObject<String> purchaseVoucher(Long senderReference, Long voucherId, BigDecimal amount, String recipient) {

        SymResponseObject<ERSWSTopupServiceImplServiceStub> topupService = getTopupService();
        if (!topupService.getResponseCode().equals(SUCCESS)) {
            return new SymResponseObject<String>(topupService.getResponseCode()).setMessage(topupService.getMessage());
        }
        try {
            RequestTopup requestTopup = new RequestTopup();

            ClientContext clientContext = new ClientContext();
            clientContext.setChannel("GLOWEBSERVICE");
            clientContext.setClientComment("Testing topup");
            clientContext.setClientId("Etopup Systems");
            clientContext.setClientReference(String.valueOf(senderReference));
            clientContext.setClientRequestTimeout(parseLong(getSymConfigDao().getConfig(CONFIG_GLO_SERVICE_REQUEST_TIMEOUT)));

            PrincipalId initiatorPrincipalId = new PrincipalId();
            initiatorPrincipalId.setId("EMPOWER");
            initiatorPrincipalId.setType("RESELLERUSER");
            initiatorPrincipalId.setUserId("empowertst");
            clientContext.setInitiatorPrincipalId(initiatorPrincipalId);
            clientContext.setPassword("empowertst0419");
            clientContext.setPrepareOnly(false);
            requestTopup.setContext(clientContext);

            PrincipalId senderPrincipalId = new PrincipalId();
            senderPrincipalId.setId("EMPOWER");
            senderPrincipalId.setType("RESELLERUSER");
            senderPrincipalId.setUserId("empowertst");
            requestTopup.setSenderPrincipalId(senderPrincipalId);

            PrincipalId topupPrincipalId = new PrincipalId();
            topupPrincipalId.setId("230089518");
            topupPrincipalId.setType("SUBSCRIBERMSISDN");
            topupPrincipalId.setUserId(recipient);
            requestTopup.setTopupPrincipalId(topupPrincipalId);

            PrincipalAccountSpecifier senderAccountSpecifier = new PrincipalAccountSpecifier();
            senderAccountSpecifier.setAccountId("?");
            senderAccountSpecifier.setAccountTypeId("RESELLER");
            requestTopup.setSenderAccountSpecifier(senderAccountSpecifier);

            PrincipalAccountSpecifier topupAccountSpecifier = new PrincipalAccountSpecifier();
            topupAccountSpecifier.setAccountId("?");
            topupAccountSpecifier.setAccountTypeId("RESELLER");
            requestTopup.setTopupAccountSpecifier(topupAccountSpecifier);

            requestTopup.setProductId("TOPUP");
            Amount sAmount = new Amount();
            sAmount.setValue(amount);
            requestTopup.setAmount(sAmount);

            RequestTopupE requestTopupE = new RequestTopupE();
            requestTopupE.setRequestTopup(requestTopup);

            logger.info("Performing Glo topup for amount: " + amount.doubleValue());
            RequestTopupResponseE topupResponseE = topupService.getResponseObject().requestTopup(requestTopupE);
            logger.info("Purchase response: " +
                topupResponseE.getRequestTopupResponse().get_return().getResultCode() + ":" +
                topupResponseE.getRequestTopupResponse().get_return().getResultDescription()
            );
            //return voucher provider reference
            return new SymResponseObject<>(SUCCESS, topupResponseE.getRequestTopupResponse().get_return().getErsReference());
        } catch (Exception ex) {
            return new SymResponseObject<String>(GENERAL_ERROR).setMessage(ex.getMessage());
        }

    }
}
