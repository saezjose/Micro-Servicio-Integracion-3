package com.tomas.chat_microservice.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

@Service
public class JwtService {

    @Value("${app.jwt.secret}")
    private String secret;

    @Value("${app.jwt.issuer}")
    private String issuer;

    // minutos de vida del token
    private static final long TTL_MINUTES = 60;

    public String generateToken(String email, String role) {
        long exp = Instant.now().plusSeconds(TTL_MINUTES * 60).getEpochSecond();
        String payload = email + "|" + role + "|" + exp + "|" + issuer;
        String sig = hmac(payload);
        String raw = payload + "|" + sig;
        return Base64.getUrlEncoder().withoutPadding()
                .encodeToString(raw.getBytes(StandardCharsets.UTF_8));
    }

    public Decoded validate(String token) {
        try {
            String raw = new String(Base64.getUrlDecoder().decode(token), StandardCharsets.UTF_8);
            String[] parts = raw.split("\\|");
            if (parts.length != 5) return null;

            String email = parts[0];
            String role = parts[1];
            long exp = Long.parseLong(parts[2]);
            String iss = parts[3];
            String sig = parts[4];

            String signed = String.join("|", email, role, String.valueOf(exp), iss);
            if (!hmac(signed).equals(sig)) return null;
            if (!issuer.equals(iss)) return null;
            if (Instant.now().getEpochSecond() > exp) return null;

            return new Decoded(email, role);
        } catch (Exception e) {
            return null;
        }
    }

    private String hmac(String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256"));
            return Base64.getUrlEncoder().withoutPadding()
                    .encodeToString(mac.doFinal(data.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public record Decoded(String email, String role) {}
}
