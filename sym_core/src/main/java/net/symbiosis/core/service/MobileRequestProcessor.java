package net.symbiosis.core.service;

import net.symbiosis.core.contract.*;
import net.symbiosis.core_lib.enumeration.SymChannel;

import java.math.BigDecimal;

/***************************************************************************
 * *
 * Created:     24 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public interface MobileRequestProcessor extends RequestProcessor {

    SymSystemUserList startSession(String imei, String username, String pin, SymChannel channel);

    SymResponse endSession(Long sessionId, String imei, String authToken, SymChannel channel);

    SymSystemUserList registerMobileUser(String email, String msisdn, String username, String imei,
                                         String companyName, String firstName, String lastName, String pin);

    SymCashoutAccountList getCashoutAccounts(Long userId, String imei, String authToken);

    SymResponse addCashoutAccount(Long userId, String imei, String authToken, Long institutionId, String accountNickName,
                                  String accountName, String accountNumber, String accountBranchCode,
                                  String accountPhone, String accountEmail);

    SymResponse removeCashoutAccounts(Long userId, String imei, String authToken, Long cashoutAccountId);

    SymVoucherPurchaseList buyVoucher(Long userId, String imei, String authToken, Long voucherId, BigDecimal voucherValue, String recipient);

    SymWalletList swipeTransaction(Long userId, String imei, String authToken, BigDecimal amount,
                                   String reference, String cardNumber, String cardPin);

    SymWalletList cashoutTransaction(Long userId, String imei, String authToken, BigDecimal amount,
                                     String reference, Long cashoutAccountId, String pin);
}
