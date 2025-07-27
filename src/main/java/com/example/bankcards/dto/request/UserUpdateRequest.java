package com.example.bankcards.dto.request;

import com.example.bankcards.enums.Role;
import jakarta.validation.constraints.NotNull;

public record UserUpdateRequest(
        @NotNull(message = "User ID is required")
        Long id,

        String password,

        Role role
) {}