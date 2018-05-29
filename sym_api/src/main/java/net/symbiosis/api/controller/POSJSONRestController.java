package net.symbiosis.api.controller;

/***************************************************************************
 * *
 * Created:     19 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

import net.symbiosis.api.exception.SymRestException;
import net.symbiosis.api.service.POSRestService;
import net.symbiosis.core.service.POSRequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

@Component
@Path("/pos")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM})
//@Api("POS JSON")
public class POSJSONRestController extends POSXMLRestController implements POSRestService {

    @Autowired
    public POSJSONRestController(POSRequestProcessor ettlposRequestProcessor) {
        super(ettlposRequestProcessor);
    }

    //	@ApiOperation(value = "Get Falcon POS Version", response = SymMap.class)
    @GET @Path("/version/falcon")
    public Response getFalconVersion() throws SymRestException { return super.getFalconVersion(); }

    //	@ApiOperation(value = "Get Falcon POS Binary", response = SymMap.class)
    @GET @Path("/update/falcon")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFalconBinary() throws SymRestException { return super.getFalconBinary(); }

    //	@ApiOperation(value = "Login POS Machine", response = SymSystemUserList.class)
    @POST @Path("/session")
    public Response startSession(@FormParam("imei") String imei,
                                 @FormParam("username") String username,
                                 @FormParam("pin") String pin) throws SymRestException {
        return super.startSession(imei, username, pin);
    }

    //	@ApiOperation(value = "Log out of POS Machine", response = SymResponse.class)
    @PUT @Path("/session/{sessionId}")
    public Response endSession(@PathParam("sessionId") Long sessionId,
                               @FormParam("imei") String imei,
                               @FormParam("authToken") String authToken) throws SymRestException {
        return super.endSession(sessionId, imei, authToken);
    }

    //	@ApiOperation(value = "Register POS Machine", response = SymSystemUserList.class)
    @POST @Path("/register")
    public Response register(@FormParam("branchName") String branchName,
                             @FormParam("machineName") String machineName,
                             @FormParam("imei1") String imei1,
                             @FormParam("imei2") String imei2,
                             @FormParam("imsi1") String imsi1,
                             @FormParam("imsi2") String imsi2,
                             @FormParam("msisdn1") String msisdn1,
                             @FormParam("msisdn2") String msisdn2,
                             @FormParam("username") String username,
                             @FormParam("pin") String pin) throws SymRestException {
        return super.register(branchName, machineName, imei1, imei2, imsi1, imsi2, msisdn1, msisdn2, username, pin);
    }

    @POST @Path("/voucher/{voucherId}/purchase")
    public Response buyVoucher(@PathParam("voucherId") Long voucherId,
                               @FormParam("imei") String imei,
                               @FormParam("pin") String pin,
                               @FormParam("cashier") String cashierName,
                               @FormParam("voucherValue") BigDecimal voucherValue,
                               @FormParam("recipient") String recipient) throws SymRestException {
        return super.buyVoucher(voucherId, imei, pin, cashierName, voucherValue, recipient);
    }

    @POST @Path("/voucherPurchase/{voucherPurchaseId}")
    public Response queryTransaction(@PathParam("voucherPurchaseId") Long voucherPurchaseId,
                                     @FormParam("imei") String imei,
                                     @FormParam("pin") String pin) throws SymRestException {
        return super.queryTransaction(voucherPurchaseId, imei, pin);
    }

}
