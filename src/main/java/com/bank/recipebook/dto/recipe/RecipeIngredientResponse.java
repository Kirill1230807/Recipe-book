package com.bank.recipebook.dto.recipe;

public record RecipeIngredientResponse(
        Long ingredientId,
        String ingredientName,
        String amount
) {
}