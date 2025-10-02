package com.application.bookstore.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Authentication request containing username and password")
public class AuthRequest {
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    @Schema(description = "Username", example = "admin")
    String username;

    @NotBlank(message = "Password is required")
    @Size(min = 6, max = 64, message = "Password must be between 6 and 64 characters")
    @Schema(description = "Password", example = "123456")
    String password;
}
