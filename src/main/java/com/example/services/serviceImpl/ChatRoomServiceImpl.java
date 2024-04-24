package com.example.services.serviceImpl;

import com.example.dto.ChatRoomDto;
import com.example.dto.UserDto;
import com.example.entities.ChatRoom;
import com.example.entities.User;
import com.example.exception.ChatRoomNotFoundException;
import com.example.exception.UsernameNotFoundException;
import com.example.repositories.ChatRoomRepository;
import com.example.repositories.UserRepository;
import com.example.services.ChatRoomService;
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
        ChatRoom chatRoomSaved = buildChatRoom(chatRoom);
        return ChatRoomDto.chatRoomDtoToEntity(chatRoomRepository.save(chatRoomSaved));
    }

    @Override
    public List<ChatRoomDto> findAll() {
        return chatRoomRepository.findAll()
                .stream()
                .map(ChatRoomDto::chatRoomDtoToEntity)
                .collect(Collectors.toList());
    }

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
        currentUsers.addAll(newUsers);
        existingRoom.setUsers(currentUsers);
        return ChatRoomDto.chatRoomDtoToEntityWIthUsers(chatRoomRepository.save(existingRoom));
    }

    @Override
    @Transactional
    public ChatRoomDto addUserToChat(ChatRoomDto chatRoomDto, Long chatRoomId) {
        List<User> users = findUsersById(chatRoomDto.getUsers());
        ChatRoom chatRoom = findChatRoom(chatRoomId);
        chatRoom.setUsers(users);
        return ChatRoomDto.chatRoomDtoToEntityWIthUsers(chatRoomRepository.save(chatRoom));
    }

    private ChatRoom findChatRoom(Long chatRoomId) {
        return chatRoomRepository.findById(chatRoomId).orElseThrow(
                () -> new ChatRoomNotFoundException("Chat Room with id " + chatRoomId + " was not found")
        );
    }

    private User findUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new UsernameNotFoundException("User with username " + username + " was not found"));
    }

    private List<User> findUsersById(List<UserDto> users) {
        List<Long> usersIds = users.stream()
                .map(UserDto::getUserId)
                .collect(Collectors.toList());
        return userRepository.findAllById(usersIds);
    }

    private ChatRoom buildChatRoom(ChatRoomDto chatRoom) {

        return ChatRoom.builder()
                .roomId(chatRoom.getRoomId())
                .name(chatRoom.getName())
                .build();
    }
}
