package com.tomas.chat_microservice.domain.auth;

import java.util.Optional;

/** Abstracción de cómo se validan credenciales (BD, servicio externo, etc). */
public interface UserAuthPort {
    Optional<UserInfo> authenticate(String email, String password);
}
