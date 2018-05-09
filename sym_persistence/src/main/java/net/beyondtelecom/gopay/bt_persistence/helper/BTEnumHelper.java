package net.beyondtelecom.gopay.bt_persistence.helper;

import net.beyondtelecom.bt_core_lib.enumeration.*;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.*;

import static net.beyondtelecom.gopay.bt_persistence.dao.EnumEntityRepoManager.findByName;

/***************************************************************************
 * *
 * Created:     18 / 10 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

public class BTEnumHelper {

    public static bt_country countryFromString(String name) {
        return findByName(bt_country.class, name);
    }

    public static bt_language languageFromString(String name) {
        return findByName(bt_language.class, name);
    }

    public static bt_auth_group fromEnum(BTAuthGroup authGroup) {
        return findByName(bt_auth_group.class, authGroup.name());
    }

    public static bt_channel fromEnum(BTChannel channel) {
        return findByName(bt_channel.class, channel.name());
    }

    public static bt_event_type fromEnum(BTEventType eventType) {
        return findByName(bt_event_type.class, eventType.name());
    }

    public static bt_financial_institution_type fromEnum(BTFinancialInstitutionType financialInstitutionType) {
        return findByName(bt_financial_institution_type.class, financialInstitutionType.name());
    }

    public static bt_response_code fromEnum(BTResponseCode responseCode) {
        return findByName(bt_response_code.class, responseCode.name());
    }

    public static bt_preference fromEnum(BTPreference preference) {
        return findByName(bt_preference.class, preference.name());
    }

}
