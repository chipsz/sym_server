package net.symbiosis.api.controller;

/***************************************************************************
 * *
 * Created:     19 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

import net.symbiosis.api.service.SymbiosisRestService;
import net.symbiosis.core.service.SymbiosisRequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

import static javax.ws.rs.core.Response.status;

@Controller
@Path("/xml/")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA})
@Produces({MediaType.APPLICATION_XML})
public class SymXMLRestController implements SymbiosisRestService {

    private final static Logger logger = Logger.getLogger(SymXMLRestController.class.getSimpleName());
    private final SymbiosisRequestProcessor symbiosisRequestProcessor;

    @Autowired
    public SymXMLRestController(SymbiosisRequestProcessor symbiosisRequestProcessor) {
        this.symbiosisRequestProcessor = symbiosisRequestProcessor;
    }

    @GET
    @Path("/responseCode/{responseCodeId}")
    public Response getResponseCode(@PathParam("responseCodeId") Long responseCodeId) {
        logger.info("Got request to get language with id " + responseCodeId);
        return status(200).entity(symbiosisRequestProcessor.getResponseCode(responseCodeId))
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/responseCode")
    public Response getResponseCodes() {
        logger.info("Got request to get response codes");
        return status(200).entity(symbiosisRequestProcessor.getResponseCodes())
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/language/{languageId}")
    public Response getLanguage(@PathParam("languageId") Long languageId) {
        logger.info("Got request to get language with id " + languageId);
        return status(200).entity(symbiosisRequestProcessor.getLanguage(languageId))
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/channel/{channelId}")
    public Response getChannel(@PathParam("channelId") Long channelId) {
        logger.info("Got request to get channel with id " + channelId);
        return status(200).entity(symbiosisRequestProcessor.getChannel(channelId))
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/country/{countryId}")
    public Response getCountry(@PathParam("countryId") Long countryId) {
        logger.info("Got request to get country with id " + countryId);
        return status(200).entity(symbiosisRequestProcessor.getCountry(countryId))
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/group/{groupId}")
    public Response getGroup(@PathParam("groupId") Long groupId) {
        logger.info("Got request to get group with id " + groupId);
        return status(200).entity(symbiosisRequestProcessor.getGroup(groupId))
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/role/{roleId}")
    public Response getRole(@PathParam("roleId") Long roleId) {
        logger.info("Got request to get role with id " + roleId);
        return status(200).entity(symbiosisRequestProcessor.getRole(roleId))
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/eventType/{eventTypeId}")
    public Response getEventType(@PathParam("eventTypeId") Long eventTypeId) {
        logger.info("Got request to get eventType with id " + eventTypeId);
        return status(200).entity(symbiosisRequestProcessor.getEventType(eventTypeId))
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @GET
    @Path("/systemConfig")
    public Response getSystemConfigs() {
        logger.info("Got request to get system configurations");
        return status(200).entity(symbiosisRequestProcessor.getSystemConfigs())
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @GET
    @Path("/financialInstitution/{institutionId}")
    public Response getFinancialInstitution(@PathParam("institutionId") Long institutionId) {
        logger.info("Got request to get financial institutions with id " + institutionId);
        return Response.status(200).entity(symbiosisRequestProcessor.getFinancialInstitution(institutionId))
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @GET
    @Path("/financialInstitution")
    public Response getFinancialInstitutions() {
        logger.info("Got request to get financial institutions");
        return Response.status(200).entity(symbiosisRequestProcessor.getFinancialInstitutions())
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @GET
    @Path("/currency/{currencyId}")
    public Response getCurrency(@PathParam("currencyId") Long currencyId) {
        logger.info("Got request to get currency with id " + currencyId);
        return Response.status(200).entity(symbiosisRequestProcessor.getCurrency(currencyId))
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @GET
    @Path("/currency")
    public Response getCurrencies() {
        logger.info("Got request to get currencies");
        return Response.status(200).entity(symbiosisRequestProcessor.getCurrencies())
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @GET
    @Path("/wallet/{walletId}")
    public Response getWallet(@PathParam("walletId") Long walletId) {
        logger.info("Got request to get wallet with id " + walletId);
        return Response.status(200).entity(symbiosisRequestProcessor.getWallet(walletId))
                .header("Access-Control-Allow-Origin", "*").build();
    }

    @Override
    @GET
    @Path("/wallet")
    public Response getWallets() {
        logger.info("Got request to get wallets");
        return Response.status(200).entity(symbiosisRequestProcessor.getWallets())
                .header("Access-Control-Allow-Origin", "*").build();
    }
}
