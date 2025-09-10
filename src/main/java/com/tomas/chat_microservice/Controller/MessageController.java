package com.tomas.chat_microservice.Controller;

import com.tomas.chat_microservice.Model.Message;
import com.tomas.chat_microservice.Service.MessageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/messages")
public class MessageController {

    private final MessageService messageService;

    public MessageController(MessageService messageService) {
        this.messageService = messageService;
    }

    @GetMapping("/{receiver}")
    public List<Message> getMessages(@PathVariable String receiver) {
        return messageService.getMessagesByUser(receiver);
    }

    @PostMapping
    public void sendMessage(@RequestBody Message message) {
        messageService.sendMessage(message);
    }
}
