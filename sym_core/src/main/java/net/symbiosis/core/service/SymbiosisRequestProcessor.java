package net.symbiosis.core.service;

import net.symbiosis.core.contract.*;
import net.symbiosis.core_lib.enumeration.SymChannel;

/***************************************************************************
 * *
 * Created:     20 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public interface SymbiosisRequestProcessor extends RequestProcessor {
    SymEnumEntity getResponseCode(Long responseCodeId);

    SymList getResponseCodes();

    SymEnumEntity getLanguage(Long languageId);

    SymEnumEntity getChannel(Long channelId);

    SymEnumEntity getCountry(Long countryId);

//    SymEnumEntity getEventType(Long eventTypeId);

//    SymMap getSystemConfigs();

    SymFinancialInstitutionList getFinancialInstitution(Long institutionId);

    SymFinancialInstitutionList getFinancialInstitutions();

    SymCurrencyList getCurrency(Long currencyId);

    SymCurrencyList getCurrencies();

    SymWalletList getWallet(Long authUserId, String deviceId, SymChannel channel, String authToken, Long walletId);

    SymWalletTransactionList getWalletTransactions(Long authUserId, String deviceId, SymChannel channel, String authToken, Long walletId);

	SymVoucherPurchaseList getVoucherPurchase(Long authUserId, String deviceId, SymChannel channel, String authToken, Long walletId);
}
