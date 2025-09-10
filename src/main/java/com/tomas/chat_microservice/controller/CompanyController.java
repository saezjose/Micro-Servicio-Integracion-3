package com.tomas.chat_microservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company")
public class CompanyController {
    @GetMapping("/home")
    public String home() {
        return "Bienvenida/o EMPRESA: zona protegida";
    }
}
