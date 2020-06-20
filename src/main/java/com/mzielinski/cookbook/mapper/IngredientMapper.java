package com.mzielinski.cookbook.mapper;

import com.mzielinski.cookbook.domain.Ingredient;
import com.mzielinski.cookbook.domain.dto.IngredientDto;
import com.mzielinski.cookbook.exception.ProductNotFoundException;
import com.mzielinski.cookbook.exception.RecipeNotFoundException;
import com.mzielinski.cookbook.repository.ProductRepository;
import com.mzielinski.cookbook.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class IngredientMapper {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private RecipeRepository recipeRepository;


    public Ingredient mapToIngredient(final IngredientDto ingredientDto) throws ProductNotFoundException, RecipeNotFoundException {
        return new Ingredient(
                ingredientDto.getAmount(),
                ingredientDto.getUnit(),
                productRepository.findById(ingredientDto.getProductId()).orElseThrow(() -> new ProductNotFoundException("Product was not found")),
                recipeRepository.findById(ingredientDto.getRecipeId()).orElseThrow(() -> new RecipeNotFoundException("Recipe was not found")),
                ingredientDto.isMainProduct()
        );
    }


    public IngredientDto mapToIngredientDto(final Ingredient ingredient) {
        return new IngredientDto(
                ingredient.getAmount(),
                ingredient.getUnit(),
                ingredient.getProduct().getProductId(),
                ingredient.getRecipe().getRecipeId(),
                ingredient.isMainProduct()
        );
    }

    public List<IngredientDto> mapToIngredientsDtoList(final List<Ingredient> ingredientsList) {
        return ingredientsList.stream()
                .map(this::mapToIngredientDto)
                .collect(Collectors.toList());
    }

}
