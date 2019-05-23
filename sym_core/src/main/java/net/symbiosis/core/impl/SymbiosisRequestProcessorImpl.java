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
import net.symbiosis.persistence.dao.EnumEntityRepoManager;
import net.symbiosis.persistence.entity.complex_type.device.sym_device_phone;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.log.sym_session;
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
import static net.symbiosis.authentication.authentication.SymbiosisAuthenticator.verifyLogin;
import static net.symbiosis.core.helper.ValidationHelper.*;
import static net.symbiosis.core_lib.enumeration.SymEventType.WALLET_HISTORY;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.DATA_NOT_FOUND;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
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
        List<sym_response_code> dbResponseCodes = EnumEntityRepoManager.findEnabled(sym_response_code.class);
        logger.info(format("Got %s sym_response_codes from DB", dbResponseCodes.size()));
        ArrayList<SymResponse> responses = new ArrayList<>();
        dbResponseCodes.forEach((r) -> responses.add(new SymResponse(r)));
        return new SymList(SUCCESS, responses);
    }

    @Override
    public SymEnumEntity getLanguage(Long languageId) {
        return new SymEnumEntity(SUCCESS,
                converterService.toDTO(getEntityManagerRepo().findById(sym_language.class, languageId)));
    }

    @Override
    public SymEnumEntity getChannel(Long channelId) {
        return new SymEnumEntity(SUCCESS,
                converterService.toDTO(getEntityManagerRepo().findById(sym_channel.class, channelId)));
    }

    @Override
    public SymEnumEntity getCountry(Long countryId) {
        return new SymEnumEntity(SUCCESS,
                converterService.toDTO(getEntityManagerRepo().findById(sym_country.class, countryId)));
    }

//    @Override
//    public SymEnumEntity getGroup(Long groupId) {
//        return new SymEnumEntity(SUCCESS,
//                converterService.toDTO(getEntityManagerRepo().findById(sym_auth_group.class, groupId)));
//    }
//
//    @Override
//    public SymEnumEntity getRole(Long roleId) {
//        return new SymEnumEntity(SUCCESS,
//                converterService.toDTO(getEntityManagerRepo().findById(sym_role.class, roleId)));
//    }
//
//    @Override
//    public SymEnumEntity getEventType(Long eventTypeId) {
//        return new SymEnumEntity(SUCCESS,
//                converterService.toDTO(getEntityManagerRepo().findById(sym_event_type.class, eventTypeId)));
//    }
//
//    @Override
//    public SymMap getSystemConfigs() {
//        logger.info("Getting system configuration list");
//        HashMap<String, String> sysConfigs = new HashMap<>();
//
//        sysConfigs.put("CurrencySymbol", getSymConfigDao().getConfig(CONFIG_DEFAULT_CURRENCY_SYMBOL));
//        sysConfigs.put("CountryCodePrefix", getSymConfigDao().getConfig(CONFIG_DEFAULT_COUNTRY_CODE));
//        sysConfigs.put("SupportEmail", getSymConfigDao().getConfig(CONFIG_SUPPORT_EMAIL));
//        sysConfigs.put("SupportPhone", getSymConfigDao().getConfig(CONFIG_SUPPORT_PHONE));
//        sysConfigs.put("MinPasswordLength", MIN_PASSWORD_LENGTH.toString());
//        sysConfigs.put("MaxPasswordLength", MAX_PASSWORD_LENGTH.toString());
//        sysConfigs.put("MinNameLength", MIN_NAME_LEN.toString());
//        sysConfigs.put("MaxNameLength", MAX_NAME_LEN.toString());
//        sysConfigs.put("MinUsernameLength", MIN_UNAME_LEN.toString());
//        sysConfigs.put("MaxUsernameLength", MAX_UNAME_LEN.toString());
//        sysConfigs.put("PinLength", PIN_LEN.toString());
//        sysConfigs.put("NameRegex", javaToJSRegex(NAME_REGEX));
//        sysConfigs.put("UsernameRegex", javaToJSRegex(USERNAME_REGEX));
//        sysConfigs.put("EmailRegex", javaToJSRegex(EMAIL_REGEX));
//        sysConfigs.put("PasswordRegex", javaToJSRegex(PASSWORD_REGEX));
//        sysConfigs.put("TenDigitMsisdnRegex", javaToJSRegex(TEN_DIGIT_MSISDN_REGEX));
//        sysConfigs.put("MsisdnRegex", javaToJSRegex(MSISDN_REGEX));
//
//        logger.info(format("Got %s system configurations", sysConfigs.size()));
//
//        return new SymMap(SUCCESS, sysConfigs);
//    }

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
        EnumEntityRepoManager.findEnabled(sym_financial_institution.class).forEach(f -> financialInstitutions.add(converterService.toDTO(f)));
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
        EnumEntityRepoManager.findEnabled(sym_currency.class).forEach(c -> currencies.add(converterService.toDTO(c)));
        logger.info(format("Returning %s currencies", currencies.size()));
        return new SymCurrencyList(SUCCESS, currencies);
    }

    @Override
    public SymWalletList getWallet(Long authUserId, String deviceId, SymChannel channel, String authToken, Long walletId) {
        logger.info(format("Getting wallet with Id %s for auth user %s from channel %s", walletId, authUserId, channel));

        String incomingRequest = format("walletId=%s|authUserId=%s|deviceId=%s|channel=%s", walletId, authUserId, deviceId, channel);

        sym_request_response_log requestResponseLog = new sym_request_response_log(
                fromEnum(channel), fromEnum(WALLET_HISTORY), incomingRequest
        ).save();

        SymResponseObject<sym_wallet> walletResponse = validateWallet(walletId);
        if (!walletResponse.getResponseCode().equals(SUCCESS)) {
            logger.warning("Failed to validate walletId! " + walletResponse.getMessage());
            logResponse(null, requestResponseLog, walletResponse.getResponseCode());
            return new SymWalletList(walletResponse.getResponseCode());
        }

        SymResponseObject<sym_auth_user> authUserResponse = validateAuthUser(authUserId);
        if (authUserResponse.getResponseCode() != SUCCESS) {
            logger.warning("Failed to validate authUserId! " + authUserResponse.getMessage());
            logResponse(null, requestResponseLog, authUserResponse.getResponseCode());
            return new SymWalletList(authUserResponse.getResponseCode());
        }

        SymResponseObject<sym_device_phone> deviceResponse = validatePhoneDevice(deviceId, false);
        if (!deviceResponse.getResponseCode().equals(SUCCESS)) {
            logger.warning("Failed to validate deviceId! " + deviceResponse.getMessage());
            logResponse(null, requestResponseLog, deviceResponse.getResponseCode());
            return new SymWalletList(deviceResponse.getResponseCode());
        }

        SymResponseObject<sym_session> sessionResponse = verifyLogin(authUserId, deviceId, authToken);
        if (sessionResponse.getResponseCode() != SUCCESS) {
            logger.warning("Failed to validate session! " + sessionResponse.getMessage());
            logResponse(null, requestResponseLog, sessionResponse.getResponseCode());
            return new SymWalletList(sessionResponse.getResponseCode());
        }
        logger.info(format("Returning wallet with Id %s: %s", walletId, walletResponse.getResponseObject().toString()));
        return new SymWalletList(SUCCESS, converterService.toDTO(walletResponse.getResponseObject()));
    }

//    @Override
//    public SymWalletList getWallets() {
//        logger.info("Getting wallet list");
//        ArrayList<SymWallet> wallets = new ArrayList<>();
//        getEntityManagerRepo().findAll(sym_wallet.class).forEach(v -> wallets.add(converterService.toDTO(v)));
//        logger.info(format("Returning %s wallets", wallets.size()));
//        return new SymWalletList(SUCCESS, wallets);
//    }


    @Override
    public SymWalletTransactionList getWalletTransactions(Long authUserId, String deviceId, SymChannel channel, String authToken, Long walletId) {
        logger.info(format("Getting wallet %s transactions for auth user %s from channel %s", walletId, authUserId, channel));

        String incomingRequest = format("walletId=%s|authUserId=%s|deviceId=%s|channel=%s", walletId, authUserId, deviceId, channel);

        sym_request_response_log requestResponseLog = new sym_request_response_log(
            fromEnum(channel), fromEnum(WALLET_HISTORY), incomingRequest
        ).save();

        SymResponseObject<sym_wallet> walletResponse = validateWallet(walletId);
        if (!walletResponse.getResponseCode().equals(SUCCESS)) {
            logger.warning("Failed to validate walletId! " + walletResponse.getMessage());
            logResponse(null, requestResponseLog, walletResponse.getResponseCode());
            return new SymWalletTransactionList(walletResponse.getResponseCode());
        }

        SymResponseObject<sym_auth_user> authUserResponse = validateAuthUser(authUserId);
        if (authUserResponse.getResponseCode() != SUCCESS) {
            logger.warning("Failed to validate authUserId! " + authUserResponse.getMessage());
            logResponse(null, requestResponseLog, authUserResponse.getResponseCode());
            return new SymWalletTransactionList(authUserResponse.getResponseCode());
        }

        SymResponseObject<sym_device_phone> deviceResponse = validatePhoneDevice(deviceId, false);
        if (!deviceResponse.getResponseCode().equals(SUCCESS)) {
            logger.warning("Failed to validate deviceId! " + deviceResponse.getMessage());
            logResponse(null, requestResponseLog, deviceResponse.getResponseCode());
            return new SymWalletTransactionList(deviceResponse.getResponseCode());
        }

        SymResponseObject<sym_session> sessionResponse = verifyLogin(authUserId, deviceId, authToken);
        if (sessionResponse.getResponseCode() != SUCCESS) {
            logger.warning("Failed to validate session! " + sessionResponse.getMessage());
            logResponse(null, requestResponseLog, sessionResponse.getResponseCode());
            return new SymWalletTransactionList(sessionResponse.getResponseCode());
        }

        if (!walletResponse.getResponseObject().getWallet_admin_user().getId().equals(authUserResponse.getResponseObject().getId())) {
            logger.warning(format("Incorrect wallet admin user %s! Auth user has no privileges to pull transactions", authUserId));
            logResponse(null, requestResponseLog, sessionResponse.getResponseCode());
            return new SymWalletTransactionList(sessionResponse.getResponseCode());
        }

        ArrayList<SymWalletTransaction> walletTransactions = new ArrayList<>();
        getEntityManagerRepo().findWhere(sym_wallet_transaction.class,
            new Pair<>("wallet_id", authUserResponse.getResponseObject().getUser().getWallet().getId()),20
        ).forEach(v -> walletTransactions.add(converterService.toDTO(v)));

        requestResponseLog.setResponse_code(fromEnum(SUCCESS))
            .setOutgoing_response(walletTransactions.toString())
            .setOutgoing_response_time(new Date())
            .save();

        logger.info(format("Returning %s wallet transactions", walletTransactions.size()));
        return new SymWalletTransactionList(SUCCESS, walletTransactions);
    }
}
