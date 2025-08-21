package com.mboumendja.refresh_token.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotBlank(message = "username required")
    private String username;

    @NotBlank(message = "password required")
    private String password;
}
