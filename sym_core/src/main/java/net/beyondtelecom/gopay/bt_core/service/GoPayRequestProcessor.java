package net.beyondtelecom.gopay.bt_core.service;

import net.beyondtelecom.bt_core_lib.enumeration.BTChannel;
import net.beyondtelecom.gopay.bt_core.contract.BTCashoutAccountList;
import net.beyondtelecom.gopay.bt_core.contract.BTResponse;
import net.beyondtelecom.gopay.bt_core.contract.BTSystemUserList;
import net.beyondtelecom.gopay.bt_core.contract.BTWalletList;

import java.math.BigDecimal;

/***************************************************************************
 * *
 * Created:     24 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public interface GoPayRequestProcessor {
    BTSystemUserList registerGoPayUser(String email, String msisdn, String username, String deviceId,
                                       String companyName, String firstName, String lastName, String pin);
    BTCashoutAccountList getCashoutAccounts(Long userId);
    BTResponse addCashoutAccount(Long userId, Long institutionId, String accountNickName,
                                 String accountName, String accountNumber, String accountBranchCode,
                                 String accountPhone, String accountEmail);
    BTResponse removeCashoutAccounts(Long userId, Long cashoutAccountId);
    BTWalletList swipeTransaction(Long userId, String deviceId, BigDecimal amount,
                                  String reference, String cardNumber, String cardPin);
    BTWalletList cashoutTransaction(Long userId, String deviceId, BigDecimal amount,
                                    String reference, Long cashoutAccountId, String pin);
    BTSystemUserList startSession(String deviceId, String username, String pin, BTChannel channel);
    BTResponse endSession(Long sessionId, String deviceId, String authToken, BTChannel channel);
}
