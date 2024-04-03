package com.example.dto;

import com.example.entities.Role;
import com.example.entities.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
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
    @JsonIgnore
    private Role role;

    public static UserDto fromUserDto(User user) {
        return UserDto.builder()
                .email(user.getEmail())
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .role(Role.USER)
                .build();
    }
}
