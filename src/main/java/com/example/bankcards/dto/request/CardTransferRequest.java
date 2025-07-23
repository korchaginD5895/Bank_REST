package com.example.bankcards.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * DTO for transferring funds between cards.
 */
public record CardTransferRequest(
        @NotNull Long fromCardId,
        @NotNull Long toCardId,
        @NotNull @DecimalMin("0.01") BigDecimal amount
) {}
