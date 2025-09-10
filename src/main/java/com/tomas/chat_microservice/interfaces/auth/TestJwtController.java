package com.tomas.chat_microservice.interfaces.auth;

import com.tomas.chat_microservice.application.auth.GenerateTokenUseCase;
import com.tomas.chat_microservice.application.auth.ValidateTokenUseCase;
import com.tomas.chat_microservice.domain.auth.JwtProvider;
import com.tomas.chat_microservice.domain.auth.UserInfo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth/test")
public class TestJwtController {

    private final GenerateTokenUseCase generate;
    private final ValidateTokenUseCase validate;
    private final JwtProvider jwt;

    public TestJwtController(GenerateTokenUseCase generate, ValidateTokenUseCase validate, JwtProvider jwt) {
        this.generate = generate;
        this.validate = validate;
        this.jwt = jwt;
    }

    /** Genera un token de prueba con datos fake */
    @GetMapping("/generate")
    public String generate(@RequestParam(defaultValue = "user@test.com") String email,
                           @RequestParam(defaultValue = "USER") String role,
                           @RequestParam(defaultValue = "user") String username) {
        return generate.execute(new UserInfo(username, role, email));
    }

    /** Valida un token */
    @GetMapping("/validate")
    public boolean validate(@RequestParam String token) {
        return validate.execute(token);
    }

    /** Lee claims del token */
    @GetMapping("/claims")
    public Object claims(@RequestParam String token) {
        return new Object() {
            public final String subject = jwt.extractUsername(token);
            public final String role = jwt.extractClaim(token, "role", String.class);
            public final String email = jwt.extractClaim(token, "email", String.class);
        };
    }
}
