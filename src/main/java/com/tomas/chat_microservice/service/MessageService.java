package com.tomas.chat_microservice.service;

import com.tomas.chat_microservice.model.Message;
import com.tomas.chat_microservice.repository.MessageRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageService {

    private final MessageRepository messageRepository;

    public MessageService(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    public Message sendMessage(Message message) {
        return messageRepository.save(message);
    }

    public List<Message> getMessagesByUser(Long userId) {
        return messageRepository.findBySenderIdOrReceiverId(userId, userId);
    }

    public List<Message> getConversation(String conversationId) {
        return messageRepository.findByConversationId(conversationId);
    }
}

