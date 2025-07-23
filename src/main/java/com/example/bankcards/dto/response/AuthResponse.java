package com.example.bankcards.dto.response;

/**
 * DTO for authentication response.
 */
public record AuthResponse(
        String accessToken,
        String refreshToken
) {}
