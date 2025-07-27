package com.example.bankcards.dto.request;

import com.example.bankcards.enums.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserCreateRequest(
        @NotBlank(message = "Username is required")
        String username,

        @NotBlank(message = "Password is required")
        String password,

        @NotNull(message = "Role is required")
        Role role
) {}
