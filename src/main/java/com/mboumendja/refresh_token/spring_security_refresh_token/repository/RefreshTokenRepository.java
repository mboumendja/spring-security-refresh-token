package com.mboumendja.refresh_token.spring_security_refresh_token.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mboumendja.refresh_token.spring_security_refresh_token.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    boolean existsByToken(String token);
    Optional<RefreshToken> findByToken(String token);
}
