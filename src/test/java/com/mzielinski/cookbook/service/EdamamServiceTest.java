package com.mzielinski.cookbook.service;

import com.mzielinski.cookbook.domain.Ingredient;
import com.mzielinski.cookbook.domain.Product;
import com.mzielinski.cookbook.domain.Recipe;
import com.mzielinski.cookbook.domain.dto.*;
import com.mzielinski.cookbook.edamam.client.EdamamClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EdamamServiceTest {
    @InjectMocks
    EdamamService edamamService;
    @Mock
    EdamamClient edamamClient;

    @Mock
    IngredientService ingredientService;
    @Test
    public void getNutritionValues() {
        //Given
        EdamamDto edamamDto = new EdamamDto(new NutrientsDto(new KcalDto(new BigDecimal("500"), "kcal"), new FatDto(new BigDecimal("20"), "g"), new ProteinDto(new BigDecimal("100"), "g"), new CarbohydratesDto(new BigDecimal("25"), "g")));

        Ingredient ingredient1 = new Ingredient(1L, new BigDecimal(500), "g", new Product(), new Recipe(), false);

        when(edamamClient.getNutritionData(any())).thenReturn(edamamDto);
        //When
        EdamamDto getNutritionData = edamamService.getNutrition(ingredient1);

        //Then
        assertEquals("kcal", getNutritionData.getNutrientsDto().getKcalDto().getUnit());
        assertEquals(new BigDecimal("500"), getNutritionData.getNutrientsDto().getKcalDto().getQuantity());


    }
}
