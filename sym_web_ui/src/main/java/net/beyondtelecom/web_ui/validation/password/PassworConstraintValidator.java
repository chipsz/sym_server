package net.beyondtelecom.web_ui.validation.password;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.beyondtelecom.web_ui.annotations.PasswordConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.isValidPassword;

/**
 * ConstraintValidator for @Name
 */
public class PassworConstraintValidator implements ConstraintValidator<PasswordConstraint, String> {

    public void initialize(PasswordConstraint a) {}

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return value == null || isValidPassword(value);
    }

}