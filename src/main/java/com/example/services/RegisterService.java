package com.example.services;

import com.example.controllers.RegisterRequest;
import com.example.dto.UserDto;

public interface RegisterService {
    UserDto register(RegisterRequest request);
}
