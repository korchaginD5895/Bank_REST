package com.example.bankcards.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * DTO for card details.
 */
public record CardDto(
        @NotNull Long id,
        @NotNull @Pattern(regexp = "\\*{4} \\*{4} \\*{4} \\d{4}") String cardNumberMasked,
        @NotNull String owner,
        @NotNull Long ownerId,
        @NotNull LocalDate expiryDate,
        @NotNull String status,
        @NotNull BigDecimal balance
) {}
