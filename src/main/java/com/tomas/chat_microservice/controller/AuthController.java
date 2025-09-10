package com.tomas.chat_microservice.controller;

import com.tomas.chat_microservice.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

record LoginReq(String email, String role) {} // role: CLIENT o COMPANY
record LoginRes(String token, String role, String email) {}

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtService jwtService;

    public AuthController(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginRes> login(@RequestBody LoginReq req) {

        String token = jwtService.generateToken(req.email(), req.role());
        return ResponseEntity.ok(new LoginRes(token, req.role(), req.email()));
    }

    @GetMapping("/health")
    public String health() { return "OK"; }
}
