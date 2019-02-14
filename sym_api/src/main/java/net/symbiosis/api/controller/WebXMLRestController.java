package net.symbiosis.api.controller;

import net.symbiosis.api.service.WebRestService;
import net.symbiosis.core.contract.SymResponse;
import net.symbiosis.core.contract.SymSystemUserList;
import net.symbiosis.core.service.WebRequestProcessor;
import net.symbiosis.core_lib.enumeration.SymResponseCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.logging.Logger;

import static java.lang.String.format;
import static javax.ws.rs.core.Response.status;
import static net.symbiosis.core_lib.enumeration.SymChannel.WEB;

/***************************************************************************
 * Created:     9/5/2018                                                  *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     kaviyat@payserv.co.zw                                      *
 **************************************************************************/

@Controller
@Path("/xml/web")
@Consumes({MediaType.APPLICATION_XML, MediaType.APPLICATION_FORM_URLENCODED, MediaType.MULTIPART_FORM_DATA})
@Produces({MediaType.APPLICATION_XML})
public class WebXMLRestController implements WebRestService {

    private static final Logger logger = Logger.getLogger(WebXMLRestController.class.getSimpleName());
    private final WebRequestProcessor webRequestProcessor;

    @Autowired
    public WebXMLRestController(WebRequestProcessor webRequestProcessor) {
        this.webRequestProcessor = webRequestProcessor;
    }

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
        logger.info(format("Got request to register new user %s %s", firstName, lastName));
        return status(200).entity(webRequestProcessor.registerWebUser(email, msisdn, msisdn2, username,
                deviceId, firstName, lastName, dateOfBirth)).build();
    }

    @Override
    @GET
    @Path("/user")
    public Response searchUser(@QueryParam("email") String email,
                               @QueryParam("msisdn") String msisdn,
                               @QueryParam("username") String username,
                               @QueryParam("firstName") String firstName,
                               @QueryParam("lastName") String lastName) {
        logger.info(format("Got request to search for user with details %s %s %s %s %s",
                email, msisdn, username, firstName, lastName));
        return status(200).entity(webRequestProcessor.searchUser(email, msisdn, username, firstName, lastName)).build();
    }

//	@PUT @Path("/user/{userId}")
//	public Response confirmRegistration(@PathParam("userId") Long userId,
//										@FormParam("authToken") String authToken,
//	                                    @FormParam("deviceId") String deviceId,
//	                                    @FormParam("username") String username,
//	                                    @FormParam("password") String password) {
//		logger.info(format("Got request to complete registration for user %s", username));
//		return status(200).entity(webRequestProcessor.confirmRegistration(userId, authToken,
//				deviceId, username, password, WEB)).build();
//	}

    @POST
    @Path("/session")
    public Response startSession(@FormParam("deviceId") String deviceId,
                                 @FormParam("username") String username,
                                 @FormParam("password") String password) {
        SymSystemUserList response = webRequestProcessor.startSession(deviceId, username, password, WEB);
        if (response.getSymResponse().getResponse_code().equals(SymResponseCode.GENERAL_ERROR.getCode())) {
            return status(500).build();
        } else {
            return status(200).entity(response).build();
        }
    }

    @PUT
    @Path("/session/{sessionId}")
    public Response endSession(@PathParam("sessionId") Long sessionId,
                               @FormParam("deviceId") String deviceId,
                               @FormParam("authToken") String authToken) {
        SymResponse response = webRequestProcessor.endSession(sessionId, deviceId, authToken, WEB);
        if (response.getResponse_code().equals(SymResponseCode.GENERAL_ERROR.getCode())) {
            return status(500).build();
        } else {
            return status(200).entity(response).build();
        }
    }
}
