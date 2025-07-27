package com.example.bankcards.dto;

import com.example.bankcards.enums.CardStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public record CardDto(
        Long id,
        String maskedCardNumber,
        String ownerUsername,
        LocalDate expirationDate,
        CardStatus status,
        BigDecimal balance
) {}
