package net.beyondtelecom.web_ui.validation.name;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.beyondtelecom.web_ui.annotations.NameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.isNullOrEmpty;
import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.isValidName;

/**
 * ConstraintValidator for @Name
 */
public class NameConstraintValidator implements ConstraintValidator<NameConstraint, String> {

    public void initialize(NameConstraint a) {}

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return isNullOrEmpty(value) || isValidName(value);
    }
}