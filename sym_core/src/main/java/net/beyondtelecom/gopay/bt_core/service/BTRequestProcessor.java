package net.beyondtelecom.gopay.bt_core.service;

import net.beyondtelecom.bt_core_lib.enumeration.BTChannel;
import net.beyondtelecom.gopay.bt_core.contract.*;

/***************************************************************************
 * *
 * Created:     20 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public interface BTRequestProcessor {
	BTEnumEntity getResponseCode(Long responseCodeId);
	BTList getResponseCodes();
	BTEnumEntity getLanguage(Long languageId);
	BTEnumEntity getChannel(Long channelId);
	BTEnumEntity getCountry(Long countryId);
	BTEnumEntity getGroup(Long groupId);
	BTEnumEntity getRole(Long roleId);
	BTEnumEntity getEventType(Long eventTypeId);
    BTMap getSystemConfigs();
    BTFinancialInstitutionList getFinancialInstitution(Long institutionId);
    BTFinancialInstitutionList getFinancialInstitutions();
    BTCurrencyList getCurrency(Long currencyId);
    BTCurrencyList getCurrencies();
    BTWalletList getWallet(Long walletId);
    BTWalletList getWallets();
	BTSystemUserList registerWebUser(String email, String msisdn, String msisdn2, String username, String deviceId,
                                     String firstName, String lastName, String dateOfBirth);
//	BTSystemUserList confirmRegistration(Long userId, String authToken, String deviceId, String username,
//                                         String password, BTChannel channel);
    BTSystemUserList searchUser(String email, String msisdn, String username, String firstName, String lastName);
    BTSystemUserList startSession(String deviceId, String username, String password, BTChannel channel);
	BTResponse endSession(Long sessionId, String deviceId, String authToken, BTChannel channel);
}
