package com.example.services.serviceImpl;

import com.example.entities.ValidationRuleEnum;
import com.example.repositories.ValidationRuleRepository;
import com.example.services.ValidationRuleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CacheableValidationService implements ValidationRuleService {

    private final ValidationRuleRepository ruleRepository;

    @Override
    @Cacheable(cacheNames = "validationRules", key = "#ruleName")
    public String findByRuleName(ValidationRuleEnum ruleName) {
        log.info(ruleName + " validation rule not from cache");
        return ruleRepository.findByName(ruleName).getRule();
    }

    @Scheduled(cron = "@daily")
    @CacheEvict(cacheNames = "validationRules", allEntries = true)
    public void validationRulesCacheEvict() {
        log.info("Evicted cache for validation rules");
    }
}
