package net.beyondtelecom.web_ui.validation.plaintext;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.beyondtelecom.web_ui.annotations.PlainTextConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.isNullOrEmpty;
import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.isValidPlainText;

/**
 * ConstraintValidator for @PlainText
 */
public class PlainTextConstraintValidator implements ConstraintValidator<PlainTextConstraint, String> {

    public void initialize(PlainTextConstraint a) {}

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return isNullOrEmpty(value) || isValidPlainText(value);
    }

}