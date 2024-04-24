package com.example.services.serviceImpl;

import com.example.dto.UserDto;
import com.example.entities.User;
import com.example.repositories.UserRepository;
import com.example.services.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

//    @Override
//    public User createUser(User user) {
//        return userRepository.save(user);
//
//    }

    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(UserDto::fromUserDto)
                .collect(Collectors.toList());
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User updateUser(Long id, User user) {
        User existingUser = userRepository.findById(id).orElse(null);
        if (existingUser != null) {
            existingUser.setFirstName(user.getFirstName());
            existingUser.setLastName(user.getLastName());
            existingUser.setEmail(user.getEmail());
            existingUser.setUsername(user.getUsername());
            existingUser.setRole(user.getRole());
            return userRepository.save(existingUser);
        }
        return null;
    }

    @Override
    public List<User> getAllOtherUsers(Long userId) {
        List<User> users = userRepository.findByUserIdNot(userId);
        List<User> userModels = new ArrayList<>();
        users.forEach(user -> {
            User userModel = new User();
            userModel.setUserId(user.getUserId());
            userModel.setUsername(user.getUsername());

            userModel.setIsActiveChat(false);
            userModels.add(userModel);
        });
        return userModels;
    }

    @Override
    public Optional<UserDto> findByUsername(final String username) {
        return Optional.empty();
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
