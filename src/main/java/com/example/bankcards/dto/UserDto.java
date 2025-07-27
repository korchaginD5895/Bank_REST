package com.example.bankcards.dto;

import com.example.bankcards.enums.Role;

public record UserDto(
        Long id,
        String username,
        Role role
) {}
