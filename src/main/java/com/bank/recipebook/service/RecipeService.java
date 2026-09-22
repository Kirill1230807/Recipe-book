package com.bank.recipebook.service;

import com.bank.recipebook.dto.recipe.CreateRecipeRequest;
import com.bank.recipebook.dto.recipe.RecipeIngredientRequest;
import com.bank.recipebook.dto.recipe.RecipeIngredientResponse;
import com.bank.recipebook.dto.recipe.RecipeResponse;
import com.bank.recipebook.dto.recipe.UpdateRecipeRequest;
import com.bank.recipebook.exception.ResourceNotFoundException;
import com.bank.recipebook.model.*;
import com.bank.recipebook.repository.CategoryRepository;
import com.bank.recipebook.repository.IngredientRepository;
import com.bank.recipebook.repository.RecipeRepository;
import com.bank.recipebook.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RecipeService {

    private final RecipeRepository recipeRepository;
    private final UserRepository userRepository;
    private final CategoryRepository categoryRepository;
    private final IngredientRepository ingredientRepository;

    public List<RecipeResponse> findAll() {
        return recipeRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public RecipeResponse findById(Long id) {
        return toResponse(getRecipeById(id));
    }

    public List<RecipeResponse> findByAuthorId(Long authorId) {
        return recipeRepository.findByAuthorId(authorId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<RecipeResponse> findByCategoryId(Long categoryId) {
        return recipeRepository.findByCategoryId(categoryId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public List<RecipeResponse> searchByTitle(String title) {
        return recipeRepository.findByTitleContainingIgnoreCase(title)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Transactional
    public RecipeResponse create(CreateRecipeRequest request) {

        User author = userRepository.findById(request.authorId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User with id " + request.authorId() + " not found"
                        )
                );

        Category category = null;

        if (request.categoryId() != null) {
            category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Category with id "
                                            + request.categoryId()
                                            + " not found"
                            )
                    );
        }

        Recipe recipe = Recipe.builder()
                .title(request.title())
                .description(request.description())
                .instructions(request.instructions())
                .prepTime(request.prepTime())
                .author(author)
                .category(category)
                .createdAt(LocalDateTime.now())
                .ingredients(new ArrayList<>())
                .build();

        addIngredients(recipe, request.ingredients());

        Recipe savedRecipe = recipeRepository.save(recipe);

        return toResponse(savedRecipe);
    }

    @Transactional
    public RecipeResponse update(
            Long id,
            UpdateRecipeRequest request
    ) {

        Recipe recipe = getRecipeById(id);

        Category category = null;

        if (request.categoryId() != null) {
            category = categoryRepository.findById(request.categoryId())
                    .orElseThrow(() ->
                            new ResourceNotFoundException(
                                    "Category with id "
                                            + request.categoryId()
                                            + " not found"
                            )
                    );
        }

        recipe.setTitle(request.title());
        recipe.setDescription(request.description());
        recipe.setInstructions(request.instructions());
        recipe.setPrepTime(request.prepTime());
        recipe.setCategory(category);

        recipe.getIngredients().clear();

        addIngredients(recipe, request.ingredients());

        return toResponse(recipe);
    }

    @Transactional
    public void delete(Long id) {

        Recipe recipe = getRecipeById(id);

        recipeRepository.delete(recipe);
    }

    private void addIngredients(
            Recipe recipe,
            List<RecipeIngredientRequest> ingredientRequests
    ) {

        if (ingredientRequests == null) {
            return;
        }

        for (RecipeIngredientRequest request : ingredientRequests) {

            Ingredient ingredient =
                    ingredientRepository.findById(request.ingredientId())
                            .orElseThrow(() ->
                                    new ResourceNotFoundException(
                                            "Ingredient with id "
                                                    + request.ingredientId()
                                                    + " not found"
                                    )
                            );

            RecipeIngredient recipeIngredient =
                    RecipeIngredient.builder()
                            .recipe(recipe)
                            .ingredient(ingredient)
                            .amount(request.amount())
                            .build();

            recipe.getIngredients().add(recipeIngredient);
        }
    }

    private Recipe getRecipeById(Long id) {
        return recipeRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Recipe with id " + id + " not found"
                        )
                );
    }

    private RecipeResponse toResponse(Recipe recipe) {

        List<RecipeIngredientResponse> ingredients =
                recipe.getIngredients()
                        .stream()
                        .map(recipeIngredient ->
                                new RecipeIngredientResponse(
                                        recipeIngredient
                                                .getIngredient()
                                                .getId(),

                                        recipeIngredient
                                                .getIngredient()
                                                .getName(),

                                        recipeIngredient.getAmount()
                                )
                        )
                        .toList();

        return new RecipeResponse(
                recipe.getId(),
                recipe.getTitle(),
                recipe.getDescription(),
                recipe.getInstructions(),
                recipe.getPrepTime(),
                recipe.getAuthor().getId(),
                recipe.getAuthor().getUsername(),
                recipe.getCategory() != null
                        ? recipe.getCategory().getId()
                        : null,
                recipe.getCategory() != null
                        ? recipe.getCategory().getName()
                        : null,
                recipe.getCreatedAt(),
                ingredients
        );
    }
}