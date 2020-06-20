package com.mzielinski.cookbook.controller;

import com.mzielinski.cookbook.domain.dto.IngredientDto;
import com.mzielinski.cookbook.exception.IngredientNotFoundException;
import com.mzielinski.cookbook.exception.RecipeNotFoundException;
import com.mzielinski.cookbook.mapper.IngredientMapper;
import com.mzielinski.cookbook.service.IngredientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1")
public class IngredientController {

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private IngredientMapper ingredientMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(IngredientController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/ingredients")
    public List<IngredientDto> getIngredients() {
        return ingredientMapper.mapToIngredientsDtoList(ingredientService.getAllIngredients());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ingredients/recipes/{recipeId}")
    public List<IngredientDto> getIngredientsFromRecipe(@PathVariable Long recipeId) throws RecipeNotFoundException {
        return ingredientMapper.mapToIngredientsDtoList(ingredientService.getIngredientsByRecipe(recipeId));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/ingredients/{ingredientId}")
    public IngredientDto getIngredient(@PathVariable Long ingredientId) throws IngredientNotFoundException {
        return ingredientMapper.mapToIngredientDto(ingredientService.getIngredient(ingredientId).orElseThrow(() -> new IngredientNotFoundException("Ingredient with id: "+ingredientId+" was not found")));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/ingredients", consumes = APPLICATION_JSON_VALUE)
    public IngredientDto createIngredient(@RequestBody IngredientDto ingredientDto) {
        LOGGER.info("Creating new Ingredient");
            return ingredientMapper.mapToIngredientDto(ingredientService.saveIngredient(ingredientMapper.mapToIngredient(ingredientDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/ingredients")
    public IngredientDto updateIngredient(@RequestBody IngredientDto ingredientDto) {
        LOGGER.info("Ingredient has been updated");
        return ingredientMapper.mapToIngredientDto(ingredientService.saveIngredient(ingredientMapper.mapToIngredient(ingredientDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "ingredients/{ingredientId}")
    public void deleteIngredient(@PathVariable Long ingredientId) {
        LOGGER.info("Deleting ingredient with id: " + ingredientId);
        ingredientService.deleteIngredient(ingredientId);
    }


}
