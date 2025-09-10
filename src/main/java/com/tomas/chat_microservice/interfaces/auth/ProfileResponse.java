package com.tomas.chat_microservice.interfaces.auth;

import java.time.Instant;

public class ProfileResponse {
    private String username;
    private String role;
    private String email;
    private Instant issuedAt;
    private Instant expiresAt;

    public ProfileResponse() {}

    public ProfileResponse(String username, String role, String email, Instant issuedAt, Instant expiresAt) {
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

    public void setUsername(String username) { this.username = username; }
    public void setRole(String role) { this.role = role; }
    public void setEmail(String email) { this.email = email; }
    public void setIssuedAt(Instant issuedAt) { this.issuedAt = issuedAt; }
    public void setExpiresAt(Instant expiresAt) { this.expiresAt = expiresAt; }
}
