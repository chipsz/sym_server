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

    Response getCashoutAccounts(Long userId);

    Response addCashoutAccount(Long userId, Long institutionId, String accountNickName, String accountName,
                               String accountNumber, String accountBranchCode, String accountPhone, String accountEmail);

    Response swipeTransaction(Long userId, String deviceId, BigDecimal amount,
                              String reference, String cardNumber, String cardPin);

    Response cashoutTransaction(Long userId, String deviceId, BigDecimal amount,
                                String reference, Long cashoutAccountId, String pin);

    Response startSession(String authToken, String username, String password);

    Response endSession(Long sessionId, String deviceId, String authToken);
}
