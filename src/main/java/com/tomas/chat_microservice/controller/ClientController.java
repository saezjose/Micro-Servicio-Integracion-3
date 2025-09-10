package com.tomas.chat_microservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/client")
public class ClientController {
    @GetMapping("/home")
    public String home() {
        return "Bienvenida/o CLIENTE: zona protegida";
    }
}

