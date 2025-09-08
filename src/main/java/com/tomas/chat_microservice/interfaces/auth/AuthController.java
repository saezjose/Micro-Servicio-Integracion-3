package com.tomas.chat_microservice.interfaces.auth;

import com.tomas.chat_microservice.application.auth.GenerateTokenUseCase;
import com.tomas.chat_microservice.application.auth.ValidateTokenUseCase;
import com.tomas.chat_microservice.domain.auth.JwtProvider;
import com.tomas.chat_microservice.domain.auth.UserInfo;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final GenerateTokenUseCase generateToken;
    private final ValidateTokenUseCase validateToken;
    private final JwtProvider jwtProvider; // para extracción de claims

    public AuthController(
            GenerateTokenUseCase generateToken,
            ValidateTokenUseCase validateToken,
            JwtProvider jwtProvider
    ) {
        this.generateToken = generateToken;
        this.validateToken = validateToken;
        this.jwtProvider = jwtProvider;
    }

    /** DEMO: password 1234 => genera token. (Sustituye por tu servicio de usuarios) */
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        if (!"1234".equals(req.getPassword())) {
            throw new RuntimeException("Credenciales inválidas");
        }
        UserInfo info = new UserInfo(req.getEmail(), "USER", req.getEmail());
        String token = generateToken.execute(info);
        return new LoginResponse(token, info.getRole(), info.getEmail());
    }

    /** Valida un token recibido por query param (útil para pruebas rápidas). */
    @GetMapping("/validate")
    public boolean validate(@RequestParam String token) {
        return validateToken.execute(token);
    }

    /** Ejemplo de perfil: extrae claims desde header Authorization: Bearer <token> */
    @GetMapping("/me")
    public LoginResponse me(@RequestHeader(HttpHeaders.AUTHORIZATION) String authorization) {
        String token = authorization != null && authorization.startsWith("Bearer ")
                ? authorization.substring(7) : "";
        if (!validateToken.execute(token)) {
            throw new RuntimeException("Token inválido o expirado");
        }
        String username = jwtProvider.extractUsername(token);
        String role = jwtProvider.extractClaim(token, "role", String.class);
        String email = jwtProvider.extractClaim(token, "email", String.class);
        return new LoginResponse("OK", role, email); // token no se devuelve aquí; “OK” solo para demo
    }
}
