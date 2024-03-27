package com.example.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "configuration")
public class Configuration {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "config_id")
    private Long configId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private Status status;


}
