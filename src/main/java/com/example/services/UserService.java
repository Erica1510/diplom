package com.example.services;

import com.example.dto.UserDto;
import com.example.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User createUser(User user);

    List<User> getAllUsers();

    User getUserById(Long id);

    User updateUser(Long id, User user);

    void deleteUser(Long id);

    List<User> getAllOtherUsers(Long userId);

    Optional<UserDto> findByUsername(String username);

}
