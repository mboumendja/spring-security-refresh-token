package com.mboumendja.refresh_token.service;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mboumendja.refresh_token.dto.LoginRequest;
import com.mboumendja.refresh_token.dto.RegisterRequest;
import com.mboumendja.refresh_token.jwt.JwtService;
import com.mboumendja.refresh_token.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public ResponseEntity<?> createUser (RegisterRequest request) {
        if(userRepository.existsByUsername(request.getUsername())) {
            return ResponseEntity.badRequest().body("USER_ALREADY_EXISTS");
        }

        User user = User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();
        
        userRepository.save(user);

        return ResponseEntity.accepted().body(user);
    }

    public ResponseEntity<?> login (LoginRequest loginRequest) {
        return ResponseEntity.ok().body("hh");
    }
}
