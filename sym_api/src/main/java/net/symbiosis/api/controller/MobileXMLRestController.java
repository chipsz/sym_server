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

    @Override
    @POST
    @Path("/user")
    public Response registerUser(@FormParam("email") String email,
                                 @FormParam("msisdn") String msisdn,
                                 @FormParam("username") String username,
                                 @FormParam("imei") String imei,
                                 @FormParam("companyName") String companyName,
                                 @FormParam("firstName") String firstName,
                                 @FormParam("lastName") String lastName,
                                 @FormParam("pin") String pin) {
        logger.info(format("Got mobile request to register new user %s %s", firstName, lastName));
        return status(200).entity(mobileRequestProcessor.registerMobileUser(
                getRealParamValue(email), getRealParamValue(msisdn), getRealParamValue(username),
                getRealParamValue(imei), getRealParamValue(companyName), getRealParamValue(firstName),
                getRealParamValue(lastName), getRealParamValue(pin))).header("Access-Control-Allow-Origin", "*").build();
    }

//    @Override
//    @GET
//    @Path("/user/{userId}/cashoutAccount")
//    public Response getCashoutAccounts(@PathParam("userId") Long userId,
//                                       @FormParam("imei") String imei,
//                                       @FormParam("authToken") String authToken
//    ) {
//        logger.info(format("Got mobile request to get cashout accounts for user %s", userId));
//        return status(200).entity(mobileRequestProcessor.getCashoutAccounts(getRealParamValue(userId),
//                getRealParamValue(imei), getRealParamValue(authToken))).header("Access-Control-Allow-Origin", "*").build();
//    }

    @Override
    @POST
    @Path("/user/{userId}/cashoutAccount")
    public Response addCashoutAccount(@PathParam("userId") Long userId,
                                      @FormParam("imei") String imei,
                                      @FormParam("authToken") String authToken,
                                      @FormParam("institutionId") Long institutionId,
                                      @FormParam("accountNickName") String accountNickName,
                                      @FormParam("accountName") String accountName,
                                      @FormParam("accountNumber") String accountNumber,
                                      @FormParam("accountBranchCode") String accountBranchCode,
                                      @FormParam("accountPhone") String accountPhone,
                                      @FormParam("accountEmail") String accountEmail) {
        logger.info(format("Got mobile request to add cashout account %s for user %s", accountNickName, userId));
        return status(200).entity(mobileRequestProcessor.addCashoutAccount(getRealParamValue(userId),
                getRealParamValue(imei), getRealParamValue(authToken),
                getRealParamValue(institutionId), getRealParamValue(accountNickName), getRealParamValue(accountName),
                getRealParamValue(accountNumber), getRealParamValue(accountBranchCode), getRealParamValue(accountPhone),
                getRealParamValue(accountEmail))).header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @DELETE
    @Path("/user/{userId}/cashoutAccount/{cashoutAccountId}")
    public Response removeCashoutAccount(@PathParam("userId") Long userId,
                                         @FormParam("imei") String imei,
                                         @FormParam("authToken") String authToken,
                                         @PathParam("cashoutAccountId") Long cashoutAccountId) {
        logger.info(format("Got mobile request to remove cashout account %s for user %s", cashoutAccountId, userId));
        return status(200).entity(mobileRequestProcessor.removeCashoutAccounts(
                getRealParamValue(userId), getRealParamValue(imei), getRealParamValue(authToken),
                getRealParamValue(cashoutAccountId))).header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @POST @Path("/voucher/{voucherId}/purchase")
    public Response buyVoucher(@FormParam("userId") Long userId,
                               @FormParam("imei") String imei,
                               @FormParam("authToken") String authToken,
                               @PathParam("voucherId") Long voucherId,
                               @FormParam("voucherValue") BigDecimal voucherValue,
                               @FormParam("recipient") String recipient) {
        logger.info(format("Got mobile request to buy voucherId %s (amount=%s) from user %s",
                voucherId, voucherValue == null ? "not specified" : voucherValue, userId));

        return Response.status(200).entity(mobileRequestProcessor.buyVoucher(getRealParamValue(userId),
                getRealParamValue(imei), getRealParamValue(authToken), getRealParamValue(voucherId),
                getRealParamValue(voucherValue), getRealParamValue(recipient))).header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @POST
    @Path("/swipeTransaction")
    public Response swipeTransaction(@FormParam("userId") Long userId,
                                     @FormParam("imei") String imei,
                                     @FormParam("authToken") String authToken,
                                     @FormParam("amount") BigDecimal amount,
                                     @FormParam("reference") String reference,
                                     @FormParam("cardNumber") String cardNumber,
                                     @FormParam("cardPin") String cardPin) {
        logger.info(format("Got mobile request process swipe transaction of %s for user %s from card %s",
                reference, userId, cardNumber));
        return status(200).entity(mobileRequestProcessor.swipeTransaction(getRealParamValue(userId),
                getRealParamValue(imei), getRealParamValue(authToken), getRealParamValue(amount),
                getRealParamValue(reference), getRealParamValue(cardNumber),
                getRealParamValue(cardPin))).header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @POST
    @Path("/cashoutTransaction")
    public Response cashoutTransaction(@FormParam("userId") Long userId,
                                       @FormParam("imei") String imei,
                                       @FormParam("authToken") String authToken,
                                       @FormParam("amount") BigDecimal amount,
                                       @FormParam("reference") String reference,
                                       @FormParam("cashoutAccountId") Long cashoutAccountId,
                                       @FormParam("pin") String pin) {
        logger.info(format("Got mobile request process cashout of %s for user %s", amount, userId));
        return status(200).entity(mobileRequestProcessor.cashoutTransaction(getRealParamValue(userId),
                getRealParamValue(imei), getRealParamValue(authToken), getRealParamValue(amount),
                getRealParamValue(reference), getRealParamValue(cashoutAccountId),
                getRealParamValue(pin))).header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @POST
    @Path("/transfer")
    public Response transferToWallet(@FormParam("userId") Long userId,
                                     @FormParam("imei") String imei,
                                     @FormParam("authToken") String authToken,
                                     @FormParam("amount") BigDecimal amount,
                                     @FormParam("recipient") String recipient,
                                     @FormParam("pin") String pin) {
        logger.info(format("Got mobile request transfer %s from user %s to %s", amount, userId, recipient));
        return status(200).entity(mobileRequestProcessor.transferToWallet(getRealParamValue(userId),
                getRealParamValue(imei), getRealParamValue(authToken), getRealParamValue(amount),
                getRealParamValue(recipient), getRealParamValue(pin))).header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @POST
    @Path("/session")
    public Response startSession(@FormParam("imei") String imei,
                                 @FormParam("username") String username,
                                 @FormParam("pin") String pin) {
        logger.info(format("Got mobile login request from device %s for user %s", imei, username));
        return status(200).entity(mobileRequestProcessor.startSession(getRealParamValue(imei),
                getRealParamValue(username), getRealParamValue(pin), SMART_PHONE)).header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @PUT
    @Path("/session/{sessionId}")
    public Response endSession(@PathParam("sessionId") Long sessionId,
                               @FormParam("imei") String imei,
                               @FormParam("authToken") String authToken) {
        logger.info(format("Got mobile logout request from device %s for session %s", imei, sessionId));
        return status(200).entity(mobileRequestProcessor.endSession(getRealParamValue(sessionId),
                getRealParamValue(imei), getRealParamValue(authToken), SMART_PHONE)).header("Access-Control-Allow-Origin", "*").build();
    }
}
