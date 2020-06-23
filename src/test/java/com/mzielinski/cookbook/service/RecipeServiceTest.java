package com.mzielinski.cookbook.service;

import com.mzielinski.cookbook.config.AdminConfig;
import com.mzielinski.cookbook.domain.Recipe;
import com.mzielinski.cookbook.domain.RecipeCategory;
import com.mzielinski.cookbook.domain.User;
import com.mzielinski.cookbook.repository.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RecipeServiceTest {

    @InjectMocks
    RecipeService recipeService;

    @Mock
    RecipeRepository recipeRepository;

    @Mock
    SimpleEmailService emailService;

    @Mock
    AdminConfig adminConfig;

    @Test
    public void getAllRecipes() {
        //Given
        List<Recipe> recipeList = new ArrayList<>();
        Recipe recipe = new Recipe();
        recipeList.add(recipe);

        when(recipeRepository.findAll()).thenReturn(recipeList);

        //When
        List<Recipe> allRecipes = recipeService.getAllRecipes();

        //Then
        assertEquals(1, allRecipes.size());

    }

    @Test
    public void getRecipesWithSpecificPreparationTime() {
        //Given
        List<Recipe> recipeList = new ArrayList<>();
        Recipe recipe1 = new Recipe(1L, "Chicken Curry", "Test details of recipe 1", 10L, new RecipeCategory(), new User(), new ArrayList<>());
        Recipe recipe2 = new Recipe(2L, "Tikka Masala", "Test details of recipe 2", 14L, new RecipeCategory(), new User(), new ArrayList<>());
        recipeList.add(recipe1);
        recipeList.add(recipe2);
        when(recipeRepository.retrieveRecipesByPreparationTimeShorterThan(15L)).thenReturn(recipeList);

        //When
        List<Recipe> recipesWithSpecificPreparationTime = recipeService.getRecipesByPreparationTimeShorterThan(15L);

        //Then
        assertEquals(2, recipesWithSpecificPreparationTime.size());

    }

    @Test
    public void getRecipesFromSpecificCategory() {
        //Given
        RecipeCategory recipeCategory = new RecipeCategory(1L, "Indian cuisine", new ArrayList<>());
        List<Recipe> recipeList = new ArrayList<>();
        Recipe recipe1 = new Recipe(1L, "Chicken Curry", "Test details of recipe 1", 10L, recipeCategory, new User(), new ArrayList<>());
        Recipe recipe2 = new Recipe(2L, "Tikka Masala", "Test details of recipe 2", 14L, recipeCategory, new User(), new ArrayList<>());
        recipeList.add(recipe1);
        recipeList.add(recipe2);
        when(recipeRepository.retrieveRecipesByCategoryId(1L)).thenReturn(recipeList);

        //When
        List<Recipe> recipesFromSpecificCategory = recipeService.getRecipesByCategory(1L);

        //Then
        assertEquals(2, recipesFromSpecificCategory.size());

    }

    @Test
    public void getRecipesByProduct() {
        //Given
        List<Recipe> recipeList = new ArrayList<>();
        Recipe tikkaMasala = new Recipe(1L, "Tikka Masala", "Test details of recipe 1", 10L, new RecipeCategory(), new User(), new ArrayList<>());
        recipeList.add(tikkaMasala);

        when(recipeRepository.retrieveRecipesByProduct(66L)).thenReturn(recipeList);

        //When
        List<Recipe> recipesByProductList = recipeService.getRecipesByProduct(66L);

        //Then
        assertEquals(1, recipesByProductList.size());

    }

    @Test
    public void getRecipesByUser() {
        //Given
        List<Recipe> recipeList = new ArrayList<>();
        Recipe tikkaMasala = new Recipe(1L, "Tikka Masala", "Test details of recipe 1", 10L, new RecipeCategory(), new User(), new ArrayList<>());
        recipeList.add(tikkaMasala);

        when(recipeRepository.retrieveRecipesByUser(66L)).thenReturn(recipeList);

        //When
        List<Recipe> recipesByProductList = recipeService.getRecipesByUser(66L);

        //Then
        assertEquals(1, recipesByProductList.size());

    }



    @Test
    public void getRecipe() {
        //Given
        Recipe recipe = new Recipe(1L, "Chicken Curry", "Test details of recipe", 10L, new RecipeCategory(), new User(), new ArrayList<>());

        when(recipeRepository.findById(1L)).thenReturn(Optional.of(recipe));

        //When
        Optional<Recipe> getRecipe = recipeService.getRecipe(1L);
        //Then
        assertTrue(getRecipe.isPresent());
        assertEquals("Chicken Curry", getRecipe.get().getRecipeName());
        assertEquals("Test details of recipe", getRecipe.get().getRecipeDetails());
    }

/*
    @Test
    public void getRandomRecipe() {
        //Given
        Recipe recipe = new Recipe(1L, "Chicken Curry", "Test details of recipe", 10L, new RecipeCategory(), new User(), new ArrayList<>());

        when(recipeRepository.retrieveRandomRecipe()).thenReturn(Optional.of(recipe));

        //When
        Optional<Recipe> getRandomRecipe = recipeService.getRandomRecipe();
        //Then
        assertTrue(getRandomRecipe.isPresent());
        assertEquals("Chicken Curry", getRandomRecipe.get().getRecipeName());
    }
*/


    @Test
    public void saveRecipe() {
        //Given
        Recipe recipe = new Recipe(1L, "Chicken Curry", "Test details of recipe", 10L, new RecipeCategory(), new User(), new ArrayList<>());

        when(recipeRepository.save(recipe)).thenReturn(recipe);

        when(adminConfig.getAdminMail()).thenReturn("test@test.pl");
        //When
        Recipe savedRecipe = recipeService.saveRecipe(recipe);

        //Then
        assertEquals("Chicken Curry", savedRecipe.getRecipeName());
        assertEquals("Test details of recipe", savedRecipe.getRecipeDetails());
    }

    @Test
    public void deleteRecipe() {
        //Given
        Recipe recipe = new Recipe(1L, "Chicken Curry", "Test details of recipe", 10L, new RecipeCategory(), new User(), new ArrayList<>());

        //When
        recipeService.deleteRecipe(1L);

        //Then
        verify(recipeRepository, times(1)).deleteById(anyLong());
        assertFalse(recipeRepository.findById(recipe.getRecipeId()).isPresent());

    }
}
