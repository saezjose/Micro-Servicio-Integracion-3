package com.tomas.chat_microservice.Service;

import com.tomas.chat_microservice.Model.Message;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MessageService {

    private final List<Message> messages = new ArrayList<>();

    // Agregar mensajes de prueba al iniciar
    public MessageService() {
        messages.add(new Message("Bob", "Alice", "Hola Alice!"));
        messages.add(new Message("Charlie", "Alice", "Qué tal Alice?"));
        messages.add(new Message("Alice", "Bob", "Hola Bob!"));
    }

    public void sendMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessagesByUser(String receiver) {
        return messages.stream()
                .filter(m -> m.getReceiver().equalsIgnoreCase(receiver))
                .collect(Collectors.toList());
    }
}
