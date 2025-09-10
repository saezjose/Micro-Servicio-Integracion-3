package com.tomas.chat_microservice.interfaces.auth;

import com.tomas.chat_microservice.application.auth.LoginUseCase;
import com.tomas.chat_microservice.application.auth.ValidateTokenUseCase;
import com.tomas.chat_microservice.application.auth.GetProfileFromTokenUseCase;
import com.tomas.chat_microservice.application.auth.LoginUseCase;
import com.tomas.chat_microservice.application.auth.ValidateTokenUseCase;
import com.tomas.chat_microservice.domain.auth.JwtProvider;
import com.tomas.chat_microservice.domain.auth.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/** Endpoints de autenticación: /auth/login, /auth/validate, /auth/me */
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginUseCase loginUseCase;
    private final ValidateTokenUseCase validateTokenUseCase;
    private final GetProfileFromTokenUseCase getProfileFromTokenUseCase;
    private final JwtProvider jwtProvider;

    public AuthController(LoginUseCase loginUseCase,
                          ValidateTokenUseCase validateTokenUseCase,
                          GetProfileFromTokenUseCase getProfileFromTokenUseCase,
                          JwtProvider jwtProvider) {
        this.loginUseCase = loginUseCase;
        this.validateTokenUseCase = validateTokenUseCase;
        this.getProfileFromTokenUseCase = getProfileFromTokenUseCase;
        this.jwtProvider = jwtProvider;
    }

    /** POST /auth/login: valida credenciales y devuelve {token, role, email} */
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest req) {
        String token = loginUseCase.execute(req.getEmail(), req.getPassword());
        String role  = jwtProvider.extractClaim(token, "role", String.class);
        String email = jwtProvider.extractClaim(token, "email", String.class);
        return new LoginResponse(token, role, email);
    }

    /** GET /auth/validate: admite ?token= o header Authorization: Bearer <token> */
    @GetMapping("/validate")
    public ResponseEntity<Boolean> validate(
            @RequestParam(value = "token", required = false) String token,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        try {
            String t = resolveToken(token, authorization);
            return ResponseEntity.ok(validateTokenUseCase.execute(t));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(false);
        }
    }

    /** GET /auth/me: devuelve perfil desde el token (query o header) */
    @GetMapping("/me")
    public ResponseEntity<ProfileResponse> me(
            @RequestParam(value = "token", required = false) String token,
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorization) {
        try {
            String t = resolveToken(token, authorization);
            if (!validateTokenUseCase.execute(t)) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }

            // Dominio: obtengo el perfil desde el token
            Profile profile = getProfileFromTokenUseCase.execute(t);

            // Interfaz: mapeo a DTO de respuesta
            ProfileResponse response = ProfileResponseMapper.toResponse(profile);

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /** Obtiene el token desde query ?token= o desde el header Authorization. */
    private String resolveToken(String token, String authorization) {
        if (token != null && !token.isBlank()) return token;
        if (authorization != null) {
            String a = authorization.trim();
            if (a.regionMatches(true, 0, "Bearer ", 0, 7) && a.length() > 7) {
                return a.substring(7).trim();
            }
        }
        throw new IllegalArgumentException("Token no proporcionado");
    }
}
