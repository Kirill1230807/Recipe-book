package com.bank.recipebook.controller;

import com.bank.recipebook.dto.recipe.CreateRecipeRequest;
import com.bank.recipebook.dto.recipe.RecipeResponse;
import com.bank.recipebook.dto.recipe.UpdateRecipeRequest;
import com.bank.recipebook.service.RecipeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recipes")
@RequiredArgsConstructor
public class RecipeController {

    private final RecipeService recipeService;

    @GetMapping
    public List<RecipeResponse> findAll() {
        return recipeService.findAll();
    }

    @GetMapping("/{id}")
    public RecipeResponse findById(
            @PathVariable Long id
    ) {
        return recipeService.findById(id);
    }

    @GetMapping("/author/{authorId}")
    public List<RecipeResponse> findByAuthorId(
            @PathVariable Long authorId
    ) {
        return recipeService.findByAuthorId(authorId);
    }

    @GetMapping("/category/{categoryId}")
    public List<RecipeResponse> findByCategoryId(
            @PathVariable Long categoryId
    ) {
        return recipeService.findByCategoryId(categoryId);
    }

    @GetMapping("/search")
    public List<RecipeResponse> searchByTitle(
            @RequestParam String title
    ) {
        return recipeService.searchByTitle(title);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RecipeResponse create(
            @Valid @RequestBody CreateRecipeRequest request
    ) {
        return recipeService.create(request);
    }

    @PutMapping("/{id}")
    public RecipeResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateRecipeRequest request
    ) {
        return recipeService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {
        recipeService.delete(id);
    }
}