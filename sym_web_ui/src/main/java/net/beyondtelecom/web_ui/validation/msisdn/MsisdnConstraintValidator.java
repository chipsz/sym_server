package net.beyondtelecom.web_ui.validation.msisdn;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.beyondtelecom.web_ui.annotations.MsisdnConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.isNullOrEmpty;
import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.isValidTenDigitMsisdn;

/**
 * ConstraintValidator for @Msisdn
 */
public class MsisdnConstraintValidator implements ConstraintValidator<MsisdnConstraint, String> {

    public void initialize(MsisdnConstraint a) {}

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return isNullOrEmpty(value) || isValidTenDigitMsisdn(value);
    }

}