package com.tomas.chat_microservice.application.auth;

import com.tomas.chat_microservice.domain.auth.JwtProvider;
import org.springframework.stereotype.Service;

/** Caso de uso: validar un JWT. */
@Service
public class ValidateTokenUseCase {

    private final JwtProvider jwtProvider;

    public ValidateTokenUseCase(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public boolean execute(String token) {
        return jwtProvider.validateToken(token);
    }
}
