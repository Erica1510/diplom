CREATE TABLE message_model
(
    message_id   BIGINT AUTO_INCREMENT,
    sender_id    BIGINT,
    sender_name  VARCHAR(255),
    receiver_id  BIGINT,
    message      VARCHAR(255),
    message_type ENUM ('JOIN', 'MESSAGE','LEAVE'),
    CONSTRAINT PK_MESSAGES PRIMARY KEY (message_id)

);


