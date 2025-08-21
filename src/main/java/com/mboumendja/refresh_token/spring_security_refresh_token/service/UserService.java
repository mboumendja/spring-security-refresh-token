package com.mboumendja.refresh_token.spring_security_refresh_token.service;

import java.time.Instant;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.mboumendja.refresh_token.spring_security_refresh_token.dto.LoginRequest;
import com.mboumendja.refresh_token.spring_security_refresh_token.dto.LoginResponse;
import com.mboumendja.refresh_token.spring_security_refresh_token.dto.RegisterRequest;
import com.mboumendja.refresh_token.spring_security_refresh_token.entity.RefreshToken;
import com.mboumendja.refresh_token.spring_security_refresh_token.entity.User;
import com.mboumendja.refresh_token.spring_security_refresh_token.jwt.JwtConfig;
import com.mboumendja.refresh_token.spring_security_refresh_token.jwt.JwtService;
import com.mboumendja.refresh_token.spring_security_refresh_token.repository.RefreshTokenRepository;
import com.mboumendja.refresh_token.spring_security_refresh_token.repository.UserRepository;
import com.mboumendja.refresh_token.spring_security_refresh_token.utils.CookieUtil;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final CustomUserDetailsService userDetailsService;

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

    public ResponseEntity<?> login (LoginRequest loginRequest, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        User user = (User) authentication.getPrincipal();

        String accesToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        saveRefreshToken(refreshToken, user);

        CookieUtil.addAccessTokenCookie(response, accesToken);
        
        return ResponseEntity.ok().body(new LoginResponse(user.getUsername(), user.getRole(), refreshToken));
    }

    public void saveRefreshToken(String token, User user) {
        RefreshToken refreshToken = RefreshToken.builder()
                .token(token)
                .user(user)
                .expiryDate(Instant.now().plusMillis(jwtConfig.getRefreshExpiration()))
                .build();
        refreshTokenRepository.save(refreshToken);
    }

    public ResponseEntity<?> refreshToken (String refreshToken, HttpServletResponse response) {
        // we should check if the token is first expired
        final String username = jwtService.extractUsername(refreshToken);
        if(!valideRefreshToken(refreshToken, username)) {
            throw new RuntimeException("Invalid refresh token");
        }

        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
            .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        if(!jwtService.isTokenExpired(refreshToken)) {
            refreshTokenRepository.delete(storedToken);
            throw new RuntimeException("Expired refresh token");
        }

        UserDetails userDetails = userDetailsService.loadUserByUsername(username);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

        User user = (User) authentication.getPrincipal();

        String accesToken = jwtService.generateAccessToken(user);

        CookieUtil.addAccessTokenCookie(response, accesToken);

        return ResponseEntity.ok().body(new LoginResponse(user.getUsername(), user.getRole(), refreshToken));
        
    }

    public boolean valideRefreshToken(String token, String username) {

        if(username != null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            if(jwtService.isTokenValid(token, userDetails)) {
                return true;
            }
        }

        return false;
    }

    public ResponseEntity<?> logout(String refreshToken, HttpServletResponse response) {

        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
            .orElseThrow(() -> new RuntimeException("Refresh token not found"));

        refreshTokenRepository.delete(storedToken);
        CookieUtil.addTokenCookies(response, null, null);

        return ResponseEntity.ok("LOG_OUT");
    }
}
