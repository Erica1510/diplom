package com.example.validation.validators;

import com.example.entities.ValidationRuleEnum;
import com.example.services.ValidationRuleService;
import com.example.validation.PatternValidator;
import com.example.validation.annotations.IsActive;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class IsActiveValidator implements ConstraintValidator<IsActive, String> {

    private final ValidationRuleService validationRuleService;

    @Override
    public boolean isValid(String valueToValid, ConstraintValidatorContext constraintValidatorContext) {
        return PatternValidator.isValid(valueToValid, validationRuleService.findByRuleName(ValidationRuleEnum.IS_ACTIVE_CHAT));
    }
}
