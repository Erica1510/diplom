package com.example.dto;

import lombok.Data;

@Data
public class UsernamePasswordValidationRequest {
    private String username;

    private String password;

    public String getUsername() {
        return username;
    }
}