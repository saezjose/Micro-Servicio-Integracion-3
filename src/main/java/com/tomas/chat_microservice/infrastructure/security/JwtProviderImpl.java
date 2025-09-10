package com.tomas.chat_microservice.infrastructure.security;

import com.tomas.chat_microservice.domain.auth.JwtProvider;
import com.tomas.chat_microservice.domain.auth.UserInfo;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.Map;
import java.util.function.Function;

/**
 * Implementación concreta con JJWT. (OCP: puede reemplazarse por otra sin tocar application)
 */
@Component
public class JwtProviderImpl implements JwtProvider {

    @Value("${jwt.secret}")
    private String secretBase64;

    @Value("${jwt.expiration}")
    private long jwtExpirationMs;

    private SecretKey getKey() {
        byte[] bytes = Decoders.BASE64.decode(secretBase64);
        return Keys.hmacShaKeyFor(bytes);
    }

    @Override
    public String generateToken(UserInfo user) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + jwtExpirationMs);

        return Jwts.builder()
                .setClaims(Map.of("role", user.getRole(), "email", user.getEmail()))
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public boolean validateToken(String token) {
        try {
            parseClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    @Override
    public String extractUsername(String token) {
        return extract(token, Claims::getSubject);
    }

    @Override
    public <T> T extractClaim(String token, String claimName, Class<T> type) {
        return parseClaims(token).get(claimName, type);
    }

    /* ----------- helpers ----------- */

    private Claims parseClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private <T> T extract(String token, Function<Claims, T> resolver) {
        return resolver.apply(parseClaims(token));
    }
}
