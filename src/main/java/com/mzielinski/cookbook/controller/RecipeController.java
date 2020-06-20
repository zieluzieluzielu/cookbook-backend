package com.mzielinski.cookbook.controller;

import com.mzielinski.cookbook.domain.dto.RecipeDto;
import com.mzielinski.cookbook.exception.RecipeNotFoundException;
import com.mzielinski.cookbook.mapper.RecipeMapper;
import com.mzielinski.cookbook.service.RecipeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1")
public class RecipeController {
    @Autowired
    private RecipeService recipeService;

    @Autowired
    private RecipeMapper recipeMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/recipes")
    public List<RecipeDto> getRecipes() {
        return recipeMapper.mapToRecipesDtoList(recipeService.getAllRecipes());
    }


    @RequestMapping(method = RequestMethod.GET, value = "/recipes/{recipeId}")
    public RecipeDto getRecipe(@PathVariable Long recipeId) throws RecipeNotFoundException {
        return recipeMapper.mapToRecipeDto(recipeService.getRecipe(recipeId).orElseThrow(() -> new RecipeNotFoundException("Recipe with id: " + recipeId + " was not found")));
    }

    @RequestMapping(method = RequestMethod.GET, value = "recipes/preparationtime/{preparationTime}")
    public List<RecipeDto> getRecipesByPreparationTimeShorterThan(@PathVariable Long preparationTime) {
        return recipeMapper.mapToRecipesDtoList(recipeService.getRecipesByPreparationTimeShorterThan(preparationTime));
    }


    @RequestMapping(method = RequestMethod.GET, value = "recipes/recipecategory/{recipeCategoryId}")
    public List<RecipeDto> getRecipesByCategory(@PathVariable Long recipeCategoryId) {
        return recipeMapper.mapToRecipesDtoList(recipeService.getRecipesByCategory(recipeCategoryId));
    }


    @RequestMapping(method = RequestMethod.GET, value = "recipes/product/{productId}")
    public List<RecipeDto> getRecipesByProduct(@PathVariable Long productId) {
        return recipeMapper.mapToRecipesDtoList(recipeService.getRecipesByProduct(productId));
    }

    /*@RequestMapping(method = RequestMethod.GET, value = "/recipes/")
    public RecipeDto getRandomRecipe() {
        return recipeMapper.mapToRecipeDto(recipeService.getRandomRecipe().orElseThrow(() -> new RecipeNotFoundException("Random recipe was not found")));
    }*/

    @RequestMapping(method = RequestMethod.POST, value = "/recipes", consumes = APPLICATION_JSON_VALUE)
    public RecipeDto createRecipe(@RequestBody RecipeDto recipeDto) {
        LOGGER.info("Creating new Recipe");
        return recipeMapper.mapToRecipeDto(recipeService.saveRecipe(recipeMapper.mapToRecipe(recipeDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/recipes")
    public RecipeDto updateRecipe(@RequestBody RecipeDto recipeDto) {
        LOGGER.info("Recipe has been updated");
        return recipeMapper.mapToRecipeDto(recipeService.saveRecipe(recipeMapper.mapToRecipe(recipeDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "recipes/{recipeId}")
    public void deleteRecipe(@PathVariable Long recipeId) {
        LOGGER.info("Deleting recipe with id: " + recipeId);
        recipeService.deleteRecipe(recipeId);
    }


}
