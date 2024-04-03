package com.example.services;

import com.example.dto.UserDto;

import java.util.Optional;

public interface AdminService {

    Optional<UserDto> findByUsername(String username);

}
