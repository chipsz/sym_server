package net.symbiosis.api.controller;

/***************************************************************************
 * *
 * Created:     19 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

import net.symbiosis.api.service.MobileRestService;
import net.symbiosis.core.service.MobileRequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.util.logging.Logger;

import static java.lang.String.format;
import static javax.ws.rs.core.Response.status;
import static net.symbiosis.api.ServiceCommon.getRealParamValue;
import static net.symbiosis.core_lib.enumeration.SymChannel.SMART_PHONE;

@Controller
@Path("/xml/mobi")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA})
@Produces({MediaType.APPLICATION_XML})
public class MobileXMLRestController implements MobileRestService {

    private static final Logger logger = Logger.getLogger(MobileXMLRestController.class.getSimpleName());
    private final MobileRequestProcessor mobileRequestProcessor;

    @Autowired
    public MobileXMLRestController(MobileRequestProcessor mobileRequestProcessor) {
        this.mobileRequestProcessor = mobileRequestProcessor;
    }

    @POST
    @Path("/user")
    public Response registerUser(@FormParam("email") String email,
                                 @FormParam("msisdn") String msisdn,
                                 @FormParam("username") String username,
                                 @FormParam("deviceId") String deviceId,
                                 @FormParam("companyName") String companyName,
                                 @FormParam("firstName") String firstName,
                                 @FormParam("lastName") String lastName,
                                 @FormParam("pin") String pin) {
        logger.info(format("Got request to register new user %s %s", firstName, lastName));
        return status(200).entity(mobileRequestProcessor.registerMobileUser(
                getRealParamValue(email), getRealParamValue(msisdn), getRealParamValue(username),
                getRealParamValue(deviceId), getRealParamValue(companyName), getRealParamValue(firstName),
                getRealParamValue(lastName), getRealParamValue(pin))).build();
    }

    @GET
    @Path("/user/{userId}/cashoutAccount")
    public Response getCashoutAccounts(@PathParam("userId") Long userId) {
        logger.info(format("Got request to get cashout accounts for user %s", userId));
        return status(200).entity(mobileRequestProcessor.getCashoutAccounts(getRealParamValue(userId))).build();
    }

    @POST
    @Path("/user/{userId}/cashoutAccount")
    public Response addCashoutAccount(@PathParam("userId") Long userId,
                                      @FormParam("institutionId") Long institutionId,
                                      @FormParam("accountNickName") String accountNickName,
                                      @FormParam("accountName") String accountName,
                                      @FormParam("accountNumber") String accountNumber,
                                      @FormParam("accountBranchCode") String accountBranchCode,
                                      @FormParam("accountPhone") String accountPhone,
                                      @FormParam("accountEmail") String accountEmail) {
        logger.info(format("Got request to add cashout account %s for user %s", accountNickName, userId));
        return status(200).entity(mobileRequestProcessor.addCashoutAccount(getRealParamValue(userId),
                getRealParamValue(institutionId), getRealParamValue(accountNickName), getRealParamValue(accountName),
                getRealParamValue(accountNumber), getRealParamValue(accountBranchCode), getRealParamValue(accountPhone),
                getRealParamValue(accountEmail))).build();
    }

    @DELETE
    @Path("/user/{userId}/cashoutAccount/{cashoutAccountId}")
    public Response removeCashoutAccount(@PathParam("userId") Long userId,
                                         @PathParam("cashoutAccountId") Long cashoutAccountId) {
        logger.info(format("Got request to remove cashout account %s for user %s", cashoutAccountId, userId));
        return status(200).entity(mobileRequestProcessor.removeCashoutAccounts(
                getRealParamValue(userId), getRealParamValue(cashoutAccountId))).build();
    }

    @POST
    @Path("/swipeTransaction")
    public Response swipeTransaction(@FormParam("userId") Long userId,
                                     @FormParam("deviceId") String deviceId,
                                     @FormParam("amount") BigDecimal amount,
                                     @FormParam("reference") String reference,
                                     @FormParam("cardNumber") String cardNumber,
                                     @FormParam("cardPin") String cardPin) {
        logger.info(format("Got request process swipe transaction of %s for user %s from card %s",
                reference, userId, cardNumber));
        return status(200).entity(mobileRequestProcessor.swipeTransaction(getRealParamValue(userId),
                getRealParamValue(deviceId), getRealParamValue(amount),
                getRealParamValue(reference), getRealParamValue(cardNumber),
                getRealParamValue(cardPin))).build();
    }

    @POST
    @Path("/cashoutTransaction")
    public Response cashoutTransaction(@FormParam("userId") Long userId,
                                       @FormParam("deviceId") String deviceId,
                                       @FormParam("amount") BigDecimal amount,
                                       @FormParam("reference") String reference,
                                       @FormParam("cashoutAccountId") Long cashoutAccountId,
                                       @FormParam("pin") String pin) {
        logger.info(format("Got request process cashout of %s for user %s", amount, userId));
        return status(200).entity(mobileRequestProcessor.cashoutTransaction(getRealParamValue(userId),
                getRealParamValue(deviceId), getRealParamValue(amount),
                getRealParamValue(reference), getRealParamValue(cashoutAccountId),
                getRealParamValue(pin))).build();
    }

    @POST
    @Path("/session")
    public Response startSession(@FormParam("deviceId") String deviceId,
                                 @FormParam("username") String username,
                                 @FormParam("pin") String pin) {
        logger.info(format("Got login request from device %s for user %s", deviceId, username));
        return status(200).entity(mobileRequestProcessor.startSession(getRealParamValue(deviceId),
                getRealParamValue(username), getRealParamValue(pin), SMART_PHONE)).build();
    }

    @PUT
    @Path("/session/{sessionId}")
    public Response endSession(@PathParam("sessionId") Long sessionId,
                               @FormParam("deviceId") String deviceId,
                               @FormParam("authToken") String authToken) {
        logger.info(format("Got logout request from device %s for session %s", deviceId, sessionId));
        return status(200).entity(mobileRequestProcessor.endSession(getRealParamValue(sessionId),
                getRealParamValue(deviceId), getRealParamValue(authToken), SMART_PHONE)).build();
    }
}
