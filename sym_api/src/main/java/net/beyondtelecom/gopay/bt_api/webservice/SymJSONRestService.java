package net.beyondtelecom.gopay.bt_api.webservice;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.stereotype.Component;

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

@Component
@Path("/server/json")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Produces({MediaType.APPLICATION_JSON})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SymJSONRestService extends SymXMLRestService {

	@Override
	@GET @Path("/responseCode/{responseCodeId}")
	public Response getResponseCode (@PathParam("responseCodeId") Long responseCodeId) {
		return super.getResponseCode(responseCodeId);
	}

	@Override
	@GET @Path("/language/{languageId}")
	public Response getLanguage (@PathParam("languageId") Long languageId) {
		return super.getLanguage(languageId);
	}

	@Override
	@GET @Path("/channel/{channelId}")
	public Response getChannel(@PathParam("channelId") Long channelId) {
		return super.getChannel(channelId);
	}

	@Override
	@GET @Path("/country/{countryId}")
	public Response getCountry(@PathParam("countryId") Long countryId) {
		return super.getCountry(countryId);
	}

	@Override
	@GET @Path("/group/{groupId}")
	public Response getGroup(@PathParam("groupId") Long groupId) {
		return super.getGroup(groupId);
	}

	@Override
	@GET @Path("/role/{roleId}")
	public Response getRole(@PathParam("roleId") Long roleId) { return super.getRole(roleId); }

	@Override
	@GET @Path("/eventType/{eventTypeId}")
	public Response getEventType(@PathParam("eventTypeId") Long eventTypeId) {
		return super.getEventType(eventTypeId);
	}

    @Override
    @GET @Path("/responseCode")
    public Response getResponseCodes() { return super.getResponseCodes(); }

    @Override
    @GET @Path("/systemConfig")
    public Response getSystemConfigs() { return super.getSystemConfigs(); }

    @Override
    @GET @Path("/financialInstitution/{institutionId}")
    public Response getFinancialInstitution(@PathParam("institutionId") Long institutionId) {
        return super.getFinancialInstitution(institutionId);
    }

    @Override
    @GET @Path("/financialInstitution")
    public Response getFinancialInstitutions() { return super.getFinancialInstitutions(); }

    @Override
    @GET
    @Path("/currency/{currencyId}")
    public Response getCurrency(@PathParam("currencyId") Long currencyId) {
        return super.getCurrency(currencyId);
    }

    @Override
    @GET @Path("/currency")
    public Response getCurrencies() { return super.getCurrencies(); }

    @Override
    @GET
    @Path("/wallet/{walletId}")
    public Response getWallet(@PathParam("walletId") Long walletId) {
        return super.getWallet(walletId);
    }

    @Override
    @GET @Path("/wallet")
    public Response getWallets() { return super.getWallets(); }

	@Override
	@POST @Path("/user")
	public Response registerUser(@FormParam("email") String email,
	                             @FormParam("msisdn") String msisdn,
	                             @FormParam("msisdn2") String msisdn2,
	                             @FormParam("username") String username,
	                             @FormParam("deviceId") String deviceId,
	                             @FormParam("firstName") String firstName,
	                             @FormParam("lastName") String lastName,
	                             @FormParam("dateOfBirth") String dateOfBirth) {
		return super.registerUser(email, msisdn, msisdn2, username, deviceId, firstName, lastName, dateOfBirth);
	}

	@Override
	@GET @Path("/user")
	public Response searchUser(@QueryParam("email") String email,
							   @QueryParam("msisdn") String msisdn,
							   @QueryParam("username") String username,
							   @QueryParam("firstName") String firstName,
							   @QueryParam("lastName") String lastName) {
		return super.searchUser(email, msisdn, username, firstName, lastName);
	}

//	@Override
//	@PUT @Path("/user/{userId}")
//	public Response confirmRegistration(
//								 @PathParam("userId") Long userId,
//	                             @FormParam("authToken") String authToken,
//	                             @FormParam("deviceId") String deviceId,
//	                             @FormParam("username") String username,
//	                             @FormParam("password") String password) {
//		return super.confirmRegistration(userId, authToken, deviceId, username, password);
//	}

	@Override
	@POST @Path("/session")
	public Response startSession(@FormParam("deviceId") String deviceId,
	                             @FormParam("username") String username,
	                             @FormParam("password") String password) {
		return super.startSession(deviceId, username, password);
	}

	@Override
	@PUT @Path("/session/{sessionId}")
	public Response endSession(@PathParam("sessionId") Long sessionId,
	                           @FormParam("deviceId") String deviceId,
	                           @FormParam("authToken") String authToken) {
		return super.endSession(sessionId, deviceId, authToken);
	}

}
