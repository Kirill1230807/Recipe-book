package com.bank.recipebook.dto.recipe;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.util.List;

public record UpdateRecipeRequest(

        @NotBlank
        @Size(max = 255)
        String title,

        String description,

        @NotBlank
        String instructions,

        @Positive
        Integer prepTime,

        @Positive
        Long categoryId,

        @Valid
        List<RecipeIngredientRequest> ingredients
) {
}