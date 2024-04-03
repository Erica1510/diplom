package com.example.dto;

import com.example.entities.Role;
import com.example.entities.User;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class AuthenticatedUserDetails {
    private Long userId;

    private String username;

    private String firstName;

    private String lastName;

    private String access_token;

    private String refresh_token;

    private String email;

    private String phoneNumber;

    private Role role;

    public static AuthenticatedUserDetails fromAuthenticatedUser(User user) {
        return AuthenticatedUserDetails.builder()
                .userId(user.getUserId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .email(user.getEmail())
                .phoneNumber(user.getPhoneNumber())
                .role(user.getRole())
                .build();
    }
}
