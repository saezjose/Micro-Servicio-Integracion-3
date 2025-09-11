package com.tomas.chat_microservice.service;

import com.tomas.chat_microservice.model.Role;
import com.tomas.chat_microservice.model.User;
import com.tomas.chat_microservice.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * Servicio para manejar usuarios: registro, login, búsqueda.
 */
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // el de Spring (BCrypt)

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /** Registrar nuevo usuario con contraseña hasheada */
    public User registerUser(String email, String password, Role role) {
        String hashed = passwordEncoder.encode(password);
        User user = User.builder()
                .email(email)
                .passwordHash(hashed)
                .role(role)
                .build();
        return userRepository.save(user);
    }

    /** Verificar credenciales de login */
    public boolean login(String email, String password) {
        return userRepository.findByEmail(email)
                .map(u -> passwordEncoder.matches(password, u.getPasswordHash()))
                .orElse(false);
    }

    /** Buscar usuario por email */
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    /** Verificar si existe un usuario con ese email */
    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }
}
