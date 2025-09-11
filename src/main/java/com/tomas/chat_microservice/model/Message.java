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

    /** Ahora usamos Role (CLIENT / COMPANY) en vez de SenderType */
    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    @Column(nullable = false, updatable = false)
    private Instant createdAt = Instant.now();
}
