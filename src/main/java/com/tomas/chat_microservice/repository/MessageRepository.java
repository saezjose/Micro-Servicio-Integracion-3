package com.tomas.chat_microservice.repository;

import com.tomas.chat_microservice.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {

    // Buscar todos los mensajes enviados por un usuario
    List<Message> findBySender(String sender);

    // Buscar todos los mensajes recibidos por un usuario
    List<Message> findByReceiver(String receiver);

    // Buscar mensajes entre dos usuarios (conversación básica)
    List<Message> findBySenderAndReceiver(String sender, String receiver);
}
