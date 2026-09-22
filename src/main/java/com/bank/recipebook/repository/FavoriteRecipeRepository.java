package com.bank.recipebook.repository;

import com.bank.recipebook.model.FavoriteRecipe;
import com.bank.recipebook.model.FavoriteRecipeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRecipeRepository
        extends JpaRepository<FavoriteRecipe, FavoriteRecipeId> {

    List<FavoriteRecipe> findByUserId(Long userId);

    List<FavoriteRecipe> findByRecipeId(Long recipeId);

    boolean existsByUserIdAndRecipeId(
            Long userId,
            Long recipeId
    );

    void deleteByUserIdAndRecipeId(
            Long userId,
            Long recipeId
    );
}