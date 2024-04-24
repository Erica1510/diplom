package com.example.repositories;

import com.example.entities.PrivateMessage;
import com.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PrivateMessageRepository extends JpaRepository<PrivateMessage, Long> {
    List<PrivateMessage> findBySenderOrReceiver(User sender, User receiver);
}
