package com.example.bankcards.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for user details.
 */
public record UserDto(
        @NotNull Long id,
        @NotNull @Size(min = 3, max = 50) String username,
        @NotNull @Size(min = 1, max = 100) String fullName,
        @NotNull @Email String email,
        @NotNull String role,
        @NotNull String status
) {}
