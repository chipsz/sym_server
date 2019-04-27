package net.symbiosis.web_ui.validation.msisdn;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import net.symbiosis.web_ui.annotations.MsisdnConstraint;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static net.symbiosis.common.utilities.SymValidator.isValidTenDigitMsisdn;
import static net.symbiosis.core_lib.utilities.CommonUtilities.isNullOrEmpty;

/**
 * ConstraintValidator for @Msisdn
 */
public class MsisdnConstraintValidator implements ConstraintValidator<MsisdnConstraint, String> {

    public void initialize(MsisdnConstraint a) {
    }

    public boolean isValid(String value, ConstraintValidatorContext cvc) {
        return isNullOrEmpty(value) || isValidTenDigitMsisdn(value);
    }

}