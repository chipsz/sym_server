package net.beyondtelecom.web_ui.annotations;

import net.beyondtelecom.web_ui.validation.password.PassworConstraintValidator;
import net.beyondtelecom.web_ui.validation.password.PasswordClientValidationConstraint;
import org.primefaces.validate.bean.ClientConstraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({METHOD,FIELD,ANNOTATION_TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy= PassworConstraintValidator.class)
@ClientConstraint(resolvedBy=PasswordClientValidationConstraint.class)
@Documented
public @interface PasswordConstraint {
    String message() default "Invalid password specified";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}