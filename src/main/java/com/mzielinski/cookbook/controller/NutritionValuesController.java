package com.mzielinski.cookbook.controller;

import com.mzielinski.cookbook.domain.Ingredient;
import com.mzielinski.cookbook.domain.Recipe;
import com.mzielinski.cookbook.domain.dto.EdamamDto;
import com.mzielinski.cookbook.edamam.facade.EdamamFacade;
import com.mzielinski.cookbook.exception.IngredientNotFoundException;
import com.mzielinski.cookbook.exception.RecipeCategoryNotFoundException;
import com.mzielinski.cookbook.exception.RecipeNotFoundException;
import com.mzielinski.cookbook.service.IngredientService;
import com.mzielinski.cookbook.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1")
public class NutritionValuesController {
    private static final Logger LOGGER = LoggerFactory.getLogger(NutritionValuesController.class);

    @Autowired
    private IngredientService ingredientService;
    @Autowired
    private RecipeService recipeService;
    @Autowired
    private EdamamFacade edamamFacade;

    @RequestMapping(method = RequestMethod.GET, value = "/nutrition/{ingredientId}")
    public EdamamDto getNutrition(@PathVariable Long ingredientId) throws IngredientNotFoundException {

        Ingredient ingredient = ingredientService.getIngredient(ingredientId).orElseThrow(() -> new IngredientNotFoundException("Ingredient with id: " + ingredientId + " was not found"));

        return edamamFacade.getNutrition(ingredient);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/nutrition/recipe/{recipeId}")
    public List<EdamamDto> getNutritionOfEachIngredientInRecipe(@PathVariable Long recipeId) throws RecipeNotFoundException {
        Recipe recipe = recipeService.getRecipe(recipeId).orElseThrow(() -> new RecipeCategoryNotFoundException("Recipe with id: " + recipeId + " was not found"));

        List<Ingredient> ingredientsList = recipe.getIngredients();

        return edamamFacade.getNutritionList(ingredientsList);

    }

    @RequestMapping(method = RequestMethod.GET, value = "/nutrition/recipe/sum/{recipeId}")
    public EdamamDto getNutritionSum(@PathVariable Long recipeId) throws RecipeNotFoundException {
        Recipe recipe = recipeService.getRecipe(recipeId).orElseThrow(() -> new RecipeCategoryNotFoundException("Recipe with id: " + recipeId + " was not found"));

        List<Ingredient> ingredientsList = recipe.getIngredients();
        return edamamFacade.getSumOfNutrition(ingredientsList);

    }




}
