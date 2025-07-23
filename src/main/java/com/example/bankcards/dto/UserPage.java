package com.example.bankcards.dto;

import java.util.List;

/**
 * DTO for paginated list of users.
 */
public record UserPage(
        List<UserDto> content,
        PageInfo pageInfo
) {}
