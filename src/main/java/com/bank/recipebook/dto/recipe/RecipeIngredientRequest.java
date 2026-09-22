package com.bank.recipebook.dto.recipe;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record RecipeIngredientRequest(

        @NotNull
        @Positive
        Long ingredientId,

        @NotBlank
        String amount
) {
}