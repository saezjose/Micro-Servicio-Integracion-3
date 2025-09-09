package com.tomas.chat_microservice.Controller;

import com.tomas.chat_microservice.DTO.RegisterRequest;
import com.tomas.chat_microservice.DTO.UserResponse;
import com.tomas.chat_microservice.Model.User;
import com.tomas.chat_microservice.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try {
            User savedUser = userService.registerUser(request.getUsername(), request.getPassword());

            UserResponse response = new UserResponse(
                    savedUser.getId(),
                    savedUser.getUsername()
            );

            return ResponseEntity.ok(response);

        } catch (RuntimeException ex) {
            // 🚨 Si el usuario ya existe → 409
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(ex.getMessage());
        }
    }
}
