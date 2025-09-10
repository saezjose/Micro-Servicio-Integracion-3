package com.tomas.chat_microservice.interfaces.auth;

import com.tomas.chat_microservice.application.auth.LoginCommand;

public final class LoginRequestMapper {

    private LoginRequestMapper() {}

    public static LoginCommand toCommand(LoginRequest dto) {
        return new LoginCommand(dto.getEmail(), dto.getPassword());
    }
}
