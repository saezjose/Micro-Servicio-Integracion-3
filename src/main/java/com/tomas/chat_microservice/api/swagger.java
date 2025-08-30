package com.tomas.chat_microservice.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class swagger {

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("status", "ok", "message", "Swagger funcionando");
    }
}
