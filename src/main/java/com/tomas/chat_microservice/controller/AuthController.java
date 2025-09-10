package com.tomas.chat_microservice.controller;

import com.tomas.chat_microservice.model.User;
import com.tomas.chat_microservice.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserRepository userRepository;

    public AuthController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> credentials) {
        String username = credentials.get("username");
        String password = credentials.get("password");

        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isPresent() && userOpt.get().getPassword().equals(password)) {
            // Genera un "token" simple (solo un string para probar)
            String token = "token-" + username;
            return Map.of(
                    "token", token,
                    "role", userOpt.get().getRole()
            );
        }

        throw new RuntimeException("Credenciales inválidas");
    }
}
