-- Миграция для PublicMessage
CREATE TABLE public_messages
(
    public_id BIGINT AUTO_INCREMENT,
    sender_id BIGINT,
    message   VARCHAR(255),
    CONSTRAINT PK_PUBLIC_MESSAGE PRIMARY KEY (public_id),
    CONSTRAINT FK_PUBLIC_MESSAGE_SENDER FOREIGN KEY (sender_id) REFERENCES users (user_id)
);

