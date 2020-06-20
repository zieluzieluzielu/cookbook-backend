package com.mzielinski.cookbook.mapper;

import com.mzielinski.cookbook.domain.Recipe;
import com.mzielinski.cookbook.domain.RecipeCategory;
import com.mzielinski.cookbook.domain.User;
import com.mzielinski.cookbook.domain.dto.RecipeDto;
import com.mzielinski.cookbook.repository.RecipeCategoryRepository;
import com.mzielinski.cookbook.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RecipeMapperTest {
    @InjectMocks
    RecipeMapper recipeMapper;

    @Mock
    RecipeCategoryRepository recipeCategoryRepository;

    @Mock
    UserRepository userRepository;

    @Test
    public void mapToRecipeTest() {
        //Given
        RecipeDto recipeDto = new RecipeDto(1L, "Chicken Burger","Test recipe details", 30L,1L, 1L);
        RecipeCategory recipeCategory1 = new RecipeCategory(1L, "Pasta", new ArrayList<>());
        User user1 =  new User(1L,"mark_hamill","mark@hamill.com","Mark123",true);

        //When
        when(userRepository.findById(1L)).thenReturn(Optional.of(user1));
        when(recipeCategoryRepository.findById(1L)).thenReturn(Optional.of(recipeCategory1));

        Recipe recipe = recipeMapper.mapToRecipe(recipeDto);

        //Then
        assertEquals(recipeDto.getRecipeName(), recipe.getRecipeName());
        assertEquals(recipeDto.getRecipeDetails(), recipe.getRecipeDetails());
        assertEquals(recipeDto.getPreparationTime(), recipe.getPreparationTime());
    }

    @Test
    public void mapToRecipeDtoTest() {
        //Given
        Recipe recipe = new Recipe(1L, "Chicken Curry", "Test details of recipe 1", 10L, new RecipeCategory(), new User(), new ArrayList<>());

        //When
        RecipeDto recipeDto = recipeMapper.mapToRecipeDto(recipe);
        //Then
        assertEquals(recipe.getRecipeName(), recipeDto.getRecipeName());
        assertEquals(recipe.getRecipeDetails(), recipeDto.getRecipeDetails());
        assertEquals(recipe.getPreparationTime(), recipeDto.getPreparationTime());
    }

    @Test
    public void mapToRecipeDtoListTest() {
        //Given
        List<Recipe> recipesList = new ArrayList<>();

        Recipe recipe1 = new Recipe(1L, "Chicken Curry", "Test details of recipe 1", 10L, new RecipeCategory(), new User(), new ArrayList<>());
        Recipe recipe2 = new Recipe(2L, "Tikka Masala", "Test details of recipe 2", 14L, new RecipeCategory(), new User(), new ArrayList<>());

        recipesList.add(recipe1);
        recipesList.add(recipe2);


        //When
        List<RecipeDto> recipeDtoList = recipeMapper.mapToRecipesDtoList(recipesList);

        //Then
        assertEquals(2, recipeDtoList.size());
        assertEquals("Chicken Curry", recipeDtoList.get(0).getRecipeName());
        assertEquals("Tikka Masala", recipeDtoList.get(1).getRecipeName());
        assertEquals("Test details of recipe 2", recipeDtoList.get(1).getRecipeDetails());
    }
}
