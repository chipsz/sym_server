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
    @Path("/authUser/{authUserId}/cashoutAccount")
    public Response addCashoutAccount(@PathParam("authUserId") Long authUserId,
                                      @FormParam("imei") String imei,
                                      @FormParam("authToken") String authToken,
                                      @FormParam("institutionId") Long institutionId,
                                      @FormParam("accountNickName") String accountNickName,
                                      @FormParam("accountName") String accountName,
                                      @FormParam("accountNumber") String accountNumber,
                                      @FormParam("accountBranchCode") String accountBranchCode,
                                      @FormParam("accountPhone") String accountPhone,
                                      @FormParam("accountEmail") String accountEmail) {
        logger.info(format("Got mobile request to add cashout account %s for auth user %s", accountNickName, authUserId));
        return status(200).entity(mobileRequestProcessor.addCashoutAccount(getRealParamValue(authUserId),
                getRealParamValue(imei), getRealParamValue(authToken),
                getRealParamValue(institutionId), getRealParamValue(accountNickName), getRealParamValue(accountName),
                getRealParamValue(accountNumber), getRealParamValue(accountBranchCode), getRealParamValue(accountPhone),
                getRealParamValue(accountEmail))).header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @DELETE
    @Path("/authUser/{authUserId}/cashoutAccount/{cashoutAccountId}")
    public Response removeCashoutAccount(@PathParam("authUserId") Long authUserId,
                                         @FormParam("imei") String imei,
                                         @FormParam("authToken") String authToken,
                                         @PathParam("cashoutAccountId") Long cashoutAccountId) {
        logger.info(format("Got mobile request to remove cashout account %s for auth user %s", cashoutAccountId, authUserId));
        return status(200).entity(mobileRequestProcessor.removeCashoutAccounts(
                getRealParamValue(authUserId), getRealParamValue(imei), getRealParamValue(authToken),
                getRealParamValue(cashoutAccountId))).header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @POST @Path("/voucher/{voucherId}/purchase")
    public Response buyVoucher(@FormParam("authUserId") Long authUserId,
                               @FormParam("imei") String imei,
                               @FormParam("authToken") String authToken,
                               @PathParam("voucherId") Long voucherId,
                               @FormParam("voucherValue") BigDecimal voucherValue,
                               @FormParam("recipient") String recipient) {
        logger.info(format("Got mobile request to buy voucherId %s (amount=%s) from auth user %s",
                voucherId, voucherValue == null ? "not specified" : voucherValue, authUserId));

        return Response.status(200).entity(mobileRequestProcessor.buyVoucher(getRealParamValue(authUserId),
                getRealParamValue(imei), getRealParamValue(authToken), getRealParamValue(voucherId),
                getRealParamValue(voucherValue), getRealParamValue(recipient))).header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @POST
    @Path("/swipeTransaction")
    public Response swipeTransaction(@FormParam("authUserId") Long authUserId,
                                     @FormParam("imei") String imei,
                                     @FormParam("authToken") String authToken,
                                     @FormParam("amount") BigDecimal amount,
                                     @FormParam("reference") String reference,
                                     @FormParam("cardNumber") String cardNumber,
                                     @FormParam("cardPin") String cardPin) {
        logger.info(format("Got mobile request process swipe transaction of %s for auth user %s from card %s",
                reference, authUserId, cardNumber));
        return status(200).entity(mobileRequestProcessor.swipeTransaction(getRealParamValue(authUserId),
                getRealParamValue(imei), getRealParamValue(authToken), getRealParamValue(amount),
                getRealParamValue(reference), getRealParamValue(cardNumber),
                getRealParamValue(cardPin))).header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @POST
    @Path("/cashoutTransaction")
    public Response cashoutTransaction(@FormParam("authUserId") Long authUserId,
                                       @FormParam("imei") String imei,
                                       @FormParam("authToken") String authToken,
                                       @FormParam("amount") BigDecimal amount,
                                       @FormParam("reference") String reference,
                                       @FormParam("cashoutAccountId") Long cashoutAccountId,
                                       @FormParam("pin") String pin) {
        logger.info(format("Got mobile request process cashout of %s for auth user %s", amount, authUserId));
        return status(200).entity(mobileRequestProcessor.cashoutTransaction(getRealParamValue(authUserId),
                getRealParamValue(imei), getRealParamValue(authToken), getRealParamValue(amount),
                getRealParamValue(reference), getRealParamValue(cashoutAccountId),
                getRealParamValue(pin))).header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @POST
    @Path("/transfer")
    public Response transferToWallet(@FormParam("authUserId") Long userId,
                                     @FormParam("imei") String imei,
                                     @FormParam("authToken") String authToken,
                                     @FormParam("amount") BigDecimal amount,
                                     @FormParam("recipient") String recipient,
                                     @FormParam("pin") String pin) {
        logger.info(format("Got mobile request transfer %s from auth user %s to %s", amount, userId, recipient));
        return status(200).entity(mobileRequestProcessor.transferToWallet(getRealParamValue(userId),
                getRealParamValue(imei), getRealParamValue(authToken), getRealParamValue(amount),
                getRealParamValue(recipient), getRealParamValue(pin))).header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @POST
    @Path("/wallet/{walletId}")
    public Response getWallet(@FormParam("authUserId") Long authUserId,
                              @FormParam("imei") String imei,
                              @FormParam("authToken") String authToken,
                              @PathParam("walletId") Long walletId) {
        logger.info(format("Got mobile request to get wallet %s details from auth user %s with device id %s", walletId, authUserId, imei));
        return status(200).entity(mobileRequestProcessor.getWallet(getRealParamValue(authUserId),
		        getRealParamValue(imei), getRealParamValue(authToken), getRealParamValue(walletId)
        )).header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @POST
    @Path("/wallet/{walletId}/transaction")
    public Response getWalletTransactions(@FormParam("authUserId") Long authUserId,
                                          @FormParam("imei") String imei,
                                          @FormParam("authToken") String authToken,
                                          @PathParam("walletId") Long walletId) {
        logger.info(format("Got mobile request to get wallet %s transactions from auth user %s with device id %s", walletId, authUserId, imei));
        return status(200).entity(mobileRequestProcessor.getWalletTransactions(getRealParamValue(authUserId),
		        getRealParamValue(imei), getRealParamValue(authToken), getRealParamValue(walletId)
        )).header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @POST @Path("/voucherPurchase/{voucherPurchaseId}")
    public Response getVoucherPurchase(@FormParam("authUserId") Long authUserId,
                                       @FormParam("imei") String imei,
                                       @FormParam("authToken") String authToken,
                                       @PathParam("voucherPurchaseId") Long voucherPurchaseId) {
        logger.info(format("Got mobile request to query transaction %s by auth user %s", voucherPurchaseId, authUserId));

        return Response.status(200).entity(mobileRequestProcessor.getVoucherPurchase(getRealParamValue(authUserId),
	        getRealParamValue(imei), getRealParamValue(authToken), getRealParamValue(voucherPurchaseId))
        ).header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @POST
    @Path("/session")
    public Response startSession(@FormParam("imei") String imei,
                                 @FormParam("username") String username,
                                 @FormParam("pin") String pin) {
        logger.info(format("Got mobile login request from device %s for username %s", imei, username));
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
