package com.bank.recipebook.dto.user;

import java.time.LocalDateTime;

public record UserResponse(
        Long id,
        String email,
        String username,
        LocalDateTime createdAt
) {
}