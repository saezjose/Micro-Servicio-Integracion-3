package com.tomas.chat_microservice.application.auth;

/**
 * Comando inmutable que representa la intención de "iniciar sesión".
 * Pertenece a la capa de aplicación, NO a la de transporte HTTP.
 */
public record LoginCommand(String email, String password) { }
