package net.beyondtelecom.web_ui.validation.full_msisdn;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

import org.primefaces.validate.ClientValidator;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;
import java.util.Map;

import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static net.beyondtelecom.bt_core_lib.utilities.BTValidator.isValidFullMsisdn;
import static net.beyondtelecom.gopay.bt_common.configuration.Configuration.getCountryCodePrefix;

/**
 * Custom JSF Validator for @FullMsisdn input
 */
@FacesValidator("custom.fullMsisdnValidator")
public class FullMsisdnValidator implements Validator, ClientValidator {

    public static final String MSISDN_VALIDATION_ERROR =
            "Invalid phone number: Must be in the format " + getCountryCodePrefix() + "123456789";

    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        if(value == null || value.toString().equals("")) { return; }
        if(!isValidFullMsisdn(value.toString())) {
            throw new ValidatorException(new FacesMessage(SEVERITY_ERROR, null, MSISDN_VALIDATION_ERROR));
        }
    }

    public Map<String, Object> getMetadata() { return null; }

    public String getValidatorId() { return "custom.fullMsisdnValidator"; }

}