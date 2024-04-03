package com.example.services;


import com.example.entities.ValidationRuleEnum;

public interface ValidationRuleService {
    String findByRuleName(ValidationRuleEnum ruleName);
}
