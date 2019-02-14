package net.symbiosis.core.service;

import net.symbiosis.core.contract.SymCashoutAccountList;
import net.symbiosis.core.contract.SymResponse;
import net.symbiosis.core.contract.SymSystemUserList;
import net.symbiosis.core.contract.SymWalletList;
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
    SymSystemUserList registerMobileUser(String email, String msisdn, String username, String deviceId,
                                         String companyName, String firstName, String lastName, String pin);

    SymCashoutAccountList getCashoutAccounts(Long userId);

    SymResponse addCashoutAccount(Long userId, Long institutionId, String accountNickName,
                                  String accountName, String accountNumber, String accountBranchCode,
                                  String accountPhone, String accountEmail);

    SymResponse removeCashoutAccounts(Long userId, Long cashoutAccountId);

    SymWalletList swipeTransaction(Long userId, String deviceId, BigDecimal amount,
                                   String reference, String cardNumber, String cardPin);

    SymWalletList cashoutTransaction(Long userId, String deviceId, BigDecimal amount,
                                     String reference, Long cashoutAccountId, String pin);

    SymSystemUserList startSession(String deviceId, String username, String pin, SymChannel channel);

    SymResponse endSession(Long sessionId, String deviceId, String authToken, SymChannel channel);
}
