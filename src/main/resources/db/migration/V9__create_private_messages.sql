CREATE TABLE private_messages
(
    private_id  BIGINT AUTO_INCREMENT,
    sender_id   BIGINT,
    receiver_id BIGINT,
    message     VARCHAR(255),
    CONSTRAINT PK_PRIVATE_MESSAGE PRIMARY KEY (private_id),
    CONSTRAINT FK_PRIVATE_MESSAGE_SENDER FOREIGN KEY (sender_id) REFERENCES users (user_id),
    CONSTRAINT FK_PRIVATE_MESSAGE_RECEIVER FOREIGN KEY (receiver_id) REFERENCES users (user_id)
);
