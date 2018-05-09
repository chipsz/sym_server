package net.symbiosis.web_ui.validation.name;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.symbiosis.web_ui.annotations.NameConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.symbiosis.core_lib.utilities.SymValidator.isNullOrEmpty;
import static net.symbiosis.core_lib.utilities.SymValidator.isValidName;

/**
 * ConstraintValidator for @Name
 */
public class NameConstraintValidator implements ConstraintValidator<NameConstraint, String> {

    public void initialize(NameConstraint a) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return isNullOrEmpty(value) || isValidName(value);
    }
}