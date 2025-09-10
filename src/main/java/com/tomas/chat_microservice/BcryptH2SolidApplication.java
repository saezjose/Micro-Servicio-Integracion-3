package com.tomas.chat_microservice;

import com.tomas.chat_microservice.Service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class BcryptH2SolidApplication {

    public static void main(String[] args) {
        SpringApplication.run(BcryptH2SolidApplication.class, args);
    }

    @Bean
    CommandLineRunner init(UserService userService) {
        return args -> {
            userService.registerUser("fran", "secreto123");

            boolean loginOk = userService.login("fran", "secreto123");
            System.out.println("Login exitoso: " + loginOk);

            boolean loginFail = userService.login("fran", "otroPass");
            System.out.println("Login fallido: " + loginFail);
        };
    }
}
