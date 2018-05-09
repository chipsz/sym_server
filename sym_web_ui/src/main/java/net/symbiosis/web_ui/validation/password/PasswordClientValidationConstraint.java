package net.symbiosis.web_ui.validation.password;

import net.symbiosis.web_ui.annotations.PasswordConstraint;
import net.symbiosis.web_ui.validation.ConstraintHelper;
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
 * ClientValidationConstraint for @PasswordConstraint annotation
 */
public class PasswordClientValidationConstraint implements ClientValidationConstraint {

    public static final String MESSAGE_METADATA = "data-p-password-msg";

    @Override
    public Map<String, Object> getMetadata(ConstraintDescriptor constraintDescriptor) {
        return ConstraintHelper.getMetadata(MESSAGE_METADATA, constraintDescriptor);
    }

    @Override
    public String getValidatorId() {
        return PasswordConstraint.class.getSimpleName();
    }
}
