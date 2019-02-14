package net.symbiosis.core.implementation;

import net.symbiosis.core.contract.*;
import net.symbiosis.core.contract.symbiosis.SymCurrency;
import net.symbiosis.core.contract.symbiosis.SymFinancialInstitution;
import net.symbiosis.core.contract.symbiosis.SymWallet;
import net.symbiosis.core.service.ConverterService;
import net.symbiosis.core.service.SymbiosisRequestProcessor;
import net.symbiosis.persistence.dao.EnumEntityRepoManager;
import net.symbiosis.persistence.entity.complex_type.wallet.sym_wallet;
import net.symbiosis.persistence.entity.enumeration.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import static java.lang.String.format;
import static net.symbiosis.common.configuration.Configuration.*;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.DATA_NOT_FOUND;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.SUCCESS;
import static net.symbiosis.core_lib.utilities.CommonUtilities.javaToJSRegex;
import static net.symbiosis.core_lib.utilities.SymValidator.*;
import static net.symbiosis.persistence.helper.DaoManager.getEntityManagerRepo;

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

    @Override
    public SymEnumEntity getGroup(Long groupId) {
        return new SymEnumEntity(SUCCESS,
                converterService.toDTO(getEntityManagerRepo().findById(sym_auth_group.class, groupId)));
    }

    @Override
    public SymEnumEntity getRole(Long roleId) {
        return new SymEnumEntity(SUCCESS,
                converterService.toDTO(getEntityManagerRepo().findById(sym_role.class, roleId)));
    }

    @Override
    public SymEnumEntity getEventType(Long eventTypeId) {
        return new SymEnumEntity(SUCCESS,
                converterService.toDTO(getEntityManagerRepo().findById(sym_event_type.class, eventTypeId)));
    }

    @Override
    public SymMap getSystemConfigs() {
        logger.info("Getting system configuration list");
        HashMap<String, String> sysConfigs = new HashMap<>();

        sysConfigs.put("CurrencySymbol", getCurrencySymbol());
        sysConfigs.put("CountryCodePrefix", getCountryCodePrefix());
        sysConfigs.put("SupportEmail", getProperty("SupportEmail"));
        sysConfigs.put("SupportPhone", getProperty("SupportPhone"));
        sysConfigs.put("MinPasswordLength", MIN_PASSWORD_LENGTH.toString());
        sysConfigs.put("MaxPasswordLength", MAX_PASSWORD_LENGTH.toString());
        sysConfigs.put("MinNameLength", MIN_NAME_LEN.toString());
        sysConfigs.put("MaxNameLength", MAX_NAME_LEN.toString());
        sysConfigs.put("MinUsernameLength", MIN_UNAME_LEN.toString());
        sysConfigs.put("MaxUsernameLength", MAX_UNAME_LEN.toString());
        sysConfigs.put("PinLength", PIN_LEN.toString());
        sysConfigs.put("NameRegex", javaToJSRegex(NAME_REGEX));
        sysConfigs.put("UsernameRegex", javaToJSRegex(USERNAME_REGEX));
        sysConfigs.put("EmailRegex", javaToJSRegex(EMAIL_REGEX));
        sysConfigs.put("PasswordRegex", javaToJSRegex(PASSWORD_REGEX));
        sysConfigs.put("TenDigitMsisdnRegex", javaToJSRegex(TEN_DIGIT_MSISDN_REGEX));
        sysConfigs.put("MsisdnRegex", javaToJSRegex(MSISDN_REGEX));

        logger.info(format("Got %s system configurations", sysConfigs.size()));

        return new SymMap(SUCCESS, sysConfigs);
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
    public SymWalletList getWallet(Long walletId) {
        logger.info(format("Getting wallet with Id %s", walletId));
        sym_wallet wallet = getEntityManagerRepo().findById(sym_wallet.class, walletId);
        if (wallet == null) {
            return new SymWalletList(DATA_NOT_FOUND);
        }
        logger.info(format("Returning wallet with Id %s: %s", walletId, wallet.toString()));
        return new SymWalletList(SUCCESS, converterService.toDTO(wallet));
    }

    @Override
    public SymWalletList getWallets() {
        logger.info("Getting wallet list");
        ArrayList<SymWallet> wallets = new ArrayList<>();
        getEntityManagerRepo().findAll(sym_wallet.class).forEach(v -> wallets.add(converterService.toDTO(v)));
        logger.info(format("Returning %s wallets", wallets.size()));
        return new SymWalletList(SUCCESS, wallets);
    }
}
