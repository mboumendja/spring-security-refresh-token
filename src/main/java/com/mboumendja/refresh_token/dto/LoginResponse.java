package com.mboumendja.refresh_token.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponse {
    @NotBlank
    private String username;
    @NotBlank
    private String role;
    @NotBlank
    private String accessToken;
    @NotBlank
    private String refresToken;
}
