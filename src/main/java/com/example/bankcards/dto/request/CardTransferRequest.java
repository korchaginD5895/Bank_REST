package com.example.bankcards.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CardTransferRequest(
        @NotBlank(message = "Source card number is required")
        String sourceCardNumber,

        @NotBlank(message = "Destination card number is required")
        String destinationCardNumber,

        @NotNull(message = "Amount is required")
        @DecimalMin(value = "0.01", message = "Amount must be positive")
        BigDecimal amount
) {}
