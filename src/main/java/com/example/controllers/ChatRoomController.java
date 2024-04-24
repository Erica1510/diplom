package com.example.controllers;

import com.example.dto.ChatRoomDto;
import com.example.entities.MessageModel;
import com.example.entities.MessageType;
import com.example.services.ChatRoomService;
import com.example.services.MessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/room")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    private final SimpMessagingTemplate simpMessagingTemplate;
    private final MessageService messageService;

    @MessageMapping("/public-message")
    @SendTo("/chatroom/public")
    public MessageModel receivePublicMessage(@Payload MessageModel message) {
        if (message.getMessageType() == MessageType.MESSAGE) {
            messageService.savePublicMessage(message);
        }
        return message;
    }

    @MessageMapping("/private-message")
    public MessageModel receivePrivateMessage(@Payload MessageModel message) {
        if (message.getMessageType() == MessageType.MESSAGE) {
            messageService.savePrivateMessage(message);
        }
        simpMessagingTemplate.convertAndSendToUser(
                message.getReceiverId().toString(), "/private", message
        );
        return message;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ChatRoomDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(chatRoomService.findById(id));
    }

    @PostMapping()
    public ResponseEntity<ChatRoomDto> saveRoom(@RequestBody ChatRoomDto chatRoom) {
        return ResponseEntity.ok(chatRoomService.save(chatRoom));
    }

    @GetMapping("/all")
    public ResponseEntity<List<ChatRoomDto>> findAll() {
        return ResponseEntity.ok(chatRoomService.findAll());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        chatRoomService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<ChatRoomDto> updateRoom(@PathVariable Long id, @RequestBody ChatRoomDto updatedChatRoom) {
        ChatRoomDto updatedRoom = chatRoomService.update(id, updatedChatRoom);
        return ResponseEntity.ok(updatedRoom);
    }

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatRoomDto sendMessage(

            @Payload ChatRoomDto chatMessage
    ) {
        return chatMessage;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatRoomDto addUser(
            @Payload ChatRoomDto chatMessage,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Add username in web socket session
        headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
        return chatMessage;
    }

    @PatchMapping("/add-users/{chatRoomId}")
    public ResponseEntity<ChatRoomDto> addUserToChat(@RequestBody ChatRoomDto chatRoomDto, @PathVariable Long chatRoomId) {
        return ResponseEntity.ok(chatRoomService.addUserToChat(chatRoomDto, chatRoomId));
    }

}
