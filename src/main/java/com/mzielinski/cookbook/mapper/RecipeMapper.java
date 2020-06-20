package com.mzielinski.cookbook.mapper;

import com.mzielinski.cookbook.domain.Recipe;
import com.mzielinski.cookbook.domain.dto.RecipeDto;
import com.mzielinski.cookbook.exception.RecipeCategoryNotFoundException;
import com.mzielinski.cookbook.exception.UserNotFoundException;
import com.mzielinski.cookbook.repository.RecipeCategoryRepository;
import com.mzielinski.cookbook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RecipeMapper {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecipeCategoryRepository recipeCategoryRepository;

    public Recipe mapToRecipe(final RecipeDto recipeDto) throws RecipeCategoryNotFoundException, UserNotFoundException {
        return new Recipe(
                recipeDto.getRecipeName(),
                recipeDto.getRecipeDetails(),
                recipeDto.getPreparationTime(),
                recipeCategoryRepository.findById(recipeDto.getRecipeCategoryId()).orElseThrow(() -> new RecipeCategoryNotFoundException("Recipe category was not found")),
                userRepository.findById(recipeDto.getUserId()).orElseThrow(() -> new UserNotFoundException("User was not found"))
        );

    }

    public RecipeDto mapToRecipeDto(final Recipe recipe) {
        return new RecipeDto(
                recipe.getRecipeName(),
                recipe.getRecipeDetails(),
                recipe.getPreparationTime(),
                recipe.getRecipeCategory().getRecipeCategoryId(),
                recipe.getUser().getUserId());
    }

    public List<RecipeDto> mapToRecipesDtoList(final List<Recipe> recipesList) {
        return recipesList.stream()
                .map(this::mapToRecipeDto)
                .collect(Collectors.toList());

    }
}
