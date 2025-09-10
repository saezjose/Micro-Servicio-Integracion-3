package com.tomas.chat_microservice.domain.auth;

/**
 * Contrato para emitir y validar JWT. (ISP + DIP)
 * La aplicación depende de esta abstracción, no de una librería específica.
 */
public interface JwtProvider {
    String generateToken(UserInfo userInfo);
    boolean validateToken(String token);
    String extractUsername(String token);
    <T> T extractClaim(String token, String claimName, Class<T> type);
}
