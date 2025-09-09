package com.tomas.chat_microservice.controller;

import com.tomas.chat_microservice.model.Message;
import com.tomas.chat_microservice.model.SenderType;
import com.tomas.chat_microservice.service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // -----------------------------
    //  Endpoints estándar REST
    // -----------------------------
    @PostMapping("/messages")
    public Message sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message);
    }

    @GetMapping("/messages/{userId}")
    public List<Message> getMessages(@PathVariable Long userId) {
        return messageService.getMessagesByUser(userId);
    }

    @GetMapping("/messages/conversation/{conversationId}")
    public List<Message> getConversation(@PathVariable String conversationId) {
        return messageService.getConversation(conversationId);
    }

    // -----------------------------
    //  Endpoints compatibles con el front de Alvarez
    // -----------------------------

    // POST /chat/send
    @PostMapping("/chat/send")
    public Message sendChatMessage(@RequestBody Map<String, String> body) {
        Message msg = new Message();
        msg.setSenderId(null); // si no usas IDs numéricos aún, pon null
        msg.setReceiverId(null);
        msg.setConversationId(body.getOrDefault("conversationId", "default"));
        msg.setSenderType(SenderType.CLIENTE); // o EMPRESA, depende de tu lógica
        msg.setContent(body.get("content"));
        msg.setCreatedAt(Instant.now());

        return messageService.sendMessage(msg);
    }

    // GET /chat/messages?user1=X&user2=Y
    @GetMapping("/chat/messages")
    public List<Message> getChatMessages(@RequestParam String user1, @RequestParam String user2) {
        // Aquí puedes mapear "user1" y "user2" a IDs o simplemente devolver todos
        // Por ahora: devolvemos toda la conversación con conversationId = default
        return messageService.getConversation("default");
    }
}
