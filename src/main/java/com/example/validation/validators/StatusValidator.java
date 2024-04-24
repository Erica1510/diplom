package com.example.validation.validators;

import com.example.entities.ValidationRuleEnum;
import com.example.services.ValidationRuleService;
import com.example.validation.PatternValidator;
import com.example.validation.annotations.Status;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class StatusValidator implements ConstraintValidator<Status, String> {

    private final ValidationRuleService validationRuleService;

    @Override
    public boolean isValid(String valueToValid, ConstraintValidatorContext constraintValidatorContext) {
        return PatternValidator.isValid(valueToValid, validationRuleService.findByRuleName(ValidationRuleEnum.STATUS));
    }
}
