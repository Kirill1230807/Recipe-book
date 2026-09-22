package com.bank.recipebook.repository;

import com.bank.recipebook.model.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeIngredientRepository
        extends JpaRepository<RecipeIngredient, Long> {

    List<RecipeIngredient> findByRecipeId(Long recipeId);

    boolean existsByRecipeIdAndIngredientId(
            Long recipeId,
            Long ingredientId
    );
}