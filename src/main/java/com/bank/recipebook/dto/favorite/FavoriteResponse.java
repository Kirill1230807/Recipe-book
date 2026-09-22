package com.bank.recipebook.dto.favorite;

import java.time.LocalDateTime;

public record FavoriteResponse(
        Long recipeId,
        String recipeTitle,
        Long userId,
        LocalDateTime addedAt
) {}