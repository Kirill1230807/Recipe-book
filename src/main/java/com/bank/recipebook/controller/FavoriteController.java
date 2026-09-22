package com.bank.recipebook.controller;

import com.bank.recipebook.dto.favorite.FavoriteResponse;
import com.bank.recipebook.service.FavoriteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{userId}/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteService favoriteService;

    @GetMapping
    public List<FavoriteResponse> findByUserId(
            @PathVariable Long userId
    ) {
        return favoriteService.findByUserId(userId);
    }

    @GetMapping("/{recipeId}/exists")
    public boolean exists(
            @PathVariable Long userId,
            @PathVariable Long recipeId
    ) {
        return favoriteService.exists(userId, recipeId);
    }

    @PostMapping("/{recipeId}")
    @ResponseStatus(HttpStatus.CREATED)
    public FavoriteResponse addToFavorites(
            @PathVariable Long userId,
            @PathVariable Long recipeId
    ) {
        return favoriteService.addToFavorites(userId, recipeId);
    }

    @DeleteMapping("/{recipeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFromFavorites(
            @PathVariable Long userId,
            @PathVariable Long recipeId
    ) {
        favoriteService.removeFromFavorites(userId, recipeId);
    }
}