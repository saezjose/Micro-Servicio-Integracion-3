package com.tuapp.service;

import com.tuapp.model.ChatMessage;
import com.tuapp.repository.ChatMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class ChatService {

    @Autowired
    private ChatMessageRepository repository;

    public ChatMessage sendMessage(String sender, String receiver, String content) {
        ChatMessage msg = new ChatMessage();
        msg.setSender(sender);
        msg.setReceiver(receiver);
        msg.setContent(content);
        msg.setTimestamp(LocalDateTime.now());
        return repository.save(msg);
    }

    public List<ChatMessage> getMessages(String user1, String user2) {
        return repository.findBySenderAndReceiverOrReceiverAndSenderOrderByTimestampAsc(
                user1, user2, user1, user2);
    }
}
