package com.example.bankcards.dto.request;


import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * DTO for card creation request.
 */
public record CardCreateRequest(
        @NotNull Long ownerId,
        @NotNull LocalDate expiryDate
) {}
