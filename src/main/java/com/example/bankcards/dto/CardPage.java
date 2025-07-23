package com.example.bankcards.dto;

import java.util.List;

/**
 * DTO for paginated list of cards.
 */
public record CardPage(
        List<CardDto> content,
        PageInfo pageInfo
) {}
