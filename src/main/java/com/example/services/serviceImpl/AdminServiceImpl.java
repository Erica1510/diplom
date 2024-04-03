package com.example.services.serviceImpl;

import com.example.dto.UserDto;
import com.example.exception.UserNotFoundException;
import com.example.repositories.UserRepository;
import com.example.services.AdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserRepository adminRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public Optional<UserDto> findByUsername(final String username) {
        return Optional.ofNullable(UserDto.fromUserDto(adminRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Admin with username " + username + " was not found"))
        ));
    }


}
