package com.tomas.chat_microservice.repository;

import com.tomas.chat_microservice.model.Message;
import com.tomas.chat_microservice.model.SenderType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // Traer todos los mensajes de una conversación
    List<Message> findByConversationId(String conversationId);

    // Traer todos los mensajes enviados o recibidos por un usuario
    List<Message> findBySenderIdOrReceiverId(Long senderId, Long receiverId);

    // Traer todos los mensajes de un tipo de emisor (EMPRESA o CLIENTE)
    List<Message> findBySenderType(SenderType senderType);
}
