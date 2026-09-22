package com.bank.recipebook.dto.ingredient;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateIngredientRequest(

        @NotBlank
        @Size(max = 150)
        String name
) {
}