package com.bol.mancalagame.controller.validator.annotation;

import com.bol.mancalagame.controller.validator.PlayRequestValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = PlayRequestValidator.class)
@Documented
public @interface PlayValidator {
    String message() default "{PlayValidator.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
