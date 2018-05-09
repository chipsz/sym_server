package net.beyondtelecom.gopay.bt_authentication.authentication;

import net.beyondtelecom.bt_core_lib.response.BTResponseObject;
import net.beyondtelecom.gopay.bt_persistence.entity.complex_type.bt_user;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_channel;
import net.beyondtelecom.gopay.bt_persistence.entity.enumeration.bt_country;

import static net.beyondtelecom.bt_core_lib.enumeration.BTChannel.fromString;
import static net.beyondtelecom.bt_core_lib.enumeration.BTResponseCode.*;
import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.*;
import static net.beyondtelecom.gopay.bt_persistence.helper.BTEnumHelper.countryFromString;

/***************************************************************************
 * *
 * Created:     30 / 12 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

class AuthenticationHelper {

    static boolean isPinChannel(bt_channel channel) {

        switch (fromString(channel.getName())) {

            //password channels
            case DESKTOP:
            case WEB: return false;

            //pin channels
            case POS_MACHINE:
            case POS_TILL:
            case USSD:
            case SMART_PHONE: return true;

            default: return true;
        }
    }

	static BTResponseObject<bt_user> validateMandatoryChannelData(bt_user userData, bt_channel channel) {

		if (channel == null) {
			return new BTResponseObject<bt_user>(GENERAL_ERROR).setMessage("Invalid channel specified");
		} else if (userData == null) {
			return new BTResponseObject<bt_user>(INPUT_INVALID_REQUEST).setMessage("Invalid user data (null) specified");
		} else if (userData.getCountry() == null || !isValidName(userData.getCountry().getName())) {
			return new BTResponseObject<bt_user>(INPUT_INVALID_REQUEST).setMessage("Invalid country specified");
		} else if (userData.getLanguage() == null || !isValidName(userData.getLanguage().getName())) {
			return new BTResponseObject<bt_user>(INPUT_INVALID_REQUEST).setMessage("Invalid language specified");
		}

		bt_country country = countryFromString(userData.getCountry().getName());
		if (country == null) {
			return new BTResponseObject<bt_user>(NOT_SUPPORTED).setMessage("Invalid country specified");
		}

		if (userData.getLanguage() == null || !isValidName(userData.getLanguage().getName())) {
			return new BTResponseObject<bt_user>(NOT_SUPPORTED).setMessage("Invalid language specified");
		}

		switch (fromString(channel.getName())) {
			case WEB:
				if (!isValidEmail(userData.getEmail())) {
					return new BTResponseObject<bt_user>(INPUT_INVALID_EMAIL).setMessage("Invalid email (" + userData.getEmail() + ") specified");
				} else if (!isValidTenDigitMsisdn(userData.getMsisdn()) && !isValidMsisdn(userData.getMsisdn(), country.getDialing_code())) {
					return new BTResponseObject<bt_user>(INPUT_INVALID_MSISDN).setMessage("Invalid phone number (" + userData.getMsisdn() + ") specified");
				}
				// NO BREAK! YOU MUST ADDITIONALLY VALIDATE FIELDS IN NEXT CASE
            case SMART_PHONE:
				if (!isValidUsername(userData.getUsername())) {
					return new BTResponseObject<bt_user>(INPUT_INVALID_USERNAME).setMessage("Invalid username (" + userData.getUsername() + ") specified");
				} else if (!isValidName(userData.getFirst_name())) {
					return new BTResponseObject<bt_user>(INPUT_INVALID_FIRST_NAME).setMessage("Invalid first name (" + userData.getFirst_name() + ") specified");
				} else if (!isValidName(userData.getLast_name())) {
					return new BTResponseObject<bt_user>(INPUT_INVALID_LAST_NAME).setMessage("Invalid last name (" + userData.getLast_name() + ") specified");
				} else if (!isValidMsisdn(userData.getMsisdn())) {
					return new BTResponseObject<bt_user>(INPUT_INVALID_MSISDN).setMessage("Invalid phone number (" + userData.getMsisdn() + ") specified");
				} else if (!isValidPin(userData.getPin())) {
					return new BTResponseObject<bt_user>(INPUT_INVALID_PASSWORD).setMessage("Invalid pin number (" + userData.getPin() + ") specified");
				} else return new BTResponseObject<bt_user>(SUCCESS).setResponseObject(userData);
			default:
				return new BTResponseObject<bt_user>(NOT_SUPPORTED).setMessage("Channel not supported: " + channel.getName());
		}
	}

}
