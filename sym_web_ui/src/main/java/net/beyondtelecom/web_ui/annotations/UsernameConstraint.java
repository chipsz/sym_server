package net.beyondtelecom.web_ui.annotations;

import net.beyondtelecom.web_ui.validation.username.UsernameClientValidationConstraint;
import net.beyondtelecom.web_ui.validation.username.UsernameConstraintValidator;
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
@Constraint(validatedBy=UsernameConstraintValidator.class)
@ClientConstraint(resolvedBy=UsernameClientValidationConstraint.class)
@Documented
public @interface UsernameConstraint {
    String message() default "Invalid username specified.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}