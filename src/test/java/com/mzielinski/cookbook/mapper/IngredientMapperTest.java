package com.mzielinski.cookbook.mapper;

import com.mzielinski.cookbook.domain.*;
import com.mzielinski.cookbook.domain.dto.IngredientDto;
import com.mzielinski.cookbook.repository.ProductRepository;
import com.mzielinski.cookbook.repository.RecipeRepository;
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
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class IngredientMapperTest {
    @InjectMocks
    IngredientMapper ingredientMapper;

    @Mock
    ProductRepository productRepository;
    @Mock
    RecipeRepository recipeRepository;

    @Test
    public void mapToIngredientTest() {
        //Given
        IngredientDto ingredientDto = new IngredientDto(new BigDecimal(500), "g", 1L, 1L, true);

        Product product = new Product(1L, "name", new ProductGroup(), new ArrayList<>());
        Recipe recipe = new Recipe(1L, "Chicken Burger", "Test recipe details", 30L, new RecipeCategory(), new User(), new ArrayList<>());
        Optional<Product> product1 = Optional.of(product);
        Optional<Recipe> recipe1 = Optional.of(recipe);


        //When
        when(productRepository.findById(1L)).thenReturn(product1);
        when(recipeRepository.findById(1L)).thenReturn(recipe1);

        Ingredient ingredient = ingredientMapper.mapToIngredient(ingredientDto);

        System.out.println(ingredient.getAmount());
        System.out.println(ingredient.getUnit());
        //Then
        assertEquals(ingredientDto.getAmount(), ingredient.getAmount());
        assertEquals(ingredientDto.isMainProduct(), ingredient.isMainProduct());
    }

    @Test
    public void mapToIngredientDtoTest() {
        //Given
        Ingredient ingredient = new Ingredient(1L, new BigDecimal(100), "ml", new Product(), new Recipe(), false);

        //When
        IngredientDto ingredientDto = ingredientMapper.mapToIngredientDto(ingredient);
        //Then
        assertEquals(ingredient.getAmount(), ingredientDto.getAmount());
        assertEquals(ingredient.isMainProduct(), ingredientDto.isMainProduct());
    }

    @Test
    public void mapToIngredientDtoListTest() {
        //Given
        List<Ingredient> ingredientsList = new ArrayList<>();

        Recipe recipe1 = new Recipe(6L, "Chicken Curry", "Test details of recipe", 10L, new RecipeCategory(), new User(), new ArrayList<>());

        Ingredient ingredient1 = new Ingredient(1L, new BigDecimal(100), "ml", new Product(), recipe1, false);
        Ingredient ingredient2 = new Ingredient(2L, new BigDecimal(500), "g", new Product(), recipe1, true);
        ingredientsList.add(ingredient1);
        ingredientsList.add(ingredient2);


        //When
        List<IngredientDto> ingredientDtoList = ingredientMapper.mapToIngredientsDtoList(ingredientsList);

        //Then
        assertEquals(2, ingredientDtoList.size());
        assertEquals("ml", ingredientDtoList.get(0).getUnit());
        assertEquals(new BigDecimal("500"), ingredientDtoList.get(1).getAmount());
    }
}
