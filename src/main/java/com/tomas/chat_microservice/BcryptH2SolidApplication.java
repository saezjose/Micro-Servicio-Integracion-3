package com.tomas.chat_microservice;

import com.tomas.chat_microservice.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import com.tomas.chat_microservice.model.Role;

@SpringBootApplication
public class BcryptH2SolidApplication {

    public static void main(String[] args) {
        SpringApplication.run(BcryptH2SolidApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserService userService) {
        return args -> {
            if (!userService.existsByEmail("cliente@test.com")) {
                userService.registerUser("cliente@test.com", "1234", Role.CLIENT);
            }
            if (!userService.existsByEmail("empresa@test.com")) {
                userService.registerUser("empresa@test.com", "abcd", Role.COMPANY);
            }

            System.out.println("Usuarios de prueba creados en H22 🚀");
        };
    }
}
