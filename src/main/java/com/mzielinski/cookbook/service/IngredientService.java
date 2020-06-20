package com.mzielinski.cookbook.service;

import com.mzielinski.cookbook.domain.Ingredient;
import com.mzielinski.cookbook.repository.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class IngredientService {

    @Autowired
    private IngredientRepository ingredientRepository;

    public List<Ingredient> getAllIngredients() {
        return ingredientRepository.findAll();
    }

    public List<Ingredient> getIngredientsByRecipe(Long recipeId) {
        return ingredientRepository.retrieveIngredientByRecipe(recipeId);
    }

    public Ingredient saveIngredient(final Ingredient ingredient) {
        return ingredientRepository.save(ingredient);
    }

    public Optional<Ingredient> getIngredient(final Long id) {
        return ingredientRepository.findById(id);
    }

    public void deleteIngredient(final Long id) {
        ingredientRepository.deleteById(id);
    }

    public String ingredientToAnalyze(final Ingredient ingredient) {
        return (ingredient.getAmount() + " " + ingredient.getUnit() + " " + ingredient.getProduct().getProductName());


    }


}
