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
                                 @FormParam("deviceId") String deviceId,
                                 @FormParam("companyName") String companyName,
                                 @FormParam("firstName") String firstName,
                                 @FormParam("lastName") String lastName,
                                 @FormParam("pin") String pin) {
        return super.registerUser(email, msisdn, username, deviceId, companyName, firstName, lastName, pin);
    }

    @GET
    @Path("/user/{userId}/cashoutAccount")
    public Response getCashoutAccounts(@PathParam("userId") Long userId) {
        return super.getCashoutAccounts(userId);
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
        return super.addCashoutAccount(userId, institutionId, accountNickName, accountName, accountNumber,
                accountBranchCode, accountPhone, accountEmail);
    }

    @POST
    @Path("/swipeTransaction")
    public Response swipeTransaction(@FormParam("userId") Long userId,
                                     @FormParam("deviceId") String deviceId,
                                     @FormParam("amount") BigDecimal amount,
                                     @FormParam("reference") String reference,
                                     @FormParam("cardNumber") String cardNumber,
                                     @FormParam("cardPin") String cardPin) {
        return super.swipeTransaction(userId, deviceId, amount, reference, cardNumber, cardPin);
    }

    @POST
    @Path("/cashoutTransaction")
    public Response cashoutTransaction(@FormParam("userId") Long userId,
                                       @FormParam("deviceId") String deviceId,
                                       @FormParam("amount") BigDecimal amount,
                                       @FormParam("reference") String reference,
                                       @FormParam("cashoutAccountId") Long cashoutAccountId,
                                       @FormParam("pin") String pin) {
        return super.cashoutTransaction(userId, deviceId, amount, reference, cashoutAccountId, pin);
    }

    @Override
    @POST
    @Path("/session")
    public Response startSession(@FormParam("deviceId") String deviceId,
                                 @FormParam("username") String username,
                                 @FormParam("pin") String pin) {
        return super.startSession(deviceId, username, pin);
    }

    @Override
    @PUT
    @Path("/session/{sessionId}")
    public Response endSession(@PathParam("sessionId") Long sessionId,
                               @FormParam("deviceId") String deviceId,
                               @FormParam("authToken") String authToken) {
        return super.endSession(sessionId, deviceId, authToken);
    }
}
