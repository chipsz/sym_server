package net.symbiosis.api.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.symbiosis.core.service.SymbiosisRequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/***************************************************************************
 * *
 * Created:     19 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

@Controller
@Path("/symbiosis/")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Produces({MediaType.APPLICATION_JSON})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SymJSONRestController extends SymXMLRestController {

    @Autowired
    public SymJSONRestController(SymbiosisRequestProcessor symbiosisRequestProcessor) {
        super(symbiosisRequestProcessor);
    }

    @Override
    @GET
    @Path("/responseCode/{responseCodeId}")
    public Response getResponseCode(@PathParam("responseCodeId") Long responseCodeId) {
        return super.getResponseCode(responseCodeId);
    }

    @Override
    @GET
    @Path("/responseCode")
    public Response getResponseCodes() {
        return super.getResponseCodes();
    }

    @Override
    @GET
    @Path("/language/{languageId}")
    public Response getLanguage(@PathParam("languageId") Long languageId) {
        return super.getLanguage(languageId);
    }

    @Override
    @GET
    @Path("/channel/{channelId}")
    public Response getChannel(@PathParam("channelId") Long channelId) {
        return super.getChannel(channelId);
    }

    @Override
    @GET
    @Path("/country/{countryId}")
    public Response getCountry(@PathParam("countryId") Long countryId) {
        return super.getCountry(countryId);
    }

    @Override
    @GET
    @Path("/group/{groupId}")
    public Response getGroup(@PathParam("groupId") Long groupId) {
        return super.getGroup(groupId);
    }

    @Override
    @GET
    @Path("/role/{roleId}")
    public Response getRole(@PathParam("roleId") Long roleId) {
        return super.getRole(roleId);
    }

    @Override
    @GET
    @Path("/eventType/{eventTypeId}")
    public Response getEventType(@PathParam("eventTypeId") Long eventTypeId) {
        return super.getEventType(eventTypeId);
    }

    @Override
    @GET
    @Path("/systemConfig")
    public Response getSystemConfigs() {
        return super.getSystemConfigs();
    }

    @Override
    @GET
    @Path("/financialInstitution/{institutionId}")
    public Response getFinancialInstitution(@PathParam("institutionId") Long institutionId) {
        return super.getFinancialInstitution(institutionId);
    }

    @Override
    @GET
    @Path("/financialInstitution")
    public Response getFinancialInstitutions() {
        return super.getFinancialInstitutions();
    }

    @Override
    @GET
    @Path("/currency/{currencyId}")
    public Response getCurrency(@PathParam("currencyId") Long currencyId) {
        return super.getCurrency(currencyId);
    }

    @Override
    @GET
    @Path("/currency")
    public Response getCurrencies() {
        return super.getCurrencies();
    }

    @Override
    @GET
    @Path("/wallet/{walletId}")
    public Response getWallet(@PathParam("walletId") Long walletId) {
        return super.getWallet(walletId);
    }

    @Override
    @GET
    @Path("/wallet")
    public Response getWallets() {
        return super.getWallets();
    }
}
