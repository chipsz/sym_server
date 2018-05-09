package net.beyondtelecom.web_ui.validation.email;

import net.beyondtelecom.web_ui.annotations.EmailConstraint;
import net.beyondtelecom.web_ui.validation.ConstraintHelper;
import org.primefaces.validate.bean.ClientValidationConstraint;

import javax.validation.metadata.ConstraintDescriptor;
import java.util.Map;

/***************************************************************************
 *                                                                         *
 * Created:     11 / 02 / 2017                                             *
 * Author:      Tsungai Kaviya                                             *
 * Contact:     tsungai.kaviya@gmail.com                                   *
 *                                                                         *
 ***************************************************************************/

/**
 * ClientValidationConstraint for @EmailConstraint annotation
 */
public class EmailClientValidationConstraint implements ClientValidationConstraint{

    public static final String MESSAGE_METADATA = "data-p-email-msg";

    @Override
    public Map<String, Object> getMetadata(ConstraintDescriptor constraintDescriptor) {
        return ConstraintHelper.getMetadata(MESSAGE_METADATA, constraintDescriptor);
    }

    @Override
    public String getValidatorId() {
        return EmailConstraint.class.getSimpleName();
    }
}
