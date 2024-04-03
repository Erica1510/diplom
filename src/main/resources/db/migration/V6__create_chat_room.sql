CREATE TABLE chat_room
(
    room_id      BIGINT AUTO_INCREMENT,
    name    VARCHAR(255),
    user_id BIGINT,
    CONSTRAINT PK_ROOM PRIMARY KEY (room_id),
    CONSTRAINT FK_ROOM_USER FOREIGN KEY (user_id) REFERENCES users(user_id)
);