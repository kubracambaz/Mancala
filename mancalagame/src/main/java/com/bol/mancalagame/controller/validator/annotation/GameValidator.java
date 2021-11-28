package com.bol.mancalagame.controller.validator.annotation;

import com.bol.mancalagame.controller.validator.GameRequestValidator;
import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
@Retention(RUNTIME)
@Constraint(validatedBy = GameRequestValidator.class)
@Documented
public @interface GameValidator {
    String message() default "{GameValidator.invalid}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
