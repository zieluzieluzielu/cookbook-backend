package com.mzielinski.cookbook.service;

import com.mzielinski.cookbook.config.AdminConfig;
import com.mzielinski.cookbook.domain.Mail;
import com.mzielinski.cookbook.domain.Recipe;
import com.mzielinski.cookbook.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private SimpleEmailService emailService;

    @Autowired
    private AdminConfig adminConfig;

    private static final String SUBJECT = "CookBook: New Recipe Added";

    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    public List<Recipe> getRecipesByCategory(Long recipeCategoryId) {
        return recipeRepository.retrieveRecipesByCategoryId(recipeCategoryId);
    }

    public List<Recipe> getRecipesByPreparationTimeShorterThan(Long preparationTime) {
        return recipeRepository.retrieveRecipesByPreparationTimeShorterThan(preparationTime);
    }

    public List<Recipe> getRecipesByProduct(Long productId) {
        return recipeRepository.retrieveRecipesByProduct(productId);
    }

    public List<Recipe> getRecipesByUser(Long userId) {
        return recipeRepository.retrieveRecipesByUser(userId);
    }



    public Recipe saveRecipe(final Recipe recipe) {
        Recipe newRecipe = recipeRepository.save(recipe);

        ofNullable(newRecipe).ifPresent(rec -> emailService.send(new Mail((
               adminConfig.getAdminMail()),
                SUBJECT,
                "New recipe: " + rec.getRecipeName() + " has been added to your CookBook"), EmailType.NEW_RECIPE));

        return newRecipe;
    }

    public Optional<Recipe> getRecipe(final Long id) {
        return recipeRepository.findById(id);
    }

/*
    public Recipe getRandomRecipe(){
        return recipeRepository.retrieveRandomRecipe().orElseThrow(() -> new RecipeNotFoundException("Recipe not found"));
    }
*/

    public void deleteRecipe(final Long id) {
        recipeRepository.deleteById(id);
    }


}
