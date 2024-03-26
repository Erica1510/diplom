CREATE TABLE IF NOT EXISTS configuration (
                                             config_id BIGINT NOT NULL AUTO_INCREMENT,
                                             status ENUM('ACTIVE', 'INACTIVE') NOT NULL,
    PRIMARY KEY (config_id)
    );
