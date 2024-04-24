package com.example.services.serviceImpl;

import com.example.entities.*;
import com.example.repositories.PrivateMessageRepository;
import com.example.repositories.PublicMessageRepository;
import com.example.repositories.UserRepository;
import com.example.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MessageServiceImpl implements MessageService {

    private final PublicMessageRepository publicMessageRepository;
    private final PrivateMessageRepository privateMessageRepository;
    private final UserRepository userRepository;
    private final SimpMessageSendingOperations messagingTemplate;

    @Autowired
    public MessageServiceImpl(PublicMessageRepository publicMessageRepository,
                              PrivateMessageRepository privateMessageRepository,
                              UserRepository userRepository, SimpMessageSendingOperations messagingTemplate) {
        this.publicMessageRepository = publicMessageRepository;
        this.privateMessageRepository = privateMessageRepository;
        this.userRepository = userRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void savePublicMessage(MessageModel message) {
        Optional<User> sender = userRepository.findById(message.getSenderId());
        if (sender.isEmpty()) {
            throw new RuntimeException("Forbidden");
        }
        PublicMessage publicMessage = new PublicMessage(
                sender.get(),
                message.getMessage()
        );
        publicMessageRepository.save(publicMessage);
        // Publish the message to WebSocket topic
        messagingTemplate.convertAndSend("/topic/public", message);
    }
    @Override
    public List<MessageModel> getAllChatRoomMessages() {
        List<PublicMessage> publicMessages = publicMessageRepository.findAll();
        List<MessageModel> messages = new ArrayList<>();
        publicMessages.forEach(publicMessage -> {
            MessageModel messageModel = new MessageModel();
            messageModel.setSenderId(publicMessage.getSender().getUserId());
            messageModel.setSenderName(publicMessage.getSender().getUsername());
            messageModel.setReceiverId(0L);
            messageModel.setMessage(publicMessage.getMessage());
            messageModel.setMessageType(MessageType.MESSAGE);
            messages.add(messageModel);
        });
        return messages;
    }

    @Override
    public void savePrivateMessage(MessageModel message) {
        Optional<User> sender = userRepository.findById(message.getSenderId());
        Optional<User> receiver = userRepository.findById(message.getReceiverId());
        if (sender.isEmpty() || receiver.isEmpty()) {
            throw new RuntimeException("Forbidden");
        }
        PrivateMessage privateMessage = new PrivateMessage();
        privateMessage.setSender(sender.get());
        privateMessage.setReceiver(receiver.get());
        privateMessage.setMessage(message.getMessage());
        privateMessageRepository.save(privateMessage);
    }

    @Override
    public List<MessageModel> getUserPrivateMessages(Long userId) {
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()) {
            throw new RuntimeException("Forbidden");
        }
        List<PrivateMessage> privateMessages = privateMessageRepository
                .findBySenderOrReceiver(user.get(), user.get());
        List<MessageModel> messageModelList = new ArrayList<>();
        privateMessages.forEach(privateMessage -> {
            MessageModel messageModel = new MessageModel();
            messageModel.setSenderId(privateMessage.getSender().getUserId());
            messageModel.setSenderName(privateMessage.getSender().getUsername());
            messageModel.setReceiverId(privateMessage.getReceiver().getUserId());
            messageModel.setMessage(privateMessage.getMessage());
            messageModel.setMessageType(MessageType.MESSAGE);
            messageModelList.add(messageModel);
        });
        return messageModelList;
    }

    private String getUserInitial(String username) {
        String[] names = username.split(" ");
        if (names.length == 1) {
            return String.valueOf(names[0].charAt(0)).toUpperCase();
        }
        return (names[0].charAt(0)
                + String.valueOf(names[1].charAt(0))).toUpperCase();
    }
}
