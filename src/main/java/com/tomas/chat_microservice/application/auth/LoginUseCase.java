package com.tomas.chat_microservice.application.auth;

import com.tomas.chat_microservice.domain.auth.JwtProvider;
import com.tomas.chat_microservice.domain.auth.UserAuthPort;
import com.tomas.chat_microservice.domain.auth.UserInfo;
import org.springframework.stereotype.Service;

@Service
public class LoginUseCase {

    private final UserAuthPort userAuthPort;
    private final JwtProvider jwtProvider;

    public LoginUseCase(UserAuthPort userAuthPort, JwtProvider jwtProvider) {
        this.userAuthPort = userAuthPort;
        this.jwtProvider = jwtProvider;
    }

    /** Valida credenciales y devuelve un JWT firmado. */
    public String execute(String email, String password) {
        UserInfo user = userAuthPort.authenticate(email, password)
                .orElseThrow(InvalidCredentialsException::new);
        return jwtProvider.generateToken(user);
    }
}

