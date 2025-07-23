package com.example.bankcards.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for user creation request.
 */
public record UserCreateRequest(
        @NotNull @Size(min = 3, max = 50) String username,
        @NotNull @Size(min = 6, max = 255) String password,
        @NotNull @Size(min = 1, max = 100) String fullName,
        @NotNull @Email String email,
        @NotNull String role
) {}
