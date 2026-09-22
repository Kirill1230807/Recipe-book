package com.bank.recipebook.repository;

import com.bank.recipebook.model.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeRepository extends JpaRepository<Recipe, Long> {

    List<Recipe> findByAuthorId(Long authorId);

    List<Recipe> findByCategoryId(Long categoryId);

    List<Recipe> findByTitleContainingIgnoreCase(String title);

    List<Recipe> findByAuthorIdAndCategoryId(
            Long authorId,
            Long categoryId
    );
}