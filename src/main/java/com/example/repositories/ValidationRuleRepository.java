package com.example.repositories;


import com.example.entities.ValidationRule;
import com.example.entities.ValidationRuleEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ValidationRuleRepository extends JpaRepository<ValidationRule, Long> {
    ValidationRule findByName(ValidationRuleEnum name);
}
