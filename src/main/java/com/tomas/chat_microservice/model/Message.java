package com.tomas.chat_microservice.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Table(name = "messages")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Para agrupar mensajes en una conversación
    @Column(nullable = false)
    private String conversationId;

    // IDs de los usuarios
    private Long senderId;
    private Long receiverId;

    // Tipo de emisor: EMPRESA o CLIENTE
    @Enumerated(EnumType.STRING)
    private SenderType senderType;

    @Column(nullable = false, length = 1000)
    private String content;

    // Fecha de creación automática
    @CreationTimestamp
    private Instant createdAt;
}
