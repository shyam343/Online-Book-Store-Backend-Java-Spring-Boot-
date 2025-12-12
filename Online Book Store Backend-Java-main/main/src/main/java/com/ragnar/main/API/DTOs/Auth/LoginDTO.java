package com.ragnar.main.API.DTOs.Auth;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO {
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Username must not contain special characters")
    private String username;

    @NotBlank
    @Size(min = 5, max = 50, message = "Password must be between 5 and 50 characters")
    private String password;
}
