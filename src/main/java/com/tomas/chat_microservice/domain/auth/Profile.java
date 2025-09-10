package com.tomas.chat_microservice.domain.auth;

import java.time.Instant;

public class Profile {
    private final String username;
    private final String role;
    private final String email;
    private final Instant issuedAt;
    private final Instant expiresAt;

    public Profile(String username, String role, String email, Instant issuedAt, Instant expiresAt) {
        this.username = username;
        this.role = role;
        this.email = email;
        this.issuedAt = issuedAt;
        this.expiresAt = expiresAt;
    }

    public String getUsername() { return username; }
    public String getRole() { return role; }
    public String getEmail() { return email; }
    public Instant getIssuedAt() { return issuedAt; }
    public Instant getExpiresAt() { return expiresAt; }
}
