package com.tomas.chat_microservice.domain.auth;

/** Entidad de dominio: datos mínimos que viajarán en el token. (SRP) */
public class UserInfo {
    private final String username;
    private final String role;
    private final String email;

    public UserInfo(String username, String role, String email) {
        this.username = username;
        this.role = role;
        this.email = email;
    }

    public String getUsername() { return username; }
    public String getRole() { return role; }
    public String getEmail() { return email; }
}
