package com.example.dto;

import com.example.entities.ChatRoom;
import com.example.entities.Role;
import com.example.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UserDto {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private Boolean isActiveChat;
    private String socialLinks;
    private String status;
    private String phoneNumber;
    @JsonIgnore
    private Role role;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ChatRoomDto> room = new ArrayList<>();

    public static UserDto fromUserDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .isActiveChat(user.getIsActiveChat())
                .socialLinks(user.getSocialLinks())
                .status(user.getStatus())
                .room(mapChatRoom(user))
                .role(Role.USER)
                .build();
    }

    private static List<ChatRoomDto> mapChatRoom(User user) {
        if (Objects.nonNull(user.getRoom())) {
            return user.getRoom().stream()
                    .map(ChatRoomDto::chatRoomDtoToEntity)
                    .collect(Collectors.toList());
        }
        return new ArrayList<>();
    }
}
