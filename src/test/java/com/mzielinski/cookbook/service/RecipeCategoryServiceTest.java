package com.mzielinski.cookbook.service;

import com.mzielinski.cookbook.domain.RecipeCategory;
import com.mzielinski.cookbook.repository.RecipeCategoryRepository;
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
public class RecipeCategoryServiceTest {

    @InjectMocks
    RecipeCategoryService recipeCategoryService;

    @Mock
    RecipeCategoryRepository recipeCategoryRepository;

    @Test
    public void getAllRecipeCategories() {
        //Given
        List<RecipeCategory> recipeCategoriesList = new ArrayList<>();
        RecipeCategory recipeCategory = new RecipeCategory();
        recipeCategoriesList.add(recipeCategory);

        when(recipeCategoryRepository.findAll()).thenReturn(recipeCategoriesList);

        //When
        List<RecipeCategory> allRecipeCategories = recipeCategoryService.getAllRecipeCategories();

        //Then
        assertEquals(1, allRecipeCategories.size());

    }

    @Test
    public void getRecipeCategory() {
        //Given
        RecipeCategory recipeCategory = new RecipeCategory(1L, "Pasta", new ArrayList<>());
        when(recipeCategoryRepository.findById(1L)).thenReturn(Optional.of(recipeCategory));

        //When
        Optional<RecipeCategory> getRecipeCategory = recipeCategoryService.getRecipeCategory(1L);
        //Then
        assertTrue(getRecipeCategory.isPresent());
        assertEquals("Pasta", getRecipeCategory.get().getRecipeCategoryName());
    }


    @Test
    public void saveRecipeCategory() {
        //Given
        RecipeCategory recipeCategory = new RecipeCategory(1L, "Pasta", new ArrayList<>());

        when(recipeCategoryRepository.save(recipeCategory)).thenReturn(recipeCategory);
        //When
        RecipeCategory savedRecipeCategory = recipeCategoryService.saveRecipeCategory(recipeCategory);
        //Then
        assertEquals("Pasta", savedRecipeCategory.getRecipeCategoryName());
    }

    @Test
    public void deleteRecipeCategory() {
        //Given
        RecipeCategory recipeCategory = new RecipeCategory(1L, "Pasta", new ArrayList<>());

        //When
        recipeCategoryService.deleteRecipeCategory(1L);

        //Then
        verify(recipeCategoryRepository, times(1)).deleteById(anyLong());
        assertFalse(recipeCategoryRepository.findById(recipeCategory.getRecipeCategoryId()).isPresent());

    }
}
