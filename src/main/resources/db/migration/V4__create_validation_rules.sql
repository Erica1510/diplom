CREATE TABLE validation_rules
(
    rule_id       BIGINT PRIMARY KEY AUTO_INCREMENT,
    name          VARCHAR(50)                         NOT NULL,
    rule          VARCHAR(100)                        NOT NULL,
    creation_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL
);
