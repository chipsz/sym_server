package net.beyondtelecom.gopay.bt_api.service;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

/***************************************************************************
 * *
 * Created:     21 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public interface BeyondTelecomRestService {

	@GET @Path("hello")
	default Response hello () { return Response.status(200).entity("Hello from server!").build(); }

	Response getResponseCode (Long responseCodeId);

	Response getLanguage (Long languageId);

	Response getChannel(Long channelId);

	Response getCountry(Long countryId);

	Response getGroup(Long groupId);

	Response getRole(Long roleId);

	Response getEventType(Long eventTypeId);

    Response getFinancialInstitution(Long institutionId);

    Response getFinancialInstitutions();

    Response getCurrency(Long currencyId);

    Response getCurrencies();

    Response getWallet(Long walletId);

    Response getWallets();

    Response registerUser(String email, String msisdn, String msisdn2, String username, String deviceId,
                          String firstName, String lastName, String dateOfBirth);

//	Response confirmRegistration(Long userId, String authToken, String deviceId, String username, String password);

    Response searchUser(String email, String msisdn, String username, String firstName, String lastName);

	Response startSession(String authToken, String username, String password);

	Response endSession(Long sessionId, String deviceId, String authToken);

	Response getResponseCodes();

	Response getSystemConfigs();
}
