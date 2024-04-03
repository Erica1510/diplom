package com.example.validation.annotations;

import com.example.validation.validators.PasswordConfirmationValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {PasswordConfirmationValidator.class})
public @interface PasswordConfirmation {
    String message() default "{validation.password.confirmation}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
