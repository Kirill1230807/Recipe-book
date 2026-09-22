package com.bank.recipebook.service;

import com.bank.recipebook.dto.ingredient.CreateIngredientRequest;
import com.bank.recipebook.dto.ingredient.IngredientResponse;
import com.bank.recipebook.dto.ingredient.UpdateIngredientRequest;
import com.bank.recipebook.exception.ResourceAlreadyExistsException;
import com.bank.recipebook.exception.ResourceNotFoundException;
import com.bank.recipebook.model.Ingredient;
import com.bank.recipebook.repository.IngredientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class IngredientService {

    private final IngredientRepository ingredientRepository;

    public List<IngredientResponse> findAll() {
        return ingredientRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public IngredientResponse findById(Long id) {
        return toResponse(getIngredientById(id));
    }

    @Transactional
    public IngredientResponse create(CreateIngredientRequest request) {

        if (ingredientRepository.existsByName(request.name())) {
            throw new ResourceAlreadyExistsException(
                    "Ingredient with name " + request.name() + " already exists"
            );
        }

        Ingredient ingredient = Ingredient.builder()
                .name(request.name())
                .build();

        return toResponse(ingredientRepository.save(ingredient));
    }

    @Transactional
    public IngredientResponse update(
            Long id,
            UpdateIngredientRequest request
    ) {

        Ingredient ingredient = getIngredientById(id);

        if (!ingredient.getName().equals(request.name())
                && ingredientRepository.existsByName(request.name())) {

            throw new ResourceAlreadyExistsException(
                    "Ingredient with name " + request.name() + " already exists"
            );
        }

        ingredient.setName(request.name());

        return toResponse(ingredient);
    }

    @Transactional
    public void delete(Long id) {
        Ingredient ingredient = getIngredientById(id);
        ingredientRepository.delete(ingredient);
    }

    private Ingredient getIngredientById(Long id) {
        return ingredientRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Ingredient with id " + id + " not found"
                        )
                );
    }

    private IngredientResponse toResponse(Ingredient ingredient) {
        return new IngredientResponse(
                ingredient.getId(),
                ingredient.getName()
        );
    }
}