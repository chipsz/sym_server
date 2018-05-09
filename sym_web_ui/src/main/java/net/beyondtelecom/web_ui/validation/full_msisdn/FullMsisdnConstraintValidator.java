package net.beyondtelecom.web_ui.validation.full_msisdn;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.beyondtelecom.web_ui.annotations.FullMsisdnConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.isNullOrEmpty;
import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.isValidFullMsisdn;

/**
 * ConstraintValidator for @FullMsisdn
 */
public class FullMsisdnConstraintValidator implements ConstraintValidator<FullMsisdnConstraint, String> {

    public void initialize(FullMsisdnConstraint a) {}

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return isNullOrEmpty(value) || isValidFullMsisdn(value);
    }

}