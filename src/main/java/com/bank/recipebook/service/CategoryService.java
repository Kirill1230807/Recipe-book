package com.bank.recipebook.service;

import com.bank.recipebook.dto.category.CategoryResponse;
import com.bank.recipebook.dto.category.CreateCategoryRequest;
import com.bank.recipebook.dto.category.UpdateCategoryRequest;
import com.bank.recipebook.exception.ResourceAlreadyExistsException;
import com.bank.recipebook.exception.ResourceNotFoundException;
import com.bank.recipebook.model.Category;
import com.bank.recipebook.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<CategoryResponse> findAll() {
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    public CategoryResponse findById(Long id) {
        return toResponse(getCategoryById(id));
    }

    @Transactional
    public CategoryResponse create(CreateCategoryRequest request) {

        if (categoryRepository.existsByName(request.name())) {
            throw new ResourceAlreadyExistsException(
                    "Category with name " + request.name() + " already exists"
            );
        }

        Category category = Category.builder()
                .name(request.name())
                .build();

        return toResponse(categoryRepository.save(category));
    }

    @Transactional
    public CategoryResponse update(
            Long id,
            UpdateCategoryRequest request
    ) {

        Category category = getCategoryById(id);

        if (!category.getName().equals(request.name())
                && categoryRepository.existsByName(request.name())) {

            throw new ResourceAlreadyExistsException(
                    "Category with name " + request.name() + " already exists"
            );
        }

        category.setName(request.name());

        return toResponse(category);
    }

    @Transactional
    public void delete(Long id) {
        Category category = getCategoryById(id);
        categoryRepository.delete(category);
    }

    private Category getCategoryById(Long id) {
        return categoryRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Category with id " + id + " not found"
                        )
                );
    }

    private CategoryResponse toResponse(Category category) {
        return new CategoryResponse(
                category.getId(),
                category.getName()
        );
    }
}