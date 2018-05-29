package net.symbiosis.api.service;

import net.symbiosis.api.exception.SymRestException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

/***************************************************************************
 * *
 * Created:     23 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public interface POSRestService {

    @GET
    @Path("hello")
    default Response hello() { return Response.status(200).entity("Hello POS from jarvis!").build(); }
    Response getFalconVersion() throws SymRestException;
    Response getFalconBinary() throws SymRestException;
    Response startSession(String deviceId, String username, String pin) throws SymRestException;
    Response endSession(Long sessionId, String deviceId, String authToken) throws SymRestException;
    Response register(String branchName, String machineName, String imei1, String imei2, String imsi1, String imsi2,
                      String msisdn1, String msisdn2, String username, String pin) throws SymRestException;
    Response buyVoucher(Long voucherId, String imei, String pin, String cashier, BigDecimal voucherValue,
                        String recipient) throws SymRestException;
    Response queryTransaction(Long voucherPurchaseId, String imei, String pin) throws SymRestException;
}
