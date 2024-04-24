package com.example.repositories;

import com.example.entities.PublicMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PublicMessageRepository extends JpaRepository<PublicMessage, Long> {
}
