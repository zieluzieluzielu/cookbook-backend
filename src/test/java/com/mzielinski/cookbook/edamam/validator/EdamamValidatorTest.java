package com.mzielinski.cookbook.edamam.validator;


import com.mzielinski.cookbook.domain.*;
import com.mzielinski.cookbook.domain.dto.*;
import com.mzielinski.cookbook.service.EdamamService;
import com.mzielinski.cookbook.service.IngredientService;
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
public class EdamamValidatorTest {
    @InjectMocks
    private EdamamValidator edamamValidator;

    @Mock
    private EdamamService edamamService;

    @Mock
    private Ingredient ingredient;

    @Mock
    IngredientService ingredientService;

    @Test
    public void validateEdamamTest() {
        //Given
        Edamam edamam = new Edamam(new Nutrients(new Kcal(new BigDecimal("500"), "kcal"), new Fat(new BigDecimal("20"), "g"), new Protein(new BigDecimal("100"), "g"), new Carbohydrates(new BigDecimal("25"), "g")));
        EdamamDto edamamDto = new EdamamDto(new NutrientsDto(new KcalDto(new BigDecimal("500"), "kcal"), new FatDto(new BigDecimal("20"), "g"), new ProteinDto(new BigDecimal("100"), "g"), new CarbohydratesDto(new BigDecimal("25"), "g")));

        Ingredient ingredient = new Ingredient();
        String ingredientToAnalyze = ingredientService.ingredientToAnalyze(ingredient);

//        when(ingredientService.ingredientToAnalyze(any())).thenReturn("test");
        when(edamamService.getNutrition(any())).thenReturn(edamamDto);

        //When
        EdamamDto edamamValidated = edamamValidator.validateNutrition(edamam,ingredient);

        //Then
        assertEquals("kcal", edamamValidated.getNutrientsDto().getKcalDto().getUnit());
        assertEquals(new BigDecimal("500"), edamamValidated.getNutrientsDto().getKcalDto().getQuantity());
        assertEquals(new BigDecimal("20"), edamamValidated.getNutrientsDto().getFatDto().getQuantity());


    }
}
