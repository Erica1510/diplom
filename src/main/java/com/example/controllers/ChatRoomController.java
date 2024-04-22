package com.example.controllers;

import com.example.dto.ChatRoomDto;
import com.example.dto.UserDto;
import com.example.entities.ChatRoom;
import com.example.services.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/v1/room")
@RequiredArgsConstructor
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    @GetMapping("/{id}")
    public ResponseEntity<Optional<ChatRoomDto>> findById(@PathVariable Long id) {
        return ResponseEntity.ok(chatRoomService.findById(id));
    }
    @PostMapping()
    public ResponseEntity<ChatRoomDto> saveRoom(@RequestBody ChatRoomDto chatRoom){
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
    public ResponseEntity<ChatRoomDto> addUserToChat(@RequestBody ChatRoomDto chatRoomDto , @PathVariable Long chatRoomId){
        return ResponseEntity.ok(chatRoomService.addUserToChat(chatRoomDto,chatRoomId));
    }

}
