package net.symbiosis.core.service;

import net.symbiosis.core_lib.enumeration.SymResponseCode;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.enumeration.sym_response_code;

import java.util.Date;

import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 05 / 2018                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public interface RequestProcessor {

    default void logResponse(sym_auth_user authUser, sym_request_response_log requestResponseLog, sym_response_code responseCode, String responseMessage) {
        if (authUser != null) {
            requestResponseLog.setAuth_user(authUser);
            requestResponseLog.setSystem_user(authUser.getUser());
        }
        requestResponseLog.setOutgoing_response(responseMessage);
        requestResponseLog.setOutgoing_response_time(new Date());
        requestResponseLog.setResponse_code(responseCode);
        requestResponseLog.save();
    }

    default void logResponse(sym_auth_user authUser, sym_request_response_log requestResponseLog, sym_response_code responseCode) {
        logResponse(authUser, requestResponseLog, responseCode, responseCode.getResponse_message());
    }

    default void logResponse(sym_auth_user authUser, sym_request_response_log requestResponseLog, SymResponseCode responseCode, String responseMessage) {
        logResponse(authUser, requestResponseLog, fromEnum(responseCode), responseMessage);
    }

    default void logResponse(sym_auth_user authUser, sym_request_response_log requestResponseLog, SymResponseCode responseCode) {
        logResponse(authUser, requestResponseLog, fromEnum(responseCode));
    }
}
