package net.symbiosis.core.service;

import net.symbiosis.core_lib.enumeration.SymResponseCode;
import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.enumeration.sym_response_code;
import net.symbiosis.persistence.helper.SymEnumHelper;

import java.util.Date;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 05 / 2018                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public interface RequestProcessor {

    default void logResponse(sym_request_response_log requestResponseLog, sym_response_code responseCode, String responseMessage) {
        requestResponseLog.setOutgoing_response(responseMessage);
        requestResponseLog.setOutgoing_response_time(new Date());
        requestResponseLog.setResponse_code(responseCode);
        requestResponseLog.save();
    }

    default void logResponse(sym_request_response_log requestResponseLog, sym_response_code responseCode) {
        logResponse(requestResponseLog, responseCode, responseCode.getResponse_message());
    }

    default void logResponse(sym_request_response_log requestResponseLog, SymResponseCode responseCode, String responseMessage) {
        logResponse(requestResponseLog, SymEnumHelper.fromEnum(responseCode));
    }

    default void logResponse(sym_request_response_log requestResponseLog, SymResponseCode responseCode) {
        logResponse(requestResponseLog, SymEnumHelper.fromEnum(responseCode));
    }
}
