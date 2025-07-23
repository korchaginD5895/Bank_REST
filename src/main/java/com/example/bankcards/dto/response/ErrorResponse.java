package com.example.bankcards.dto.response;

import java.time.LocalDateTime;

/**
 * DTO for error response.
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        String path
) {}
