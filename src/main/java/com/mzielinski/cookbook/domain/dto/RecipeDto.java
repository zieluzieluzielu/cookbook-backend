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

}
