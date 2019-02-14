package net.symbiosis.web_ui.common;


import net.symbiosis.persistence.entity.complex_type.log.sym_request_response_log;
import net.symbiosis.persistence.entity.complex_type.sym_auth_user;
import net.symbiosis.persistence.entity.enumeration.sym_channel;
import net.symbiosis.persistence.entity.enumeration.sym_event_type;
import net.symbiosis.persistence.entity.enumeration.sym_response_code;

import java.util.Date;
import java.util.logging.Logger;

import static java.lang.String.format;
import static net.symbiosis.core_lib.enumeration.SymChannel.WEB;
import static net.symbiosis.persistence.helper.SymEnumHelper.fromEnum;

/***************************************************************************
 *                                                                         *
 * Created:     01 / 05 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public interface JSFLoggable {
    Logger logger = Logger.getLogger(JSFLoggable.class.getSimpleName());
    sym_channel LOG_CHANNEL = fromEnum(WEB);

    default void log(sym_event_type eventType, sym_auth_user user, sym_response_code responseCode,
                     Date requestTime, Date responseTime, String incomingRequest, String outgoingResponse) {
        logger.info(format("Logging event type %s by user %s: [%s]",
                eventType.getName(), user.getUser().getUsername(), incomingRequest)
        );
        new sym_request_response_log(
                LOG_CHANNEL, eventType, user.getUser(), user, responseCode, requestTime, responseTime,
                incomingRequest, outgoingResponse).save();
    }
}
