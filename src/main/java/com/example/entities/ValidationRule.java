package com.example.entities;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "validation_rules", schema = "diplom")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ValidationRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "rule_id")
    private Long ruleId;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private ValidationRuleEnum name;

    @Column(name = "rule", nullable = false)
    private String rule;

    @Column(name = "creation_time", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationTime;
}
