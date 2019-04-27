package net.symbiosis.api.service;

import net.symbiosis.api.exception.SymRestException;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

import static net.symbiosis.core_lib.enumeration.DBConfigVars.CONFIG_SYSTEM_NAME;
import static net.symbiosis.persistence.helper.DaoManager.getSymConfigDao;

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
    default Response hello() { return Response.status(200).entity("Hello from " + getSymConfigDao().getConfig(CONFIG_SYSTEM_NAME)).build(); }
    Response getFalconVersion() throws SymRestException;
    Response getFalconBinary() throws SymRestException;
    Response startSession(String deviceId, String username, String pin) throws SymRestException;
    Response endSession(Long sessionId, String deviceId, String authToken) throws SymRestException;
    Response register(String branchName, String machineName, String imei1, String imei2, String imsi1, String imsi2,
                      String msisdn1, String msisdn2, String username, String pin) throws SymRestException;
    Response buyVoucher(Long voucherId, String username, String pin, String cashier, BigDecimal voucherValue,
                        String recipient) throws SymRestException;
    Response queryTransaction(Long voucherPurchaseId, String username, String pin) throws SymRestException;
}
