package com.mzielinski.cookbook.mapper;

import com.mzielinski.cookbook.domain.RecipeCategory;
import com.mzielinski.cookbook.domain.dto.RecipeCategoryDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeCategoryMapper {

    public RecipeCategory mapToRecipeCategory(final RecipeCategoryDto recipeCategoryDto) {
        return new RecipeCategory(
                recipeCategoryDto.getRecipeCategoryName());
    }

    public RecipeCategoryDto mapToRecipeCategoryDto(final RecipeCategory recipeCategory) {
        return new RecipeCategoryDto(
                recipeCategory.getRecipeCategoryId(),
                recipeCategory.getRecipeCategoryName());
    }

    public List<RecipeCategoryDto> mapToRecipeCategoriesDtoList(final List<RecipeCategory> recipeCategoriesList) {
        return recipeCategoriesList.stream()
                .map(this::mapToRecipeCategoryDto)
                .collect(Collectors.toList());
    }

}
