package com.example.dto;

import com.example.entities.ChatRoom;
import com.example.entities.MessageType;
import com.example.entities.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ChatRoomDto {
    private Long roomId;
    private String name;
    private MessageType type;
    private String content;
    private String sender;
    private List<UserDto> users = new ArrayList<>();

    public static ChatRoomDto chatRoomDtoToEntity(ChatRoom chatRoom){
    return ChatRoomDto.builder()
            .roomId(chatRoom.getRoomId())
            .name(chatRoom.getName())
            .users(toUserList(chatRoom.getUsers()))
            .build();
    }
    public static List<UserDto> toUserList(List<User> users) {
        return users.stream()
                .map(UserDto::fromUserDto)
                .collect(Collectors.toList());
    }

}
