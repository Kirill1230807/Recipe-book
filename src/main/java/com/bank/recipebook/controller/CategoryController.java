package com.bank.recipebook.controller;

import com.bank.recipebook.dto.category.CategoryResponse;
import com.bank.recipebook.dto.category.CreateCategoryRequest;
import com.bank.recipebook.dto.category.UpdateCategoryRequest;
import com.bank.recipebook.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
public class CategoryController {

    private final CategoryService categoryService;

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping
    public List<CategoryResponse> findAll() {
        return categoryService.findAll();
    }

    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    @GetMapping("/{id}")
    public CategoryResponse findById(
            @PathVariable Long id
    ) {
        return categoryService.findById(id);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryResponse create(
            @Valid @RequestBody CreateCategoryRequest request
    ) {
        return categoryService.create(request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public CategoryResponse update(
            @PathVariable Long id,
            @Valid @RequestBody UpdateCategoryRequest request
    ) {
        return categoryService.update(id, request);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(
            @PathVariable Long id
    ) {
        categoryService.delete(id);
    }
}