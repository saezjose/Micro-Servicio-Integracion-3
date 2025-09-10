package com.tomas.chat_microservice.application.auth;

import com.tomas.chat_microservice.domain.auth.Profile;
import com.tomas.chat_microservice.domain.auth.JwtProvider;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
public class GetProfileFromTokenUseCase {

    private final JwtProvider jwtProvider;

    public GetProfileFromTokenUseCase(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    public Profile execute(String token) {
        String username = jwtProvider.extractUsername(token);
        String role     = jwtProvider.extractClaim(token, "role", String.class);
        String email    = jwtProvider.extractClaim(token, "email", String.class);

        // Tiempos estándar (puedes ajustar según tu JwtProvider)
        Date iatDate = null;
        Date expDate = null;
        try { iatDate = jwtProvider.extractClaim(token, "iat", Date.class); } catch (Exception ignored) {}
        try { expDate = jwtProvider.extractClaim(token, "exp", Date.class); } catch (Exception ignored) {}

        Instant iat = (iatDate != null) ? iatDate.toInstant() : null;
        Instant exp = (expDate != null) ? expDate.toInstant() : null;

        return new Profile(username, role, email, iat, exp);
        // Si no quieres iat/exp, puedes pasarlos como null sin problemas.
    }
}
