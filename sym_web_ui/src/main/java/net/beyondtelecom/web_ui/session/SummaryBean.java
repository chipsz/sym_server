package net.beyondtelecom.web_ui.session;

import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_user;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Calendar;
import java.util.logging.Logger;

import static java.util.Calendar.*;
import static net.beyondtelecom.gopay.bt_common.utilities.SymTransformer.dateToString;
import static net.beyondtelecom.gopay.bt_persistence.helper.DaoManager.getEntityManagerRepo;

/***************************************************************************
 *                                                                         *
 * Created:     13 / 03 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

@Component
@Scope("session")
public class SummaryBean {

    private static final Logger logger = Logger.getLogger(SummaryBean.class.getSimpleName());

    private String todayStartTime = null;

    private String getTodayStartTime() {
        if (todayStartTime == null) {
            Calendar date = Calendar.getInstance();
            date.set(HOUR_OF_DAY, 0);
            date.set(MINUTE, 0);
            date.set(SECOND, 0);
            date.set(MILLISECOND, 0);
            logger.info("Using start time as " + date.getTime());
            todayStartTime = dateToString(date.getTime());
        }
        return todayStartTime;
    }

    public Long getSystemErrorsToday() {
        return 0L;
    }

    public Long getTotalUsers() {
        return getEntityManagerRepo().countAll(bt_user.class);
    }
}
