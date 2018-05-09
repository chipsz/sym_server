package net.symbiosis.api.service;

import javax.ws.rs.core.Response;

/***************************************************************************
 * Created:     9/5/2018                                                  *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     kaviyat@payserv.co.zw                                      *
 **************************************************************************/

public interface WebRestService {

    Response registerUser(String email, String msisdn, String msisdn2, String username, String deviceId,
                          String firstName, String lastName, String dateOfBirth);

//	Response confirmRegistration(Long userId, String authToken, String deviceId, String username, String password);

    Response searchUser(String email, String msisdn, String username, String firstName, String lastName);

    Response startSession(String authToken, String username, String password);

    Response endSession(Long sessionId, String deviceId, String authToken);
}
