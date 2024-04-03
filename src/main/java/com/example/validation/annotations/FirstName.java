package com.example.validation.annotations;

import com.example.validation.validators.FirstNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {FirstNameValidator.class})
public @interface FirstName {
    String message() default "{validation.firstname}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
