package com.tomas.chat_microservice.controller;

import com.tomas.chat_microservice.auth.JwtService;
import com.tomas.chat_microservice.dto.LoginRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/auth")
@Tag(name = "Auth", description = "Endpoints de autenticación")
public class UserController {

    private final JwtService jwtService;

    public UserController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Operation(summary = "Login de usuario")
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody LoginRequest request) {
        String token = jwtService.generateToken(request.getEmail(), "USER");
        return Map.of(
                "token", token,
                "role", "USER",
                "email", request.getEmail()
        );
    }
    @GetMapping("/me")
    @Operation(summary = "Información del usuario logueado")
    public Map<String, Object> me(
            @Parameter(description = "Token JWT del usuario", required = true)
            @RequestHeader("Authorization") String authHeader
    ) {
        String token = authHeader.replace("Bearer ", "");
        var claims = jwtService.validateToken(token);
        return Map.of(
                "email", claims.getBody().getSubject(),
                "role", claims.getBody().get("role")
        );
    }
}
