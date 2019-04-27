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
import net.symbiosis.core.contract.SymDeviceUserResponse;
import net.symbiosis.core.contract.SymMap;
import net.symbiosis.core.contract.SymResponse;
import net.symbiosis.core.service.POSRequestProcessor;
import net.symbiosis.core_lib.enumeration.SymResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.File;
import java.math.BigDecimal;
import java.util.logging.Logger;

import static java.lang.String.format;
import static javax.ws.rs.core.Response.status;
import static net.symbiosis.core_lib.enumeration.DBConfigVars.CONFIG_FALCON_POS_BINARY_LOCATION;
import static net.symbiosis.core_lib.enumeration.DBConfigVars.CONFIG_FALCON_POS_BINARY_NAME;
import static net.symbiosis.persistence.helper.DaoManager.getSymConfigDao;

@Component
@Path("/xml/pos")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED})
@Produces({MediaType.APPLICATION_XML, MediaType.APPLICATION_OCTET_STREAM})
//@Api("POS XML")
public class POSXMLRestController implements POSRestService {

    private final POSRequestProcessor posRequestProcessor;

    @Context UriInfo uriInfo;
	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Autowired
    public POSXMLRestController(POSRequestProcessor posRequestProcessor) {
        this.posRequestProcessor = posRequestProcessor;
    }

    //	@ApiOperation(value = "Get Falcon POS Version", response = SymMap.class)
    @GET @Path("/version/falcon")
    public Response getFalconVersion() throws SymRestException {
        logger.info("Got request to get Falcon POS version");
        SymMap response = posRequestProcessor.getFalconVersion();

        if (response.getSymResponse().getResponse_code().equals(SymResponseCode.GENERAL_ERROR.getCode())) {
            return status(500).build();
        } else {
            return status(200).entity(response).build();
        }
    }

    //	@ApiOperation(value = "Get Falcon POS Version", response = SymMap.class)
    @GET @Path("/update/falcon")
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response getFalconBinary() throws SymRestException {
        logger.info("Got request to download Falcon POS binary");
        File file = new File(getSymConfigDao().getConfig(CONFIG_FALCON_POS_BINARY_LOCATION));

        if (!file.exists()) {
            logger.info("File does not exist at " + getSymConfigDao().getConfig(CONFIG_FALCON_POS_BINARY_LOCATION));
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        logger.info(format("Returning %s bytes of file %s", file.length(), getSymConfigDao().getConfig(CONFIG_FALCON_POS_BINARY_LOCATION)));

        Response.ResponseBuilder response = Response.ok(file);
        response.header("Content-Disposition", "attachment; filename=" + getSymConfigDao().getConfig(CONFIG_FALCON_POS_BINARY_NAME));
        response.header("Content-Length", file.length());
        return response.build();
    }

    //	@ApiOperation(value = "Login POS Machine", response = SymSystemUserList.class)
    @POST
    @Path("/session")
    public Response startSession(@FormParam("imei") String imei,
                                 @FormParam("username") String username,
                                 @FormParam("pin") String pin) throws SymRestException {
        logger.info("Logging in user " + username + " on device " + imei);
        SymDeviceUserResponse response = posRequestProcessor.startPosSession(imei, username, pin);

        if (response.getSymResponse().getResponse_code().equals(SymResponseCode.GENERAL_ERROR.getCode())) {
            return status(500).build();
        } else {
            return status(200).entity(response).build();
        }
    }

    //	@ApiOperation(value = "Log out POS Machine", response = SymResponse.class)
    @PUT @Path("/session/{sessionId}")
    public Response endSession(@PathParam("sessionId") Long sessionId,
                               @FormParam("deviceId") String deviceId,
                               @FormParam("authToken") String authToken) throws SymRestException {
        SymResponse response = posRequestProcessor.endPosSession(sessionId, deviceId, authToken);
        if (response.getResponse_code().equals(SymResponseCode.GENERAL_ERROR.getCode())) {
            return status(500).build();
        } else {
            return status(200).entity(response).build();
        }
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
        SymDeviceUserResponse response = posRequestProcessor.registerPosUser(branchName, machineName,
                imei1, imei2, imsi1, imsi2, msisdn1,msisdn2,username,pin);

        if (response.getSymResponse().getResponse_code().equals(SymResponseCode.GENERAL_ERROR.getCode())) {
            return status(500).build();
        } else {
            return status(200).entity(response).build();
        }
    }

    @Override
    @POST @Path("/voucher/{voucherId}/purchase")
    public Response buyVoucher(@PathParam("voucherId") Long voucherId,
                               @FormParam("username") String username,
                               @FormParam("pin") String pin,
                               @FormParam("cashier") String cashierName,
                               @FormParam("voucherValue") BigDecimal voucherValue,
                               @FormParam("recipient") String recipient) throws SymRestException {
        logger.info(format("Got request to buy voucherId %s (amount=%s) from POS machine with user %s, cashier %s",
            voucherId, voucherValue == null ? "not specified" : voucherValue, username, cashierName));

        return Response.status(200).entity(posRequestProcessor.buyVoucher(voucherId, username, pin,
            voucherValue, recipient, cashierName)).build();
    }

    @Override
    @POST @Path("/voucherPurchase/{voucherPurchaseId}")
    public Response queryTransaction(@PathParam("voucherPurchaseId") Long voucherPurchaseId,
                                     @FormParam("username") String username,
                                     @FormParam("pin") String pin) throws SymRestException {
        logger.info(format("Got request to query transaction %s by POS machine with user %s", voucherPurchaseId, username));

        return Response.status(200).entity(posRequestProcessor.queryTransaction(voucherPurchaseId, username, pin)).build();
    }
}
