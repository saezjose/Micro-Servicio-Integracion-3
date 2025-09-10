package com.tomas.chat_microservice.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;

@Entity
@Table(name = "messages")
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class Message {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long senderId;
    private Long receiverId;

    /** Identificador lógico de conversación (p. ej. "user1-user2" o UUID) */
    private String conversationId;

    @Column(nullable = false, length = 2000)
    private String content;

    /** Quién envió el mensaje (CLIENTE/EMPRESA/SISTEMA, etc.) */
    @Enumerated(EnumType.STRING)
    private SenderType senderType;

    /** Para que @Builder respete el valor por defecto: */
    @Builder.Default
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();
}
