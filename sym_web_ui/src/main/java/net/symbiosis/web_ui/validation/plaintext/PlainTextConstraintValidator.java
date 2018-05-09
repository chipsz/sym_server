package net.symbiosis.web_ui.validation.plaintext;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.symbiosis.web_ui.annotations.PlainTextConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.symbiosis.core_lib.utilities.SymValidator.isNullOrEmpty;
import static net.symbiosis.core_lib.utilities.SymValidator.isValidPlainText;

/**
 * ConstraintValidator for @PlainText
 */
public class PlainTextConstraintValidator implements ConstraintValidator<PlainTextConstraint, String> {

    public void initialize(PlainTextConstraint a) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return isNullOrEmpty(value) || isValidPlainText(value);
    }

}