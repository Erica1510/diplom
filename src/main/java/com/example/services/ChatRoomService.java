package com.example.services;

import com.example.dto.ChatRoomDto;

import java.util.List;
import java.util.Optional;

public interface ChatRoomService {
    Optional<ChatRoomDto> findById(Long id);

    ChatRoomDto save(ChatRoomDto chatRoom);

    List<ChatRoomDto> findAll();

    Optional<ChatRoomDto> deleteById(Long id);

    ChatRoomDto update(Long id, ChatRoomDto updatedChatRoom);

    ChatRoomDto addUserToChat(ChatRoomDto chatRoomDto, Long chatRoomId);
}
