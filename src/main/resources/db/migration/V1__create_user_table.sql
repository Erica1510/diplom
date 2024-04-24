CREATE TABLE users
(
    user_id          BIGINT AUTO_INCREMENT,
    first_name       VARCHAR(255),
    last_name        VARCHAR(255),
    email            VARCHAR(255),
    username         VARCHAR(255),
    status           VARCHAR(255),
    is_active_chat   boolean,
    password         VARCHAR(255),
    password_confirm VARCHAR(255),
    social_links     VARCHAR(255),
    role             ENUM ('USER', 'ADMIN'),
    CONSTRAINT PK_USER PRIMARY KEY (user_id)
);
