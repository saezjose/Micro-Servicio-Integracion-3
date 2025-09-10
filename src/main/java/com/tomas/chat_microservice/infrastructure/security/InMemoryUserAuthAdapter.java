package com.tomas.chat_microservice.infrastructure.security;

import com.tomas.chat_microservice.domain.auth.UserAuthPort;
import com.tomas.chat_microservice.domain.auth.UserInfo;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;

@Component
public class InMemoryUserAuthAdapter implements UserAuthPort {

    // email -> (password, role, username)
    private final Map<String, Record> users = Map.of(
            "user@test.com",  new Record("1234", "USER",  "user"),
            "admin@test.com", new Record("admin","ADMIN", "admin")
    );

    @Override
    public Optional<UserInfo> authenticate(String email, String password) {
        Record r = users.get(email);
        if (r != null && r.password().equals(password)) {
            return Optional.of(new UserInfo(r.username(), r.role(), email));
        }
        return Optional.empty();
    }

    private record Record(String password, String role, String username) {}
}
