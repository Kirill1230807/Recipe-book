package com.bank.recipebook.dto.recipe;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateRecipeRequest(

        @NotBlank
        @Size(max = 255)
        String title,

        String description,

        @NotBlank
        String instructions,

        @Positive
        Integer prepTime,

        @NotNull
        @Positive
        Long authorId,

        @Positive
        Long categoryId,

        @Valid
        List<RecipeIngredientRequest> ingredients
) {
}