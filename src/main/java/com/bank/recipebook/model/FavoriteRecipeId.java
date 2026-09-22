package com.bank.recipebook.model;

import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FavoriteRecipeId implements Serializable {

    private Long userId;

    private Long recipeId;
}