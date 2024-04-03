package com.example.validation.validators;

import com.example.entities.ValidationRuleEnum;
import com.example.services.ValidationRuleService;
import com.example.validation.PatternValidator;
import com.example.validation.annotations.LastName;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class LastNameValidator implements ConstraintValidator<LastName, String> {

    private final ValidationRuleService validationRuleService;

    @Override
    public boolean isValid(String valueToValid, ConstraintValidatorContext constraintValidatorContext) {
        return PatternValidator.isValid(valueToValid, validationRuleService.findByRuleName(ValidationRuleEnum.LAST_NAME));
    }
}
