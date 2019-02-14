package net.symbiosis.core.service;

import net.symbiosis.core.contract.SymDeviceUserResponse;
import net.symbiosis.core.contract.SymMap;
import net.symbiosis.core.contract.SymResponse;
import net.symbiosis.core.contract.SymVoucherPurchaseList;

import java.math.BigDecimal;

/***************************************************************************
 * *
 * Created:     24 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public interface POSRequestProcessor extends RequestProcessor {

    SymMap getFalconVersion();
    SymDeviceUserResponse startPosSession(String imei, String username, String pin);
    SymResponse endPosSession(Long sessionId, String imei, String authToken);
    SymDeviceUserResponse registerPosUser(String branchName, String machineName, String imei1, String imei2, String imsi1,
                                          String imsi2, String msisdn1, String msisdn2, String username, String pin);
    SymVoucherPurchaseList buyVoucher(Long voucherId, String imei, String pin, BigDecimal voucherValue,
                                      String recipient, String cashierName);
    SymVoucherPurchaseList queryTransaction(Long voucherPurchaseId, String imei, String pin);
}
