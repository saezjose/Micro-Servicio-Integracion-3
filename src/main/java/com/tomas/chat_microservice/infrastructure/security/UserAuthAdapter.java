package com.tomas.chat_microservice.infrastructure.security;

import com.tomas.chat_microservice.domain.auth.UserAuthPort;
import com.tomas.chat_microservice.domain.auth.UserInfo;
import com.tomas.chat_microservice.model.User;
import com.tomas.chat_microservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class UserAuthAdapter implements UserAuthPort {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserAuthAdapter(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Optional<UserInfo> authenticate(String email, String password) {
        return userRepository.findByEmail(email)
                .filter(user -> passwordEncoder.matches(password, user.getPasswordHash()))
                .map(this::toUserInfo);
    }

    private UserInfo toUserInfo(User user) {
        return new UserInfo(
                user.getEmail(),      // username → podemos usar el mismo email
                user.getRole().name(), // convierto enum Role.CLIENT o Role.COMPANY a String
                user.getEmail()
        );
    }
}
