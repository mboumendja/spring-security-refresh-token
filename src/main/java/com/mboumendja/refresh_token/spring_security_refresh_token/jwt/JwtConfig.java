package com.mboumendja.refresh_token.spring_security_refresh_token.jwt;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;

@Getter
@Configuration
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {
    private final String issuer = "mboumendja-auth-app";
    private final long expiration = 300000; // 5 min in milliseconds
    private final long refreshExpiration = 86400000; // 24 hours in milliseconds
}