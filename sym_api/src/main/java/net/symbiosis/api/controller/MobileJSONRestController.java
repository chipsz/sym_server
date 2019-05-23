package net.symbiosis.api.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.symbiosis.core.service.MobileRequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;

/***************************************************************************
 * *
 * Created:     19 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Controller
@Path("/mobi")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA})
@Produces("application/json; charset=UTF-8")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MobileJSONRestController extends MobileXMLRestController {

    @Autowired
    public MobileJSONRestController(MobileRequestProcessor mobileRequestProcessor) {
        super(mobileRequestProcessor);
    }

    @Override
    @POST
    @Path("/user")
    public Response registerUser(@FormParam("email") String email,
                                 @FormParam("msisdn") String msisdn,
                                 @FormParam("username") String username,
                                 @FormParam("imei") String deviceId,
                                 @FormParam("companyName") String companyName,
                                 @FormParam("firstName") String firstName,
                                 @FormParam("lastName") String lastName,
                                 @FormParam("pin") String pin) {
        return super.registerUser(email, msisdn, username, deviceId, companyName, firstName, lastName, pin);
    }

//    @Override
//    @POST
//    @Path("/user/{userId}/cashoutAccount")
//    public Response getCashoutAccounts(@PathParam("userId") Long userId,
//                                       @FormParam("imei") String imei,
//                                       @FormParam("authToken") String authToken) {
//        return super.getCashoutAccounts(userId, imei, authToken);
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
        return super.addCashoutAccount(authUserId, imei, authToken, institutionId, accountNickName, accountName, accountNumber,
                accountBranchCode, accountPhone, accountEmail);
    }


    @Override
    @DELETE
    @Path("/authUser/{authUserId}/cashoutAccount/{cashoutAccountId}")
    public Response removeCashoutAccount(@PathParam("authUserId") Long authUserId,
                                         @FormParam("imei") String imei,
                                         @FormParam("authToken") String authToken,
                                         @PathParam("cashoutAccountId") Long cashoutAccountId) {
        return super.removeCashoutAccount(authUserId, imei, authToken, cashoutAccountId);
    }


    @Override
    @POST @Path("/voucher/{voucherId}/purchase")
    public Response buyVoucher(@FormParam("authUserId") Long authUserId,
                               @FormParam("imei") String imei,
                               @FormParam("authToken") String authToken,
                               @PathParam("voucherId") Long voucherId,
                               @FormParam("voucherValue") BigDecimal voucherValue,
                               @FormParam("recipient") String recipient) {
        return super.buyVoucher(authUserId, imei, authToken, voucherId, voucherValue, recipient);
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
        return super.swipeTransaction(authUserId, imei, authToken, amount, reference, cardNumber, cardPin);
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
        return super.cashoutTransaction(authUserId, imei, authToken, amount, reference, cashoutAccountId, pin);
    }

    @Override
    @POST
    @Path("/transfer")
    public Response transferToWallet(@FormParam("authUserId") Long authUserId,
                                     @FormParam("imei") String imei,
                                     @FormParam("authToken") String authToken,
                                     @FormParam("amount") BigDecimal amount,
                                     @FormParam("recipient") String recipient,
                                     @FormParam("pin") String pin) {
        return super.transferToWallet(authUserId, imei, authToken, amount, recipient, pin);
    }

    @Override
    @POST
    @Path("/wallet/{walletId}")
    public Response getWallet(@FormParam("authUserId") Long authUserId,
                              @FormParam("imei") String imei,
                              @FormParam("authToken") String authToken,
                              @PathParam("walletId") Long walletId) {
        return super.getWallet(authUserId, imei, authToken, walletId);
    }

    @Override
    @POST
    @Path("/wallet/{walletId}/transaction")
    public Response getWalletTransactions(@FormParam("authUserId") Long authUserId,
                                          @FormParam("imei") String imei,
                                          @FormParam("authToken") String authToken,
                                          @PathParam("walletId") Long walletId) {
        return super.getWalletTransactions(authUserId, imei, authToken, walletId);
    }

    @Override
    @POST
    @Path("/session")
    public Response startSession(@FormParam("imei") String imei,
                                 @FormParam("username") String username,
                                 @FormParam("pin") String pin) {
        return super.startSession(imei, username, pin);
    }

    @Override
    @PUT
    @Path("/session/{sessionId}")
    public Response endSession(@PathParam("sessionId") Long sessionId,
                               @FormParam("imei") String imei,
                               @FormParam("authToken") String authToken) {
        return super.endSession(sessionId, imei, authToken);
    }
}
