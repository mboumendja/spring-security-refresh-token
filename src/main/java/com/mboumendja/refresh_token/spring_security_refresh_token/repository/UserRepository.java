package com.mboumendja.refresh_token.spring_security_refresh_token.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mboumendja.refresh_token.spring_security_refresh_token.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUsername(String username);
    Optional<User> findByUsername(String username);
}
