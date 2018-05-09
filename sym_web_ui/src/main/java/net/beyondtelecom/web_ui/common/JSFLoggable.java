package net.beyondtelecom.web_ui.common;


import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_auth_user;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.log.bt_request_response_log;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_channel;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_event_type;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_response_code;

import java.util.Date;
import java.util.logging.Logger;

import static java.lang.String.format;
import static net.beyondtelecom.bt_core_lib.enumeration.BTChannel.WEB;
import static net.beyondtelecom.gopay.bt_persistence.helper.BTEnumHelper.fromEnum;

/***************************************************************************
 *                                                                         *
 * Created:     01 / 05 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

public interface JSFLoggable {
    Logger logger = Logger.getLogger(JSFLoggable.class.getSimpleName());
    bt_channel LOG_CHANNEL = fromEnum(WEB);

    default void log(bt_event_type eventType, bt_auth_user user, bt_response_code responseCode,
                     Date requestTime, Date responseTime, String incomingRequest, String outgoingResponse) {
        logger.info(format("Logging event type %s by user %s: [%s]",
            eventType.getName(), user.getUser().getUsername(), incomingRequest)
        );
        new bt_request_response_log(
            LOG_CHANNEL, eventType, user.getUser(), user, responseCode, requestTime, responseTime,
            incomingRequest, outgoingResponse).save();
    }
}
