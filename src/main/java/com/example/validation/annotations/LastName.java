package com.example.validation.annotations;

import com.example.validation.validators.LastNameValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {LastNameValidator.class})
public @interface LastName {
    String message() default "{validation.lastname}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
