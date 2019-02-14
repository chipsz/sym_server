package net.symbiosis.api.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import net.symbiosis.core.service.WebRequestProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/***************************************************************************
 * Created:     9/5/2018                                                  *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     kaviyat@payserv.co.zw                                      *
 **************************************************************************/

@Controller
@Path("/web/")
@Consumes({MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED})
@Produces({MediaType.APPLICATION_JSON})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WebJSONRestController extends WebXMLRestController {

    @Autowired
    public WebJSONRestController(WebRequestProcessor webRequestProcessor) {
        super(webRequestProcessor);
    }

    @Override
    @POST
    @Path("/user")
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
    @GET
    @Path("/user")
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
    @POST
    @Path("/session")
    public Response startSession(@FormParam("deviceId") String deviceId,
                                 @FormParam("username") String username,
                                 @FormParam("password") String password) {
        return super.startSession(deviceId, username, password);
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
