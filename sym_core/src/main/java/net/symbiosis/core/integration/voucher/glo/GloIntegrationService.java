package net.symbiosis.core.integration.voucher.glo;

import net.symbiosis.core.integration.voucher.VoucherPurchaseIntegration;
import net.symbiosis.core.integration.voucher.glo.seamless.*;
import net.symbiosis.core.service.IntegrationManagerService;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.log.sym_integration_log;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher_provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static net.symbiosis.common.configuration.Configuration.getProperty;
import static net.symbiosis.common.configuration.NetworkUtilities.sendEmailAlert;
import static net.symbiosis.core_lib.enumeration.DBConfigVars.*;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.*;
import static net.symbiosis.core_lib.security.Security.decryptAES;
import static net.symbiosis.core_lib.utilities.CommonUtilities.formatFullMsisdn;
import static net.symbiosis.core_lib.utilities.CommonUtilities.throwableAsString;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.DaoManager.getSymConfigDao;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;
import static net.symbiosis.persistence.helper.SymEnumHelper.getMappedResponseMessage;

//import net.symbiosis.core.integration.voucher.glo.ERSWSTopupServiceImplServiceStub.*;

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
    private static ERSWSTopupServiceImplServiceLocator serviceLocator = null;
    private sym_voucher_provider voucherProvider;

    @Autowired
    GloIntegrationService(IntegrationManagerService integrationManagerService) {
        logger.info("Registering GloIntegrationService with integration id '" + INTEGRATION_ID + "'");
        List<sym_voucher_provider> voucherProviders = getEntityManagerRepo()
            .findWhere(sym_voucher_provider.class, new Pair<>("integration_id", INTEGRATION_ID));
        this.voucherProvider = voucherProviders.get(0);
        integrationManagerService.registerVoucherPurchaseIntegration(voucherProvider.getId(), this);
    }

    private static SymResponseObject<ERSWSTopupServiceImplServiceStub> getTopupService2() {
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

    private static SymResponseObject<ERSWSTopupServiceImplServiceLocator> getTopupService() {
        if (serviceLocator == null) {
            try {
	            serviceLocator = new ERSWSTopupServiceImplServiceLocator();
            } catch (Exception ex) {
                ex.printStackTrace();
                return new SymResponseObject<ERSWSTopupServiceImplServiceLocator>(GENERAL_ERROR).setMessage(ex.getMessage());
            }
        }
        return new SymResponseObject<>(SUCCESS, serviceLocator);
    }

    @Override
    public String getIntegrationName() { return INTEGRATION_ID; }

    @Override
    public sym_voucher_provider getVoucherProvider() { return this.voucherProvider; }

    @Override
    public SymResponseObject<String> purchaseVoucher(Long senderReference, Long voucherId, BigDecimal amount, String recipient) {

    	SymResponseObject<ERSWSTopupServiceImplServiceLocator> topupService = getTopupService();
        if (!topupService.getResponseCode().equals(SUCCESS)) {
            return new SymResponseObject<String>(topupService.getResponseCode()).setMessage(topupService.getMessage());
        }

        sym_integration_log transactionLog = new sym_integration_log();

    	try {
		    ClientContext clientContext = new ClientContext();
		    clientContext.setChannel("WEBSERVICE");
		    clientContext.setClientComment("");
		    clientContext.setClientId(getSymConfigDao().getConfig(CONFIG_GLO_SERVICE_CLIENT_ID));
		    clientContext.setClientReference(String.valueOf(senderReference));
		    clientContext.setClientRequestTimeout(parseLong(getSymConfigDao().getConfig(CONFIG_GLO_SERVICE_REQUEST_TIMEOUT)));

		    PrincipalId initiatorPrincipalId = new PrincipalId();
		    initiatorPrincipalId.setId(getSymConfigDao().getConfig(CONFIG_GLO_SERVICE_CLIENT_ID));
		    initiatorPrincipalId.setType("RESELLERUSER");
		    initiatorPrincipalId.setUserId(getSymConfigDao().getConfig(CONFIG_GLO_SERVICE_USER_ID));
		    clientContext.setInitiatorPrincipalId(initiatorPrincipalId);
		    clientContext.setPassword(decryptAES(getProperty("AES128BitKey"), getProperty("AESInitializationVector"),
				    getSymConfigDao().getConfig(CONFIG_GLO_SERVICE_PASSWORD)
		    ));
		    clientContext.setPrepareOnly(false);

		    PrincipalId senderPrincipalId = new PrincipalId();
		    senderPrincipalId.setId("EMPOWER");
		    senderPrincipalId.setType("RESELLERUSER");
		    senderPrincipalId.setUserId(getSymConfigDao().getConfig(CONFIG_GLO_SERVICE_USER_ID));

		    PrincipalId topupPrincipalId = new PrincipalId();
		    topupPrincipalId.setId(formatFullMsisdn(recipient, getSymConfigDao().getConfig(CONFIG_DEFAULT_COUNTRY_CODE)));
		    topupPrincipalId.setType("SUBSCRIBERMSISDN");
//            topupPrincipalId.setUserId(recipient);

		    PrincipalAccountSpecifier senderAccountSpecifier = new PrincipalAccountSpecifier();
		    senderAccountSpecifier.setAccountId(getSymConfigDao().getConfig(CONFIG_GLO_SERVICE_CLIENT_ID));
		    senderAccountSpecifier.setAccountTypeId("RESELLER");

		    PrincipalAccountSpecifier topupAccountSpecifier = new PrincipalAccountSpecifier();
		    topupAccountSpecifier.setAccountId(formatFullMsisdn(recipient, getSymConfigDao().getConfig(CONFIG_DEFAULT_COUNTRY_CODE)));
		    topupAccountSpecifier.setAccountTypeId("AIRTIME");

		    Amount sAmount = new Amount();
		    sAmount.setCurrency(getSymConfigDao().getConfig(CONFIG_DEFAULT_CURRENCY_SYMBOL).toUpperCase());
		    sAmount.setValue(amount);

		    String requestStr = format("senderReference=%s|voucherId=%s|amount=%s|recipient=%s",
			    senderReference,voucherId,amount,recipient);

		    transactionLog.setOutgoing_request_time(new Date()).setOutgoing_request(requestStr).save();

		    logger.info("Sending request to Glo with params: " + requestStr);

		    ERSWSTopupResponse topupResponse = topupService.getResponseObject().getERSWSTopupServiceImplPort().requestTopup(
			    clientContext, senderPrincipalId, topupPrincipalId,
			    senderAccountSpecifier, topupAccountSpecifier, "TOPUP", sAmount
		    );

            int responseCode = topupResponse.getResultCode();
            String responseMessage = topupResponse.getResultDescription();


            logger.info("Purchase response: " + responseCode + ":" + responseMessage);

            if (responseCode == 0) {
                //return voucher provider reference
                return new SymResponseObject<>(SUCCESS, topupResponse.getErsReference());
            } else {
                switch (responseCode) {
                    case 1: case 2: case 3: case 4: case 45: case 47: { /* user error. do nothing. user should retry */ break; }
                    default: {
                        sendEmailAlert(getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME),INTEGRATION_ID + " Error " + responseCode,
                            format("Voucher Purchase failed with error %s: %s", responseCode, responseMessage));
                        break;
                    }
                }
                Pair<Long, String> mappedResponse = getMappedResponseMessage(INTEGRATION_ID, (long)responseCode);
                return new SymResponseObject<String>(valueOf(mappedResponse.getLeft())).setMessage(mappedResponse.getRight());
            }


	    } catch (Exception ex) {
            ex.printStackTrace();
            transactionLog.setIncoming_response_time(new Date())
                          .setResponse_code(fromEnum(GENERAL_ERROR))
                          .setIncoming_response(throwableAsString(ex))
                          .save();
            logger.info("Request topup failed: " + ex.getMessage());
            return new SymResponseObject<>(GENERAL_ERROR);
        }
    }

    //@Override
//    public SymResponseObject<String> purchaseVoucher2(Long senderReference, Long voucherId, BigDecimal amount, String recipient) {
//
//        SymResponseObject<ERSWSTopupServiceImplServiceStub> topupService = getTopupService();
//        if (!topupService.getResponseCode().equals(SUCCESS)) {
//            return new SymResponseObject<String>(topupService.getResponseCode()).setMessage(topupService.getMessage());
//        }
//
//        sym_integration_log transactionLog = new sym_integration_log();
//
//        try {
//            RequestTopup requestTopup = new RequestTopup();
//
//            ClientContext clientContext = new ClientContext();
//            clientContext.setChannel("WEBSERVICE");
//            clientContext.setClientComment("");
//            clientContext.setClientId(getSymConfigDao().getConfig(CONFIG_GLO_SERVICE_CLIENT_ID));
//            clientContext.setClientReference(String.valueOf(senderReference));
//            clientContext.setClientRequestTimeout(parseLong(getSymConfigDao().getConfig(CONFIG_GLO_SERVICE_REQUEST_TIMEOUT)));
//
//            PrincipalId initiatorPrincipalId = new PrincipalId();
//            initiatorPrincipalId.setId(getSymConfigDao().getConfig(CONFIG_GLO_SERVICE_CLIENT_ID));
//            initiatorPrincipalId.setType("RESELLERUSER");
//            initiatorPrincipalId.setUserId(getSymConfigDao().getConfig(CONFIG_GLO_SERVICE_USER_ID));
//            clientContext.setInitiatorPrincipalId(initiatorPrincipalId);
//            clientContext.setPassword(decryptAES(getProperty("AES128BitKey"), getProperty("AESInitializationVector"),
//                getSymConfigDao().getConfig(CONFIG_GLO_SERVICE_PASSWORD)
//            ));
//            clientContext.setPrepareOnly(false);
//            requestTopup.setContext(clientContext);
//
//            PrincipalId senderPrincipalId = new PrincipalId();
//            senderPrincipalId.setId("EMPOWER");
//            senderPrincipalId.setType("RESELLERUSER");
//            senderPrincipalId.setUserId(getSymConfigDao().getConfig(CONFIG_GLO_SERVICE_USER_ID));
//            requestTopup.setSenderPrincipalId(senderPrincipalId);
//
//            PrincipalId topupPrincipalId = new PrincipalId();
//            topupPrincipalId.setId(formatFullMsisdn(recipient, getSymConfigDao().getConfig(CONFIG_DEFAULT_COUNTRY_CODE)));
//            topupPrincipalId.setType("SUBSCRIBERMSISDN");
////            topupPrincipalId.setUserId(recipient);
//            requestTopup.setTopupPrincipalId(topupPrincipalId);
//
//            PrincipalAccountSpecifier senderAccountSpecifier = new PrincipalAccountSpecifier();
//            senderAccountSpecifier.setAccountId(getSymConfigDao().getConfig(CONFIG_GLO_SERVICE_CLIENT_ID));
//            senderAccountSpecifier.setAccountTypeId("RESELLER");
//            requestTopup.setSenderAccountSpecifier(senderAccountSpecifier);
//
//            PrincipalAccountSpecifier topupAccountSpecifier = new PrincipalAccountSpecifier();
//            topupAccountSpecifier.setAccountId(formatFullMsisdn(recipient, getSymConfigDao().getConfig(CONFIG_DEFAULT_COUNTRY_CODE)));
//            topupAccountSpecifier.setAccountTypeId("AIRTIME");
//            requestTopup.setTopupAccountSpecifier(topupAccountSpecifier);
//
//            requestTopup.setProductId("TOPUP");
//            Amount sAmount = new Amount();
//            sAmount.setCurrency(getSymConfigDao().getConfig(CONFIG_DEFAULT_CURRENCY_SYMBOL).toUpperCase());
//            sAmount.setValue(amount);
//            requestTopup.setAmount(sAmount);
//
//            RequestTopupE requestTopupE = new RequestTopupE();
//            requestTopupE.setRequestTopup(requestTopup);
//
//            XMLOutputFactory factory = XMLOutputFactory.newInstance();
//            StringWriter requestXMLWriter = new StringWriter();
//            requestTopupE.getRequestTopup().serialize(
//                new QName("http://external.interfaces.ers.seamless.com/", "requestTopup"),
//                factory.createXMLStreamWriter(requestXMLWriter)
//            );
//            String outgoingRequestStr = requestXMLWriter.toString();
//
//            transactionLog.setOutgoing_request_time(new Date()).setOutgoing_request(outgoingRequestStr).save();
//
//            logger.info("Performing Glo topup for amount: " + amount.doubleValue() + "\r\n" + outgoingRequestStr);
//            RequestTopupResponseE topupResponseE = topupService.getResponseObject().requestTopup(requestTopupE);
//
//            int responseCode = topupResponseE.getRequestTopupResponse().get_return().getResultCode();
//            String responseMessage = topupResponseE.getRequestTopupResponse().get_return().getResultDescription();
//
//
//            logger.info("Purchase response: " + responseCode + ":" + responseMessage);
//
//            if (responseCode == 0) {
//                //return voucher provider reference
//                return new SymResponseObject<>(SUCCESS, topupResponseE.getRequestTopupResponse().get_return().getErsReference());
//            } else {
//                switch (responseCode) {
//                    case 1: case 2: case 3: case 4: case 45: case 47: { /* user error. do nothing. user should retry */ break; }
//                    default: {
//                        sendEmailAlert(getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME),INTEGRATION_ID + " Error " + responseCode,
//                            format("Voucher Purchase failed with error %s: %s", responseCode, responseMessage));
//                        break;
//                    }
//                }
//                Pair<Long, String> mappedResponse = getMappedResponseMessage(INTEGRATION_ID, (long)responseCode);
//                return new SymResponseObject<String>(valueOf(mappedResponse.getLeft())).setMessage(mappedResponse.getRight());
//            }
//        } catch (Exception ex) {
//            ex.printStackTrace();
//            transactionLog.setIncoming_response_time(new Date())
//                          .setResponse_code(fromEnum(GENERAL_ERROR))
//                          .setIncoming_response(throwableAsString(ex))
//                          .save();
//            logger.info("Request topup failed: " + ex.getMessage());
//            return new SymResponseObject<>(GENERAL_ERROR);
//        }
//
//    }
}
