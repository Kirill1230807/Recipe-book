package com.bank.recipebook.controller;

import com.bank.recipebook.dto.ingredient.CreateIngredientRequest;
import com.bank.recipebook.dto.ingredient.IngredientResponse;
import com.bank.recipebook.dto.ingredient.UpdateIngredientRequest;
import com.bank.recipebook.service.IngredientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ingredients")
@RequiredArgsConstructor
public class IngredientController {

    private final IngredientService ingredientService;

    @GetMapping
    public List<IngredientResponse> findAll() {
        return ingredientService.findAll();
    }

    @GetMapping("/{id}")
    public IngredientResponse findById(
            @PathVariable Long id
    ) {
        return ingredientService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IngredientResponse create(
            @Valid @RequestBody CreateIngredientRequest request
    ) {
        return ingredientService.create(request);
    }

    @PutMapping("/{id}")
    public IngredientResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateIngredientRequest request
    ) {
        return ingredientService.update(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {
        ingredientService.delete(id);
    }
}