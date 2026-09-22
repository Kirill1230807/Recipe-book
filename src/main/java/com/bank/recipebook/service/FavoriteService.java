package com.bank.recipebook.service;

import com.bank.recipebook.dto.favorite.FavoriteResponse;
import com.bank.recipebook.exception.ResourceAlreadyExistsException;
import com.bank.recipebook.exception.ResourceNotFoundException;
import com.bank.recipebook.model.*;
import com.bank.recipebook.repository.FavoriteRecipeRepository;
import com.bank.recipebook.repository.RecipeRepository;
import com.bank.recipebook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class FavoriteService {

    private final FavoriteRecipeRepository favoriteRecipeRepository;
    private final UserRepository userRepository;
    private final RecipeRepository recipeRepository;

    public List<FavoriteResponse> findByUserId(Long userId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User with id " + userId + " not found"
            );
        }

        return favoriteRecipeRepository.findByUserId(userId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public boolean exists(Long userId, Long recipeId) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User with id " + userId + " not found"
            );
        }

        if (!recipeRepository.existsById(recipeId)) {
            throw new ResourceNotFoundException(
                    "Recipe with id " + recipeId + " not found"
            );
        }

        return favoriteRecipeRepository
                .existsByUserIdAndRecipeId(userId, recipeId);
    }

    @Transactional
    public FavoriteResponse addToFavorites(
            Long userId,
            Long recipeId
    ) {

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User with id " + userId + " not found"
                        )
                );

        Recipe recipe = recipeRepository.findById(recipeId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Recipe with id " + recipeId + " not found"
                        )
                );

        if (favoriteRecipeRepository
                .existsByUserIdAndRecipeId(userId, recipeId)) {

            throw new ResourceAlreadyExistsException(
                    "Recipe is already in favorites"
            );
        }

        FavoriteRecipe favoriteRecipe = FavoriteRecipe.builder()
                .id(new FavoriteRecipeId(userId, recipeId))
                .user(user)
                .recipe(recipe)
                .addedAt(LocalDateTime.now())
                .build();

        FavoriteRecipe savedFavorite =
                favoriteRecipeRepository.save(favoriteRecipe);

        return toResponse(savedFavorite);
    }

    @Transactional
    public void removeFromFavorites(
            Long userId,
            Long recipeId
    ) {

        if (!userRepository.existsById(userId)) {
            throw new ResourceNotFoundException(
                    "User with id " + userId + " not found"
            );
        }

        if (!recipeRepository.existsById(recipeId)) {
            throw new ResourceNotFoundException(
                    "Recipe with id " + recipeId + " not found"
            );
        }

        if (!favoriteRecipeRepository
                .existsByUserIdAndRecipeId(userId, recipeId)) {

            throw new IllegalArgumentException(
                    "Recipe is not in favorites"
            );
        }

        favoriteRecipeRepository.deleteByUserIdAndRecipeId(
                userId,
                recipeId
        );
    }

    private FavoriteResponse toResponse(
            FavoriteRecipe favoriteRecipe
    ) {
        return new FavoriteResponse(
                favoriteRecipe.getRecipe().getId(),
                favoriteRecipe.getRecipe().getTitle(),
                favoriteRecipe.getUser().getId(),
                favoriteRecipe.getAddedAt()
        );
    }
}