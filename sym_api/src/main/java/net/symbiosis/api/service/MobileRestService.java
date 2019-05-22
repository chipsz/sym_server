package net.symbiosis.api.service;

import javax.ws.rs.core.Response;
import java.math.BigDecimal;

/***************************************************************************
 * *
 * Created:     23 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public interface MobileRestService {

    Response registerUser(String email, String msisdn, String username, String deviceId,
                          String companyName, String firstName, String lastName, String dateOfBirth);

//    Response getCashoutAccounts(Long userId, String imei, String authToken);

    Response addCashoutAccount(Long userId, String imei, String authToken, Long institutionId, String accountNickName,
                               String accountName, String accountNumber, String accountBranchCode, String accountPhone,
                               String accountEmail);

    Response removeCashoutAccount(Long userId, String imei, String authToken, Long cashoutAccountId);

    Response buyVoucher(Long userId, String imei, String authToken, Long voucherId, BigDecimal voucherValue, String recipient);

    Response swipeTransaction(Long userId, String imei, String authToken, BigDecimal amount, String reference, String cardNumber, String cardPin);

    Response cashoutTransaction(Long userId, String imei, String authToken, BigDecimal amount, String reference, Long cashoutAccountId, String pin);

    Response transferToWallet(Long userId, String imei, String authToken, BigDecimal amount, String recipient, String pin);

    Response getWalletTransactions(Long authUserId, String imei, String authToken, Long walletId);

    Response startSession(String authToken, String username, String password);

    Response endSession(Long sessionId, String deviceId, String authToken);
}
