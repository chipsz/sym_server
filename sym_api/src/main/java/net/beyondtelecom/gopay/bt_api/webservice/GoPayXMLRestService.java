package net.beyondtelecom.gopay.bt_api.webservice;

/***************************************************************************
 * *
 * Created:     19 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

import net.beyondtelecom.gopay.bt_api.service.GoPayRestService;
import net.beyondtelecom.gopay.bt_core.service.GoPayRequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.math.BigDecimal;
import java.util.logging.Logger;

import static java.lang.String.format;
import static javax.ws.rs.core.Response.status;
import static net.beyondtelecom.bt_core_lib.enumeration.BTChannel.SMART_PHONE;
import static net.beyondtelecom.gopay.bt_api.helper.ValidationHelper.getRealParamValue;

@Component
@Path("/server/xml/goPay")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA})
@Produces({MediaType.APPLICATION_XML})
public class GoPayXMLRestService implements GoPayRestService {

	private final GoPayRequestProcessor goPayRequestProcessor;

	@Context
    UriInfo uriInfo;

	private final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

    @Autowired
    public GoPayXMLRestService(GoPayRequestProcessor goPayRequestProcessor) {
        this.goPayRequestProcessor = goPayRequestProcessor;
    }

    @POST @Path("/user")
    public Response registerUser(@FormParam("email") String email,
                                 @FormParam("msisdn") String msisdn,
                                 @FormParam("username") String username,
                                 @FormParam("deviceId") String deviceId,
                                 @FormParam("companyName") String companyName,
                                 @FormParam("firstName") String firstName,
                                 @FormParam("lastName") String lastName,
                                 @FormParam("pin") String pin) {
        logger.info(format("Got request to register new user %s %s", firstName, lastName));
        return status(200).entity(goPayRequestProcessor.registerGoPayUser(
                getRealParamValue(email), getRealParamValue(msisdn), getRealParamValue(username),
                getRealParamValue(deviceId), getRealParamValue(companyName), getRealParamValue(firstName),
                getRealParamValue(lastName), getRealParamValue(pin))).build();
    }

    @GET @Path("/user/{userId}/cashoutAccount")
    public Response getCashoutAccounts(@PathParam("userId") Long userId) {
        logger.info(format("Got request to get cashout accounts for user %s", userId));
        return status(200).entity(goPayRequestProcessor.getCashoutAccounts(getRealParamValue(userId))).build();
    }

    @POST @Path("/user/{userId}/cashoutAccount")
    public Response addCashoutAccount(@PathParam("userId") Long userId,
                                      @FormParam("institutionId") Long institutionId,
                                      @FormParam("accountNickName") String accountNickName,
                                      @FormParam("accountName") String accountName,
                                      @FormParam("accountNumber") String accountNumber,
                                      @FormParam("accountBranchCode") String accountBranchCode,
                                      @FormParam("accountPhone") String accountPhone,
                                      @FormParam("accountEmail") String accountEmail) {
        logger.info(format("Got request to add cashout account %s for user %s", accountNickName, userId));
        return status(200).entity(goPayRequestProcessor.addCashoutAccount(getRealParamValue(userId),
                    getRealParamValue(institutionId), getRealParamValue(accountNickName), getRealParamValue(accountName),
                    getRealParamValue(accountNumber), getRealParamValue(accountBranchCode), getRealParamValue(accountPhone),
                    getRealParamValue(accountEmail))).build();
    }

    @DELETE @Path("/user/{userId}/cashoutAccount/{cashoutAccountId}")
    public Response removeCashoutAccount(@PathParam("userId") Long userId,
                                         @PathParam("cashoutAccountId") Long cashoutAccountId) {
        logger.info(format("Got request to remove cashout account %s for user %s", cashoutAccountId, userId));
        return status(200).entity(goPayRequestProcessor.removeCashoutAccounts(
                getRealParamValue(userId), getRealParamValue(cashoutAccountId))).build();
    }

    @POST @Path("/swipeTransaction")
    public Response swipeTransaction(@FormParam("userId") Long userId,
                                     @FormParam("deviceId") String deviceId,
                                     @FormParam("amount") BigDecimal amount,
                                     @FormParam("reference") String reference,
                                     @FormParam("cardNumber") String cardNumber,
                                     @FormParam("cardPin") String cardPin) {
        logger.info(format("Got request process swipe transaction of %s for user %s from card %s",
                reference, userId, cardNumber));
        return status(200).entity(goPayRequestProcessor.swipeTransaction(getRealParamValue(userId),
                getRealParamValue(deviceId), getRealParamValue(amount),
                getRealParamValue(reference), getRealParamValue(cardNumber),
                getRealParamValue(cardPin))).build();
    }

    @POST @Path("/cashoutTransaction")
    public Response cashoutTransaction(@FormParam("userId") Long userId,
                                       @FormParam("deviceId") String deviceId,
                                       @FormParam("amount") BigDecimal amount,
                                       @FormParam("reference") String reference,
                                       @FormParam("cashoutAccountId") Long cashoutAccountId,
                                       @FormParam("pin") String pin) {
        logger.info(format("Got request process cashout of %s for user %s", amount, userId));
        return status(200).entity(goPayRequestProcessor.cashoutTransaction(getRealParamValue(userId),
                getRealParamValue(deviceId), getRealParamValue(amount),
                getRealParamValue(reference), getRealParamValue(cashoutAccountId),
                getRealParamValue(pin))).build();
    }

    @POST @Path("/session")
    public Response startSession(@FormParam("deviceId") String deviceId,
                                 @FormParam("username") String username,
                                 @FormParam("pin") String pin) {
        logger.info(format("Got login request from device %s for user %s", deviceId, username));
        return status(200).entity(goPayRequestProcessor.startSession(getRealParamValue(deviceId),
            getRealParamValue(username), getRealParamValue(pin), SMART_PHONE)).build();
    }

    @PUT @Path("/session/{sessionId}")
    public Response endSession(@PathParam("sessionId") Long sessionId,
                               @FormParam("deviceId") String deviceId,
                               @FormParam("authToken") String authToken) {
        logger.info(format("Got logout request from device %s for session %s", deviceId, sessionId));
        return status(200).entity(goPayRequestProcessor.endSession(getRealParamValue(sessionId),
            getRealParamValue(deviceId), getRealParamValue(authToken), SMART_PHONE)).build();
    }
}
