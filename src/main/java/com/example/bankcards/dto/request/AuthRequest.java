package com.example.bankcards.dto.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * DTO for authentication request.
 */
public record AuthRequest(
        @NotNull @Size(min = 3, max = 50) String username,
        @NotNull @Size(min = 6, max = 255) String password
) {}