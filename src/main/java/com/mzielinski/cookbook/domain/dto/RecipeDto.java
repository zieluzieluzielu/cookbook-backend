package com.mzielinski.cookbook.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RecipeDto {

    private Long recipeId;
    private String recipeName;
    private String recipeDetails;
    private Long preparationTime;
    private Long recipeCategoryId;
    private Long userId;

    public RecipeDto(String recipeName, String recipeDetails, Long preparationTime, Long recipeCategoryId, Long userId) {
        this.recipeName = recipeName;
        this.recipeDetails = recipeDetails;
        this.preparationTime = preparationTime;
        this.recipeCategoryId = recipeCategoryId;
        this.userId = userId;
    }
}
