package net.symbiosis.core.impl;

import net.symbiosis.core.contract.*;
import net.symbiosis.core.contract.symbiosis.SymCurrency;
import net.symbiosis.core.contract.symbiosis.SymFinancialInstitution;
import net.symbiosis.core.contract.symbiosis.SymWalletTransaction;
import net.symbiosis.core.service.ConverterService;
import net.symbiosis.core.service.SymbiosisRequestProcessor;
import net.symbiosis.core_lib.enumeration.SymChannel;
import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.core_lib.structure.Pair;
import net.symbiosis.persistence.entity.complex_type.device.sym_device_phone;
import net.symbiosis.persistence.entity.complex_type.device.sym_device_pos_machine;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.log.sym_session;
import net.symbiosis.persistence.entity.complex_type.log.sym_voucher_purchase;
import net.symbiosis.persistence.entity.complex_type.log.sym_wallet_transaction;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.entity.enumeration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import static net.symbiosis.authentication.authentication.SymbiosisAuthenticator.verifyLogin;
import static net.symbiosis.core.helper.ValidationHelper.*;
import static net.symbiosis.core_lib.enumeration.SymChannel.POS_MACHINE;
import static net.symbiosis.core_lib.enumeration.SymEventType.VOUCHER_PURCHASE_QUERY;
import static net.symbiosis.core_lib.enumeration.SymEventType.WALLET_HISTORY;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.*;
import static net.symbiosis.persistence.dao.EnumEntityRepoManager.findEnabled;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;

/***************************************************************************
 * *
 * Created:     20 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Service
public class SymbiosisRequestProcessorImpl implements SymbiosisRequestProcessor {

    private static final Logger logger = Logger.getLogger(SymbiosisRequestProcessorImpl.class.getSimpleName());
    private final ConverterService converterService;

    @Autowired
    public SymbiosisRequestProcessorImpl(ConverterService converterService) {
        this.converterService = converterService;
    }

    public SymEnumEntity getResponseCode(Long responseCodeId) {

        sym_response_code response_code = getEntityManagerRepo().findById(sym_response_code.class, responseCodeId);

        if (response_code == null) {
            return new SymEnumEntity(DATA_NOT_FOUND);
        }

        return new SymEnumEntity(SUCCESS, converterService.toDTO(response_code));
    }

    @Override
    public SymList getResponseCodes() {
        logger.info("Getting system response code list");
        List<sym_response_code> dbResponseCodes = findEnabled(sym_response_code.class);
        logger.info(format("Got %s sym_response_codes from DB", dbResponseCodes.size()));
        ArrayList<SymResponse> responses = new ArrayList<>();
        dbResponseCodes.forEach((r) -> responses.add(new SymResponse(r)));
        return new SymList(SUCCESS, responses);
    }

    @Override
    public SymEnumEntity getLanguage(Long languageId) {
        return new SymEnumEntity(SUCCESS, converterService.toDTO(getEntityManagerRepo().findById(sym_language.class, languageId)));
    }

    @Override
    public SymEnumEntity getChannel(Long channelId) {
        return new SymEnumEntity(SUCCESS, converterService.toDTO(getEntityManagerRepo().findById(sym_channel.class, channelId)));
    }

    @Override
    public SymEnumEntity getCountry(Long countryId) {
        return new SymEnumEntity(SUCCESS, converterService.toDTO(getEntityManagerRepo().findById(sym_country.class, countryId)));
    }

    @Override
    public SymFinancialInstitutionList getFinancialInstitution(Long institutionId) {
        logger.info(format("Getting financial institution with Id %s", institutionId));
        sym_financial_institution institution = getEntityManagerRepo().findById(sym_financial_institution.class, institutionId);
        if (institution == null) {
            return new SymFinancialInstitutionList(DATA_NOT_FOUND);
        }
        logger.info(format("Returning financial institution with Id %s: %s", institutionId, institution.toString()));
        return new SymFinancialInstitutionList(SUCCESS, converterService.toDTO(institution));
    }

    @Override
    public SymFinancialInstitutionList getFinancialInstitutions() {
        logger.info("Getting financial institution list");
        ArrayList<SymFinancialInstitution> financialInstitutions = new ArrayList<>();
        findEnabled(sym_financial_institution.class).forEach(f -> financialInstitutions.add(converterService.toDTO(f)));
        logger.info(format("Returning %s financial institutions", financialInstitutions.size()));
        return new SymFinancialInstitutionList(SUCCESS, financialInstitutions);
    }

    @Override
    public SymCurrencyList getCurrency(Long currencyId) {
        logger.info(format("Getting currency with Id %s", currencyId));
        sym_currency currency = getEntityManagerRepo().findById(sym_currency.class, currencyId);
        if (currency == null) {
            return new SymCurrencyList(DATA_NOT_FOUND);
        }
        logger.info(format("Returning currency with Id %s: %s", currencyId, currency.toString()));
        return new SymCurrencyList(SUCCESS, converterService.toDTO(currency));
    }

    @Override
    public SymCurrencyList getCurrencies() {
        logger.info("Getting currency list");
        ArrayList<SymCurrency> currencies = new ArrayList<>();
        findEnabled(sym_currency.class).forEach(c -> currencies.add(converterService.toDTO(c)));
        logger.info(format("Returning %s currencies", currencies.size()));
        return new SymCurrencyList(SUCCESS, currencies);
    }

    @Override
    public SymWalletList getWallet(Long authUserId, String deviceId, SymChannel channel, String authToken, Long walletId) {
        logger.info(format("Getting wallet with Id %s for auth user %s from channel %s", walletId, authUserId, channel.name()));

        String incomingRequest = format("walletId=%s|authUserId=%s|deviceId=%s|channel=%s", walletId, authUserId, deviceId, channel);

        sym_request_response_log requestResponseLog = new sym_request_response_log(
                fromEnum(channel), fromEnum(WALLET_HISTORY), incomingRequest
        ).save();

        SymResponseObject<sym_auth_user> authUserResponse = validateAuthUser(authUserId);
        if (authUserResponse.getResponseCode() != SUCCESS) {
            logger.warning("Failed to validate authUserId! " + authUserResponse.getMessage());
            logResponse(null, requestResponseLog, authUserResponse.getResponseCode());
            return new SymWalletList(authUserResponse.getResponseCode());
        }

        SymResponseObject<sym_session> sessionResponse = verifyLogin(authUserId, deviceId, authToken);
        if (sessionResponse.getResponseCode() != SUCCESS) {
            logger.warning("Failed to validate session! " + sessionResponse.getMessage());
            logResponse(null, requestResponseLog, sessionResponse.getResponseCode());
            return new SymWalletList(sessionResponse.getResponseCode());
        }

        SymResponseObject<sym_wallet> walletResponse = validateWallet(walletId);
        if (!walletResponse.getResponseCode().equals(SUCCESS)) {
            logger.warning("Failed to validate walletId! " + walletResponse.getMessage());
            logResponse(authUserResponse.getResponseObject(), requestResponseLog, walletResponse.getResponseCode());
            return new SymWalletList(walletResponse.getResponseCode());
        }

        SymResponseObject<sym_device_phone> deviceResponse = validatePhoneDevice(deviceId, false);
        if (!deviceResponse.getResponseCode().equals(SUCCESS)) {
            logger.warning("Failed to validate deviceId! " + deviceResponse.getMessage());
            logResponse(authUserResponse.getResponseObject(), requestResponseLog, deviceResponse.getResponseCode());
            return new SymWalletList(deviceResponse.getResponseCode());
        }
        logger.info(format("Returning wallet with Id %s: %s", walletId, walletResponse.getResponseObject().toString()));
        return new SymWalletList(SUCCESS, converterService.toDTO(walletResponse.getResponseObject()));
    }

    @Override
    public SymWalletTransactionList getWalletTransactions(Long authUserId, String deviceId, SymChannel channel, String authToken, Long walletId) {
        logger.info(format("Getting wallet %s transactions for auth user %s from channel %s", walletId, authUserId, channel.name()));

        String incomingRequest = format("walletId=%s|authUserId=%s|deviceId=%s|channel=%s", walletId, authUserId, deviceId, channel);

        sym_request_response_log requestResponseLog = new sym_request_response_log(
            fromEnum(channel), fromEnum(WALLET_HISTORY), incomingRequest
        ).save();

//        SymResponseObject<sym_auth_user> authUserResponse = validateAuthUser(authUserId);
//        if (authUserResponse.getResponseCode() != SUCCESS) {
//            logger.warning("Failed to validate authUserId! " + authUserResponse.getMessage());
//            logResponse(null, requestResponseLog, authUserResponse.getResponseCode());
//            return new SymWalletTransactionList(authUserResponse.getResponseCode());
//        }

        SymResponseObject<sym_session> sessionResponse = verifyLogin(authUserId, deviceId, authToken);
        if (sessionResponse.getResponseCode() != SUCCESS) {
            logger.warning("Failed to validate session! " + sessionResponse.getMessage());
            logResponse(null, requestResponseLog, sessionResponse.getResponseCode());
            return new SymWalletTransactionList(sessionResponse.getResponseCode());
        }

        sym_auth_user authUser = sessionResponse.getResponseObject().getAuth_user();

        SymResponseObject<sym_wallet> walletResponse = validateWallet(walletId);
        if (!walletResponse.getResponseCode().equals(SUCCESS)) {
            logger.warning("Failed to validate walletId! " + walletResponse.getMessage());
            logResponse(authUser, requestResponseLog, walletResponse.getResponseCode());
            return new SymWalletTransactionList(walletResponse.getResponseCode());
        }

        SymResponseObject<sym_device_phone> deviceResponse = validatePhoneDevice(deviceId, false);
        if (!deviceResponse.getResponseCode().equals(SUCCESS)) {
            logger.warning("Failed to validate deviceId! " + deviceResponse.getMessage());
            logResponse(authUser, requestResponseLog, deviceResponse.getResponseCode());
            return new SymWalletTransactionList(deviceResponse.getResponseCode());
        }

        if (!walletResponse.getResponseObject().getWallet_admin_user().getId().equals(authUser.getId())) {
            logger.warning(format("Incorrect wallet admin user %s! Auth user has no privileges to pull transactions", authUserId));
            logResponse(authUser, requestResponseLog, sessionResponse.getResponseCode());
            return new SymWalletTransactionList(sessionResponse.getResponseCode());
        }

        ArrayList<SymWalletTransaction> walletTransactions = new ArrayList<>();
        getEntityManagerRepo().findWhere(sym_wallet_transaction.class,
            new Pair<>("wallet_id", authUser.getUser().getWallet().getId()),20
        ).forEach(v -> walletTransactions.add(converterService.toDTO(v)));

        requestResponseLog.setResponse_code(fromEnum(SUCCESS))
            .setAuth_user(authUser)
            .setOutgoing_response(walletTransactions.toString())
            .setOutgoing_response_time(new Date())
            .save();

        logger.info(format("Returning %s wallet transactions", walletTransactions.size()));
        return new SymWalletTransactionList(SUCCESS, walletTransactions, walletResponse.getResponseObject().getCurrent_balance());
    }

    @Override
    public SymVoucherPurchaseList getVoucherPurchase(Long authUserId, String deviceId, SymChannel channel, String authToken, Long voucherPurchaseId) {

	    logger.info(format("Getting voucher purchase %s for auth user %s from channel %s", voucherPurchaseId, authUserId, channel.name()));

	    String incomingRequest = format("authUserId=%s|deviceId=%s|channel=%s|voucherPurchaseId=%s", authUserId, deviceId, channel, voucherPurchaseId);

	    sym_request_response_log requestResponseLog = new sym_request_response_log(fromEnum(channel), fromEnum(VOUCHER_PURCHASE_QUERY), incomingRequest);

	    SymResponseObject<sym_session> authResponse = verifyLogin(authUserId, deviceId, authToken);
	    if (!authResponse.getResponseCode().equals(SUCCESS)) {
            logger.warning("Failed to validate session! " + authResponse.getMessage());
            logResponse(null, requestResponseLog, authResponse.getResponseCode());
            return new SymVoucherPurchaseList(authResponse.getResponseCode());
	    }

	    if (channel == POS_MACHINE) {
		    List<sym_device_pos_machine> posMachines = getEntityManagerRepo().findWhere(
			    sym_device_pos_machine.class, asList(
				    new Pair<>("is_active", 1),
				    new Pair<>("auth_user", authUserId)
			    )
		    );

		    if (posMachines.size() != 1) {
			    logger.warning(format("Failed to get voucher purchase %s. POS Machine %s not found or not active for auth user %s",
				    deviceId, authUserId, voucherPurchaseId));
			    logResponse(authResponse.getResponseObject().getAuth_user(), requestResponseLog, AUTH_NON_EXISTENT);
			    return new SymVoucherPurchaseList(AUTH_NON_EXISTENT);
		    }
	    }

        sym_auth_user authUser = authResponse.getResponseObject().getAuth_user();

        sym_voucher_purchase voucherPurchaseResponse = getEntityManagerRepo().findById(
            sym_voucher_purchase.class, voucherPurchaseId
        );

        if (voucherPurchaseResponse == null) {
	        logger.warning(format("Failed to get voucher purchase %s. Voucher purchase not found", voucherPurchaseId));
	        logResponse(authUser, requestResponseLog, DATA_NOT_FOUND);
	        return new SymVoucherPurchaseList(DATA_NOT_FOUND);
        }

        if (!voucherPurchaseResponse.getWallet().getId().equals(authUser.getUser().getWallet().getId())) {
	        logger.warning(format("Cannot return voucher purchase %s. Wallet %s does not match transaction wallet %s",
		        voucherPurchaseId, authUser.getUser().getWallet().getId(), voucherPurchaseResponse.getWallet().getId()));
	        logResponse(authUser, requestResponseLog, INPUT_INVALID_WALLET);
	        return new SymVoucherPurchaseList(INPUT_INVALID_WALLET);
        }

        if (!voucherPurchaseResponse.getResponse_code().equals(fromEnum(SUCCESS))) {
            logger.warning(format("Failed to get voucher purchase %s. " + voucherPurchaseResponse.getResponse_code().getResponse_message(), voucherPurchaseId));
            logResponse(authUser, requestResponseLog, voucherPurchaseResponse.getResponse_code());
            return new SymVoucherPurchaseList(voucherPurchaseResponse.getResponse_code().asSymResponseCode());
        }

        return new SymVoucherPurchaseList(SUCCESS, converterService.toDTO(voucherPurchaseResponse));

    }
}
