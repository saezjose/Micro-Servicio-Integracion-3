package com.tomas.chat_microservice.repository;

import com.tomas.chat_microservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
