package com.tomas.chat_microservice.model.repository;

import com.tomas.chat_microservice.model.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
}
