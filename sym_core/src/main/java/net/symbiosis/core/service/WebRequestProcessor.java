package net.symbiosis.core.service;

import net.symbiosis.core.contract.SymResponse;
import net.symbiosis.core.contract.SymSystemUserList;
import net.symbiosis.core_lib.enumeration.SymChannel;

/***************************************************************************
 * Created:     9/5/2018                                                  *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     kaviyat@payserv.co.zw                                      *
 **************************************************************************/

public interface WebRequestProcessor {
    SymSystemUserList registerWebUser(String email, String msisdn, String msisdn2, String username, String deviceId,
                                      String firstName, String lastName, String dateOfBirth);

    //	SymSystemUserList confirmRegistration(Long userId, String authToken, String deviceId, String username,
//                                         String password, SymChannel channel);
    SymSystemUserList searchUser(String email, String msisdn, String username, String firstName, String lastName);

    SymSystemUserList startSession(String deviceId, String username, String password, SymChannel channel);

    SymResponse endSession(Long sessionId, String deviceId, String authToken, SymChannel channel);
}
