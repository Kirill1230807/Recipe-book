package com.bank.recipebook.dto.recipe;

import java.time.LocalDateTime;
import java.util.List;

public record RecipeResponse(
        Long id,
        String title,
        String description,
        String instructions,
        Integer prepTime,
        Long authorId,
        String authorUsername,
        Long categoryId,
        String categoryName,
        LocalDateTime createdAt,
        List<RecipeIngredientResponse> ingredients
) {
}