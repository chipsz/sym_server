package net.symbiosis.core.integration.voucher.glo;

import net.symbiosis.core.integration.voucher.VoucherPurchaseIntegration;
import net.symbiosis.core.integration.voucher.glo.seamless.*;
import net.symbiosis.core.service.IntegrationManagerService;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.log.sym_integration_log;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher;
import net.symbiosis.persistence.entity.complex_type.voucher.sym_voucher_provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.Long.parseLong;
import static java.lang.String.format;
import static net.symbiosis.common.configuration.Configuration.getProperty;
import static net.symbiosis.common.configuration.NetworkUtilities.sendEmailAlert;
import static net.symbiosis.common.utilities.SymValidator.isNumeric;
import static net.symbiosis.core.helper.ValidationHelper.validateVoucher;
import static net.symbiosis.core_lib.enumeration.DBConfigVars.*;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.*;
import static net.symbiosis.core_lib.security.Security.decryptAES;
import static net.symbiosis.core_lib.utilities.CommonUtilities.*;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.DaoManager.getSymConfigDao;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;
import static net.symbiosis.persistence.helper.SymEnumHelper.getMappedResponseMessage;

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

    	SymResponseObject<sym_voucher> voucherResponse = validateVoucher(voucherId);
    	if (!voucherResponse.getResponseCode().equals(SUCCESS) ||
            !voucherResponse.getResponseObject().getVoucher_provider().getId().equals(voucherProvider.getId())) {
    		logger.severe(format("Invalid voucher id %s requested from %s integration. Voucher is for voucher provider %s",
			    voucherProvider.getName(), voucherId, voucherResponse.getResponseObject().getVoucher_provider().getName()));
    		return new SymResponseObject<>(INPUT_INVALID_REQUEST);
	    }

	    String accountTypeId = null, productId = null;
    	Parameter[] parameters = null;
    	if (voucherResponse.getResponseObject().getVoucher_type().getName().equals("AIRTIME")) {
    		accountTypeId = "AIRTIME";
		    productId = "TOPUP";
	    } else if (voucherResponse.getResponseObject().getVoucher_type().getName().equals("DATABUNDLE")) {
    		accountTypeId = "DATA_BUNDLE";
		    parameters = new Parameter[] { new Parameter("TRANSACTION_TYPE", "PRODUCT_RECHARGE") };
		    productId = voucherResponse.getResponseObject().getProduct_id();
		    if (productId == null) {
			    return new SymResponseObject<>(CONFIGURATION_INVALID);
		    }
	    }

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
		    clientContext.setTransactionProperties(parameters);


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
		    senderPrincipalId.setId(getSymConfigDao().getConfig(CONFIG_GLO_SERVICE_CLIENT_ID));
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
		    topupAccountSpecifier.setAccountTypeId(accountTypeId);

		    Amount sAmount = new Amount();
		    sAmount.setCurrency(getSymConfigDao().getConfig(CONFIG_DEFAULT_CURRENCY_SYMBOL).toUpperCase());
		    sAmount.setValue(amount);

		    String requestStr = format("senderReference=%s|voucherId=%s|amount=%s|recipient=%s",
			    senderReference,voucherId,amount,recipient);

		    transactionLog.setOutgoing_request_time(new Date()).setOutgoing_request(requestStr).save();

		    logger.info("Sending request to Glo with params: " + requestStr);

		    ERSWSTopupResponse topupResponse = topupService.getResponseObject().getERSWSTopupServiceImplPort().requestTopup(
			    clientContext, senderPrincipalId, topupPrincipalId,
			    senderAccountSpecifier, topupAccountSpecifier, productId, sAmount
		    );

            int responseCode = topupResponse.getResultCode();
            String responseMessage = topupResponse.getResultDescription();

		    logger.info("Purchase request: " + topupResponse.getRequestSoapXML());
            logger.info("Purchase response: " + responseCode + " : " + responseMessage + "\r\n" + topupResponse.getResponseSoapXML());

		    if (topupResponse.getRequestSoapXML() != null) {
		    	transactionLog.setOutgoing_request(topupResponse.getRequestSoapXML());
		    }
		    transactionLog.setIncoming_response(topupResponse.getResponseSoapXML()).save();

            if (responseCode == 0) {
                //return voucher provider reference
	            Pattern pattern = Pattern.compile("Your balance is now (.*?) GHS");
	            Matcher matcher = pattern.matcher(topupResponse.getResultDescription());
	            if (matcher.find() && isNumeric(matcher.group(1))) {
	            	voucherProvider.setCurrent_balance(new BigDecimal(matcher.group(1))).save();
	            	logger.info(format("Set Voucher Provider Glo balance to %s", voucherProvider.getCurrent_balance()));
		            if (voucherProvider.getCurrent_balance().compareTo(
		            		new BigDecimal(getSymConfigDao().getConfig(CONFIG_GLO_SERVICE_LOW_BALANCE_THRESHOLD))) < 0) {
			            logger.warning("Remaining Glo balance is now below threshold!");
			            sendEmailAlert(getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME), "Voucher Provider Glo Low Balance",
				            format("Balance for Voucher Provider Glo has now reached %s", formatBigDecimalToMoney(voucherProvider.getCurrent_balance()))
			            );
		            }
	            }


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
}
