package com.tomas.chat_microservice.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import java.time.LocalDateTime;

@Data   // Lombok: genera getters, setters, toString, equals y hashCode automáticamente
@Entity // JPA: indica que esta clase es una entidad de base de datos
public class Message {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    private Long senderId;
    private Long receiverId;
    private String content;
    private LocalDateTime timestamp;
}
