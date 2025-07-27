package com.example.bankcards.dto.request;

import com.example.bankcards.enums.CardStatus;
import jakarta.validation.constraints.NotNull;

public record CardStatusUpdateRequest(
        @NotNull(message = "Card ID is required")
        Long cardId,

        @NotNull(message = "New status is required")
        CardStatus newStatus
) {}
