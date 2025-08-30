package org.example;

import org.example.model.Message;
import org.example.repository.MessageRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public CommandLineRunner demo(MessageRepository messageRepository) {
        return (args) -> {
            Message msg = new Message();
            msg.setSenderId(1L);
            msg.setReceiverId(2L);
            msg.setContent("Hola, este es un mensaje de prueba en H2");
            msg.setTimestamp(LocalDateTime.now());

            messageRepository.save(msg);

            System.out.println("Mensajes en la BD");
            messageRepository.findAll().forEach(System.out::println);
        };
    }
}