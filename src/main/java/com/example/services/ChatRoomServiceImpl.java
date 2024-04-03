package com.example.services;

import com.example.dto.ChatRoomDto;
import com.example.dto.UserDto;
import com.example.entities.ChatRoom;
import com.example.entities.User;
import com.example.exception.ChatRoomNotFoundException;
import com.example.repositories.ChatRoomRepository;
import com.example.repositories.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatRoomServiceImpl implements ChatRoomService {
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Override
    public Optional<ChatRoomDto> findById(Long id) {
        return Optional.ofNullable(ChatRoomDto.chatRoomDtoToEntity(chatRoomRepository.findById(id)
                .orElseThrow(() -> new ChatRoomNotFoundException("Chat with id " + id + " was not found"))
        ));
    }

    @Override
    @Transactional
    public ChatRoomDto save(ChatRoomDto chatRoom) {
        List<User> users = findUsersById(chatRoom.getUsers());
        ChatRoom chatRoomSaved = buildChatRoom(chatRoom, users);
        chatRoomSaved.setUsers(users);

        return ChatRoomDto.chatRoomDtoToEntity(chatRoomRepository.save(chatRoomSaved));
    }

    @Override
    public List<ChatRoomDto> findAll() {
        return chatRoomRepository.findAll()
                .stream()
                .map(ChatRoomDto::chatRoomDtoToEntity)
                .collect(Collectors.toList());}

    @Override
    @Transactional
    public Optional<ChatRoomDto> deleteById(Long id) {
        Optional<ChatRoom> deletedRoom = chatRoomRepository.findById(id);
        if (deletedRoom.isPresent()) {
            chatRoomRepository.deleteById(id);
            return Optional.of(ChatRoomDto.chatRoomDtoToEntity(deletedRoom.get()));
        } else {
            throw new ChatRoomNotFoundException("Chat with id " + id + " was not found");
        }
    }
    @Override
    @Transactional
    public ChatRoomDto update(Long id, ChatRoomDto updatedChatRoom) {
        ChatRoom existingRoom = chatRoomRepository.findById(id)
                .orElseThrow(() -> new ChatRoomNotFoundException("Chat with id " + id + " was not found"));

        existingRoom.setName(updatedChatRoom.getName());
        List<User> currentUsers = existingRoom.getUsers();
        List<User> newUsers = findUsersById(updatedChatRoom.getUsers());
        // Add new users to the chat room
        currentUsers.addAll(newUsers);
        existingRoom.setUsers(currentUsers);
        return ChatRoomDto.chatRoomDtoToEntity(chatRoomRepository.save(existingRoom));
    }

    private List<User> findUsersById(List<UserDto> users) {
        List<Long> usersIds = users.stream()
                .map(UserDto::getUserId)
                .collect(Collectors.toList());
        return userRepository.findAllById(usersIds);
    }

    private ChatRoom buildChatRoom(ChatRoomDto chatRoom, List<User> currentUsers) {

        return ChatRoom.builder()
                .roomId(chatRoom.getRoomId())
                .name(chatRoom.getName())
                .users(currentUsers)
                .build();
    }
}
