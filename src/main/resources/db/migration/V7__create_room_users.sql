CREATE TABLE room_users
(
    user_id BIGINT NOT NULL REFERENCES users,
    room_id BIGINT NOT NULL REFERENCES chat_room,

    FOREIGN KEY (user_id) REFERENCES users (user_id),
    FOREIGN KEY (room_id) REFERENCES chat_room (room_id)
);




