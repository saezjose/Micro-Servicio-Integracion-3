package com.tomas.chat_microservice.Controller;
import com.tomas.chat_microservice.Model.Message;
import com.tomas.chat_microservice.Service.MessageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/messages")
@Tag(name = "Mensajes", description = "Operaciones para enviar y recibir mensajes")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{receiver}")
    @Operation(summary = "Obtener mensajes por receptor",
            description = "Devuelve todos los mensajes enviados a un usuario en específico.")
    public List<Message> getMessages(@PathVariable String receiver) {
        return messageService.getMessagesByUser(receiver);
    }

    @PostMapping
    @Operation(summary = "Enviar un mensaje",
            description = "Permite enviar un nuevo mensaje indicando emisor, receptor y contenido.")
    public void sendMessage(@RequestBody Message message) {
        messageService.sendMessage(message);
    }
}
