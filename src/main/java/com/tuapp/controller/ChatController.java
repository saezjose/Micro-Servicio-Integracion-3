package com.tuapp.controller;

import com.tuapp.model.ChatMessage;
import com.tuapp.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/chat")
public class ChatController {

    @Autowired
    private ChatService service;

    @PostMapping("/send")
    public ChatMessage sendMessage(@RequestBody Map<String, String> body) {
        return service.sendMessage(body.get("sender"), body.get("receiver"), body.get("content"));
    }

    @GetMapping("/messages")
    public List<ChatMessage> getMessages(@RequestParam String user1, @RequestParam String user2) {
        return service.getMessages(user1, user2);
    }
}
