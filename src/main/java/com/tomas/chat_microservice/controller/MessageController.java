package com.tomas.chat_microservice.controller;

import com.tomas.chat_microservice.model.Message;
import com.tomas.chat_microservice.model.Role;
import com.tomas.chat_microservice.service.MessageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
@Tag(name = "Mensajes", description = "Operaciones para enviar y recibir mensajes")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    // -----------------------------
    //  Endpoints estándar REST
    // -----------------------------
    @PostMapping("/messages")
    @Operation(summary = "Enviar un mensaje", description = "Permite enviar un nuevo mensaje indicando emisor, receptor y contenido.")
    public Message sendMessage(@RequestBody Message message) {
        return messageService.sendMessage(message);
    }

    @GetMapping("/messages/{userId}")
    @Operation(summary = "Obtener mensajes por usuario", description = "Devuelve todos los mensajes enviados o recibidos por un usuario.")
    public List<Message> getMessages(@PathVariable Long userId) {
        return messageService.getMessagesByUser(userId);
    }

    @GetMapping("/messages/conversation/{conversationId}")
    @Operation(summary = "Obtener conversación", description = "Devuelve todos los mensajes de una conversación específica.")
    public List<Message> getConversation(@PathVariable String conversationId) {
        return messageService.getConversation(conversationId);
    }

    // -----------------------------
    //  Endpoints compatibles con el front de Alvarez
    // -----------------------------
    @PostMapping("/chat/send")
    @Operation(summary = "Enviar mensaje desde el chat", description = "Permite enviar mensajes usando el front de Álvarez.")
    public Message sendChatMessage(@RequestBody Map<String, String> body) {
        Message msg = new Message();
        msg.setSenderId(null); // si no usas IDs numéricos aún
        msg.setReceiverId(null);
        msg.setConversationId(body.getOrDefault("conversationId", "default"));
        msg.setRole(Role.CLIENT); // Cambia a Role.COMPANY según corresponda
        msg.setContent(body.get("content"));
        msg.setCreatedAt(Instant.now());

        return messageService.sendMessage(msg);
    }

    @GetMapping("/chat/messages")
    @Operation(summary = "Obtener mensajes entre usuarios", description = "Devuelve los mensajes intercambiados entre dos usuarios.")
    public List<Message> getChatMessages(@RequestParam String user1, @RequestParam String user2) {
        return messageService.getConversation("default");
    }
}
