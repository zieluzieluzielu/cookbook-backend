package com.mzielinski.cookbook.service;

import com.mzielinski.cookbook.domain.*;
import com.mzielinski.cookbook.repository.IngredientRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class IngredientServiceTest {
    @InjectMocks
    IngredientService ingredientService;
    @Mock
    IngredientRepository ingredientRepository;

    @Test
    public void getAllIngredients() {
        //Given
        List<Ingredient> ingredientsList = new ArrayList<>();
        Ingredient ingredient1 = new Ingredient();
        ingredientsList.add(ingredient1);

        when(ingredientRepository.findAll()).thenReturn(ingredientsList);

        //When
        List<Ingredient> allIngredients = ingredientService.getAllIngredients();

        //Then
        assertEquals(1, allIngredients.size());

    }

    @Test
    public void getIngredientById() {
        //Given
        Ingredient ingredient1 = new Ingredient(1L, new BigDecimal(100), "ml", new Product(), new Recipe(), false);
        when(ingredientRepository.findById(1L)).thenReturn(Optional.of(ingredient1));
        //When
        Optional<Ingredient> getIngredient = ingredientService.getIngredient(1L);
        //Then
        assertTrue(getIngredient.isPresent());
        assertEquals(new BigDecimal(100), getIngredient.get().getAmount());
        assertEquals("ml", getIngredient.get().getUnit());

    }

    @Test
    public void getIngredientsByRecipe() {
        //Given
        List<Ingredient> ingredientsList = new ArrayList<>();

        Recipe recipe1 = new Recipe(6L, "Chicken Curry", "Test details of recipe", 10L, new RecipeCategory(), new User(), new ArrayList<>());

        Ingredient ingredient1 = new Ingredient(1L, new BigDecimal(100), "ml", new Product(), recipe1, false);
        Ingredient ingredient2 = new Ingredient(2L, new BigDecimal(500), "g", new Product(), recipe1, true);
        ingredientsList.add(ingredient1);
        ingredientsList.add(ingredient2);

        when(ingredientRepository.retrieveIngredientByRecipe(6L)).thenReturn(ingredientsList);

        //When
        List<Ingredient> ingredientsByRecipe = ingredientService.getIngredientsByRecipe(recipe1.getRecipeId());

        //Then
        assertEquals(2, ingredientsByRecipe.size());

    }



    @Test
    public void saveIngredient() {
        //Given
        Ingredient ingredient1 = new Ingredient(1L, new BigDecimal(500), "g", new Product(), new Recipe(), false);
        when(ingredientRepository.save(ingredient1)).thenReturn(ingredient1);
        //When
        Ingredient savedIngredient = ingredientService.saveIngredient(ingredient1);
        //Then
        assertEquals(new BigDecimal(500), savedIngredient.getAmount());

    }

    @Test
    public void deleteIngredient() {
        //Given
        Ingredient ingredient1 = new Ingredient(1L, new BigDecimal(100), "ml", new Product(), new Recipe(), false);
        //When
        ingredientService.deleteIngredient(1L);

        //Then
        verify(ingredientRepository, times(1)).deleteById(anyLong());
        assertFalse(ingredientRepository.findById(ingredient1.getIngredientPortionId()).isPresent());

    }

    @Test
    public void  ingredientToAnalyzeTest(){
        //Given
        Ingredient ingredient = new Ingredient(1L, new BigDecimal(100), "g", new Product(1L, "Chicken breast", new ProductGroup(6L, "Meat", new ArrayList<>()), new ArrayList<>()), new Recipe(), false);
        //When
        String ingredientToAnalyze = ingredient.getAmount() + " " + ingredient.getUnit() + " " + ingredient.getProduct().getProductName();

        System.out.println(ingredientToAnalyze);


        //Then

        assertEquals(ingredientToAnalyze, "100 g Chicken breast");
    }
}