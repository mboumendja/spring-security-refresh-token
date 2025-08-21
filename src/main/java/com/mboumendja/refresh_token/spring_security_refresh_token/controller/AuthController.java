package com.mboumendja.refresh_token.spring_security_refresh_token.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mboumendja.refresh_token.spring_security_refresh_token.dto.LoginRequest;
import com.mboumendja.refresh_token.spring_security_refresh_token.dto.RegisterRequest;
import com.mboumendja.refresh_token.spring_security_refresh_token.service.UserService;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated @RequestBody RegisterRequest request) {
        return userService.createUser(request);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Validated @RequestBody LoginRequest request, HttpServletResponse response) {
        return userService.login(request, response);
    }

    @PostMapping("/logout")
    public ResponseEntity<?> logout(@Validated @RequestBody String refreshToken, HttpServletResponse response) {        
        return userService.logout(refreshToken, response);
    }
    
}
