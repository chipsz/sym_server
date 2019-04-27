package net.symbiosis.web_ui.validation.full_msisdn;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.symbiosis.web_ui.annotations.FullMsisdnConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.symbiosis.common.utilities.SymValidator.isValidFullMsisdn;
import static net.symbiosis.core_lib.utilities.CommonUtilities.isNullOrEmpty;

/**
 * ConstraintValidator for @FullMsisdn
 */
public class FullMsisdnConstraintValidator implements ConstraintValidator<FullMsisdnConstraint, String> {

    public void initialize(FullMsisdnConstraint a) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return isNullOrEmpty(value) || isValidFullMsisdn(value);
    }

}