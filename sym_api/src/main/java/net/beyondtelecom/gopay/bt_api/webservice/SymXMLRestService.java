package net.beyondtelecom.gopay.bt_api.webservice;

/***************************************************************************
 * *
 * Created:     19 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

import net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode;
import net.beyondtelecom.gopay.bt_api.service.BeyondTelecomRestService;
import net.beyondtelecom.gopay.bt_core.contract.BTResponse;
import net.beyondtelecom.gopay.bt_core.contract.BTSystemUserList;
import net.beyondtelecom.gopay.bt_core.service.BTRequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.util.logging.Logger;

import static java.lang.String.format;
import static javax.ws.rs.core.Response.status;
import static net.beyondtelecom.bt_core_lib.enumeration.BTChannel.WEB;

@Component
@Path("/server/xml")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA})
@Produces({MediaType.APPLICATION_XML})
public class SymXMLRestService implements BeyondTelecomRestService {

	@Autowired
    BTRequestProcessor btRequestProcessor;
	@Context UriInfo uriInfo;
	protected final Logger logger = Logger.getLogger(this.getClass().getSimpleName());

	@GET @Path("/responseCode/{responseCodeId}")
	public Response getResponseCode (@PathParam("responseCodeId") Long responseCodeId) {
		logger.info("Got request to get language with id " + responseCodeId);
		return status(200).entity(btRequestProcessor.getResponseCode(responseCodeId)).build();
	}


	@GET @Path("/language/{languageId}")
	public Response getLanguage (@PathParam("languageId") Long languageId) {
		logger.info("Got request to get language with id " + languageId);
		return status(200).entity(btRequestProcessor.getLanguage(languageId)).build();
	}

	@GET @Path("/channel/{channelId}")
	public Response getChannel(@PathParam("channelId") Long channelId) {
		logger.info("Got request to get channel with id " + channelId);
		return status(200).entity(btRequestProcessor.getChannel(channelId)).build();
	}

	@GET @Path("/country/{countryId}")
	public Response getCountry(@PathParam("countryId") Long countryId) {
		logger.info("Got request to get country with id " + countryId);
		return status(200).entity(btRequestProcessor.getCountry(countryId)).build();
	}

	@GET @Path("/group/{groupId}")
	public Response getGroup(@PathParam("groupId") Long groupId) {
		logger.info("Got request to get group with id " + groupId);
		return status(200).entity(btRequestProcessor.getGroup(groupId)).build();
	}

	@GET @Path("/role/{roleId}")
	public Response getRole(@PathParam("roleId") Long roleId) {
		logger.info("Got request to get role with id " + roleId);
		return status(200).entity(btRequestProcessor.getRole(roleId)).build();
	}

	@GET @Path("/eventType/{eventTypeId}")
	public Response getEventType(@PathParam("eventTypeId") Long eventTypeId) {
		logger.info("Got request to get eventType with id " + eventTypeId);
		return status(200).entity(btRequestProcessor.getEventType(eventTypeId)).build();
	}

    @GET @Path("/responseCode")
    public Response getResponseCodes() {
        logger.info("Got request to get response codes");
        return status(200).entity(btRequestProcessor.getResponseCodes()).build();
    }

    @GET @Path("/systemConfig")
    public Response getSystemConfigs () {
        logger.info("Got request to get system configurations");
        return status(200).entity(btRequestProcessor.getSystemConfigs()).build();
    }

    @Override
    @GET @Path("/financialInstitution/{institutionId}")
    public Response getFinancialInstitution(@PathParam("institutionId") Long institutionId) {
        logger.info("Got request to get financial institutions with id " + institutionId);
        return Response.status(200).entity(btRequestProcessor.getFinancialInstitution(institutionId)).build();
    }

    @Override
    @GET @Path("/financialInstitution")
    public Response getFinancialInstitutions() {
        logger.info("Got request to get financial institutions");
        return Response.status(200).entity(btRequestProcessor.getFinancialInstitutions()).build();
    }

    @Override
    @GET @Path("/currency/{currencyId}")
    public Response getCurrency(@PathParam("currencyId") Long currencyId) {
        logger.info("Got request to get currency with id " + currencyId);
        return Response.status(200).entity(btRequestProcessor.getCurrency(currencyId)).build();
    }

    @Override
    @GET @Path("/currency")
    public Response getCurrencies() {
        logger.info("Got request to get currencies");
        return Response.status(200).entity(btRequestProcessor.getCurrencies()).build();
    }

    @Override
    @GET @Path("/wallet/{walletId}")
    public Response getWallet(@PathParam("walletId") Long walletId) {
        logger.info("Got request to get wallet with id " + walletId);
        return Response.status(200).entity(btRequestProcessor.getWallet(walletId)).build();
    }

    @Override
    @GET @Path("/wallet")
    public Response getWallets() {
        logger.info("Got request to get wallets");
        return Response.status(200).entity(btRequestProcessor.getWallets()).build();
    }

	@POST @Path("/user")
	public Response registerUser(@FormParam("email") String email,
	                             @FormParam("msisdn") String msisdn,
	                             @FormParam("msisdn2") String msisdn2,
	                             @FormParam("username") String username,
	                             @FormParam("deviceId") String deviceId,
	                             @FormParam("firstName") String firstName,
	                             @FormParam("lastName") String lastName,
	                             @FormParam("dateOfBirth") String dateOfBirth) {
		logger.info(format("Got request to register new user %s %s", firstName, lastName));
		return status(200).entity(btRequestProcessor.registerWebUser(email, msisdn, msisdn2, username,
				deviceId, firstName, lastName, dateOfBirth)).build();
	}

	@Override
	@GET @Path("/user")
	public Response searchUser(@QueryParam("email") String email,
							   @QueryParam("msisdn") String msisdn,
							   @QueryParam("username") String username,
							   @QueryParam("firstName") String firstName,
							   @QueryParam("lastName") String lastName) {
        logger.info(format("Got request to search for user with details %s %s %s %s %s",
                email, msisdn, username, firstName, lastName));
		return status(200).entity(btRequestProcessor.searchUser(email, msisdn, username, firstName, lastName)).build();
	}

//	@PUT @Path("/user/{userId}")
//	public Response confirmRegistration(@PathParam("userId") Long userId,
//										@FormParam("authToken") String authToken,
//	                                    @FormParam("deviceId") String deviceId,
//	                                    @FormParam("username") String username,
//	                                    @FormParam("password") String password) {
//		logger.info(format("Got request to complete registration for user %s", username));
//		return status(200).entity(btRequestProcessor.confirmRegistration(userId, authToken,
//				deviceId, username, password, WEB)).build();
//	}

	@POST @Path("/session")
	public Response startSession(@FormParam("deviceId") String deviceId,
	                             @FormParam("username") String username,
	                             @FormParam("password") String password) {
		BTSystemUserList response = btRequestProcessor.startSession(deviceId, username, password, WEB);
		if (response.getBTResponse().getResponse_code().equals(BTResponseCode.GENERAL_ERROR.getCode())) {
			return status(500).build();
		} else {
			return status(200).entity(response).build();
		}
	}

	@PUT @Path("/session/{sessionId}")
	public Response endSession(@PathParam("sessionId") Long sessionId,
	                           @FormParam("deviceId") String deviceId,
	                           @FormParam("authToken") String authToken) {
		BTResponse response = btRequestProcessor.endSession(sessionId, deviceId, authToken, WEB);
		if (response.getResponse_code().equals(BTResponseCode.GENERAL_ERROR.getCode())) {
			return status(500).build();
		} else {
			return status(200).entity(response).build();
		}
	}
}
