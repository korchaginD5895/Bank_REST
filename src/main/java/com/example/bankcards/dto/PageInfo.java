package com.example.bankcards.dto;

/**
 * DTO for pagination info.
 */
public record PageInfo(
        int page,
        int size,
        long totalElements,
        int totalPages
) {}
