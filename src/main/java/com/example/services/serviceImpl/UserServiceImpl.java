package com.example.services.serviceImpl;

import com.example.entities.User;
import com.example.repositories.UserRepository;
import com.example.services.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
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
            existingUser.setNickname(user.getNickname());
            existingUser.setRole(user.getRole());
            return userRepository.save(existingUser);
        }
        return null;
    }
    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
