package com.tomas.chat_microservice.service;

import com.tomas.chat_microservice.model.Role;
import com.tomas.chat_microservice.model.UserTemp;
import com.tomas.chat_microservice.repository.UserRepositoryTemp;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    private final UserRepositoryTemp userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepositoryTemp userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Registrar nuevo usuario
    public UserTemp registerUser(String email, String password, Role role) {
        String hashed = passwordEncoder.encode(password);
        UserTemp user = UserTemp.builder()
                .email(email)
                .passwordHash(hashed)
                .role(role)
                .build();
        return userRepository.save(user);
    }

    // Verificar login
    public boolean login(String email, String password) {
        return userRepository.findByEmail(email)
                .map(u -> passwordEncoder.matches(password, u.getPasswordHash()))
                .orElse(false);
    }

    // Buscar usuario por email
    public Optional<UserTemp> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

}
