package com.example.services;


import com.example.entities.MessageModel;

import java.util.List;

public interface MessageService {
    void savePublicMessage(MessageModel message);

    List<MessageModel> getAllChatRoomMessages();

    void savePrivateMessage(MessageModel message);

    List<MessageModel> getUserPrivateMessages(Long userId);
}
