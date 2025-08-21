package com.mboumendja.refresh_token.spring_security_refresh_token.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class ProptectedController {
    @GetMapping("/me")
    public String me() {
        return "The protected route";
    }
    
}
