package net.symbiosis.authentication.authentication;

import net.symbiosis.core_lib.response.SymResponseObject;
import net.symbiosis.persistence.entity.complex_type.sym_user;
import net.symbiosis.persistence.entity.enumeration.sym_channel;
import net.symbiosis.persistence.entity.enumeration.sym_country;

import static net.symbiosis.common.utilities.SymValidator.*;
import static net.symbiosis.core_lib.enumeration.SymChannel.fromString;
import static net.symbiosis.core_lib.enumeration.SymResponseCode.*;
import static net.symbiosis.persistence.helper.SymEnumHelper.countryFromString;

/***************************************************************************
 * *
 * Created:     30 / 12 / 2016                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 * *
 ***************************************************************************/

class AuthenticationHelper {

    static boolean isPinChannel(sym_channel channel) {

        switch (fromString(channel.getName())) {

            //password channels
            case DESKTOP:
            case WEB:
                return false;

            //pin channels
            case POS_MACHINE:
            case POS_TILL:
            case USSD:
            case SMART_PHONE:
                return true;

            default:
                return true;
        }
    }

    static SymResponseObject<sym_user> validateMandatoryChannelData(sym_user userData, sym_channel channel) {

        if (channel == null) {
            return new SymResponseObject<sym_user>(GENERAL_ERROR).setMessage("Invalid channel specified");
        } else if (userData == null) {
            return new SymResponseObject<sym_user>(INPUT_INVALID_REQUEST).setMessage("Invalid user data (null) specified");
        } else if (userData.getCountry() == null || !isValidName(userData.getCountry().getName())) {
            return new SymResponseObject<sym_user>(INPUT_INVALID_REQUEST).setMessage("Invalid country specified");
        } else if (userData.getLanguage() == null || !isValidName(userData.getLanguage().getName())) {
            return new SymResponseObject<sym_user>(INPUT_INVALID_REQUEST).setMessage("Invalid language specified");
        }

        sym_country country = countryFromString(userData.getCountry().getName());
        if (country == null) {
            return new SymResponseObject<sym_user>(NOT_SUPPORTED).setMessage("Invalid country specified");
        } else if (userData.getLanguage() == null || !isValidName(userData.getLanguage().getName())) {
            return new SymResponseObject<sym_user>(NOT_SUPPORTED).setMessage("Invalid language specified");
        } else if (!isValidUsername(userData.getUsername())) {
		    return new SymResponseObject<sym_user>(INPUT_INVALID_USERNAME).setMessage("Invalid username (" + userData.getUsername() + ") specified");
	    } else if (!isValidName(userData.getFirst_name())) {
		    return new SymResponseObject<sym_user>(INPUT_INVALID_FIRST_NAME).setMessage("Invalid first name (" + userData.getFirst_name() + ") specified");
	    } else if (!isValidName(userData.getLast_name())) {
		    return new SymResponseObject<sym_user>(INPUT_INVALID_LAST_NAME).setMessage("Invalid last name (" + userData.getLast_name() + ") specified");
	    }  else if (!isValidTenDigitMsisdn(userData.getMsisdn()) && !isValidMsisdn(userData.getMsisdn(), country.getDialing_code())) {
		    return new SymResponseObject<sym_user>(INPUT_INVALID_MSISDN).setMessage("Invalid phone number (" + userData.getMsisdn() + ") specified");
	    }

	    switch (fromString(channel.getName())) {
            case WEB: {
	            if (!isValidEmail(userData.getEmail())) {
		            return new SymResponseObject<sym_user>(INPUT_INVALID_EMAIL).setMessage("Invalid email (" + userData.getEmail() + ") specified");
	            } else if (!isValidPassword(userData.getPassword())) {
		            return new SymResponseObject<sym_user>(INPUT_INVALID_PASSWORD).setMessage("Invalid password specified");
	            } else return new SymResponseObject<sym_user>(SUCCESS).setResponseObject(userData);
            }
	        case SMART_PHONE: {
		        if (!isValidPin(userData.getPin())) {
			        return new SymResponseObject<sym_user>(INPUT_INVALID_PASSWORD).setMessage("Invalid pin number specified");
		        } else return new SymResponseObject<sym_user>(SUCCESS).setResponseObject(userData);
	        }
            default:
                return new SymResponseObject<sym_user>(NOT_SUPPORTED).setMessage("Channel not supported: " + channel.getName());
        }
    }

}
