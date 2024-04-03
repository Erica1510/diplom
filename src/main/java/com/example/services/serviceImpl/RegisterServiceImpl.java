package com.example.services.serviceImpl;

import com.example.controllers.RegisterRequest;
import com.example.dto.UserDto;
import com.example.entities.Role;
import com.example.entities.User;
import com.example.repositories.UserRepository;
import com.example.services.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegisterServiceImpl implements RegisterService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public UserDto register(RegisterRequest request) {
        User user = buildUser(request);
        return UserDto.fromUserDto(repository.save(user));
    }

    private User buildUser(RegisterRequest request) {
        return User.builder()
                .firstName(request.getFirstName())
                .lastName(request.getLastName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .passwordConfirm(passwordEncoder.encode(request.getPasswordConfirm()))
                .username(request.getUsername())
                .role(Role.USER)
                .build();
    }
}
