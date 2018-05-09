package net.symbiosis.core.service;

import net.symbiosis.core.contract.*;

/***************************************************************************
 * *
 * Created:     20 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public interface SymbiosisRequestProcessor {
    SymEnumEntity getResponseCode(Long responseCodeId);

    SymList getResponseCodes();

    SymEnumEntity getLanguage(Long languageId);

    SymEnumEntity getChannel(Long channelId);

    SymEnumEntity getCountry(Long countryId);

    SymEnumEntity getGroup(Long groupId);

    SymEnumEntity getRole(Long roleId);

    SymEnumEntity getEventType(Long eventTypeId);

    SymMap getSystemConfigs();

    SymFinancialInstitutionList getFinancialInstitution(Long institutionId);

    SymFinancialInstitutionList getFinancialInstitutions();

    SymCurrencyList getCurrency(Long currencyId);

    SymCurrencyList getCurrencies();

    SymWalletList getWallet(Long walletId);

    SymWalletList getWallets();
}
