package net.symbiosis.web_ui.validation.address;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.symbiosis.web_ui.annotations.AddressConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.symbiosis.core_lib.utilities.SymValidator.isNullOrEmpty;
import static net.symbiosis.core_lib.utilities.SymValidator.isValidAddress;

/**
 * ConstraintValidator for @Address
 */
public class AddressConstraintValidator implements ConstraintValidator<AddressConstraint, String> {

    public void initialize(AddressConstraint a) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return isNullOrEmpty(value) || isValidAddress(value);
    }

}