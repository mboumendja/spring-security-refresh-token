package com.mboumendja.refresh_token.spring_security_refresh_token.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class RegisterRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank
    private String role;
}
