package com.tomas.chat_microservice.application.auth;

import com.tomas.chat_microservice.domain.auth.JwtProvider;
import com.tomas.chat_microservice.domain.auth.UserInfo;
import org.springframework.stereotype.Service;

/** Caso de uso: generar JWT a partir de información del usuario. (SRP + DIP) */
@Service
public class GenerateTokenUseCase {

    private final JwtProvider jwtProvider;

    public GenerateTokenUseCase(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public String execute(UserInfo userInfo) {
        return jwtProvider.generateToken(userInfo);
    }
}
