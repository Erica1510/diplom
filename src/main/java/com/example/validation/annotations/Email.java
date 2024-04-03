package com.example.validation.annotations;


import com.example.validation.validators.EmailValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {EmailValidator.class})
public @interface Email {
    String message() default "{validation.email}";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
