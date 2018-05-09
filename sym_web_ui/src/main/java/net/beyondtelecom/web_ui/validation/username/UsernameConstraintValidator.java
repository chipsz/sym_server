package net.beyondtelecom.web_ui.validation.username;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.beyondtelecom.web_ui.annotations.UsernameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.isValidUsername;

/**
 * ConstraintValidator for @Email
 */
public class UsernameConstraintValidator implements ConstraintValidator<UsernameConstraint, String> {

    public void initialize(UsernameConstraint a) {}

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return value == null || isValidUsername(value);
    }

}