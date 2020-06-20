package com.mzielinski.cookbook.edamam.facade;

import com.mzielinski.cookbook.domain.*;
import com.mzielinski.cookbook.domain.dto.*;
import com.mzielinski.cookbook.edamam.validator.EdamamValidator;
import com.mzielinski.cookbook.mapper.EdamamMapper;
import com.mzielinski.cookbook.service.EdamamService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EdamamFacadeTest {
    @InjectMocks
    private EdamamFacade edamamFacade;

    @Mock
    private EdamamService edamamService;

    @Mock
    private EdamamMapper edamamMapper;

    @Mock
    private EdamamValidator edamamValidator;

    @Test
    public void shouldGetNutritionValues() {
        //Given
        EdamamDto edamamDto = new EdamamDto(new NutrientsDto(new KcalDto(new BigDecimal("500"), "kcal"), new FatDto(new BigDecimal("20"), "g"), new ProteinDto(new BigDecimal("100"), "g"), new CarbohydratesDto(new BigDecimal("25"), "g")));
        Edamam edamam = new Edamam(new Nutrients(new Kcal(new BigDecimal("500"), "kcal"), new Fat(new BigDecimal("20"), "g"), new Protein(new BigDecimal("100"), "g"), new Carbohydrates(new BigDecimal("25"), "g")));


        Ingredient ingredient1 = new Ingredient(1L, new BigDecimal(500), "g", new Product(), new Recipe(), false);
        Optional<Ingredient> ingredient = Optional.of(ingredient1);


        when(edamamService.getNutrition(any())).thenReturn(edamamDto);
        when(edamamMapper.mapToEdamam(edamamDto)).thenReturn(edamam);
        when(edamamMapper.mapToEdamamDto(edamam)).thenReturn(edamamDto);
        when(edamamValidator.validateNutrition(any(), any())).thenReturn(edamamDto);

        //When
        EdamamDto getNutritionData = edamamFacade.getNutrition(ingredient1);

        //Then
        assertEquals("kcal", getNutritionData.getNutrientsDto().getKcalDto().getUnit());
        assertEquals(new BigDecimal("500"), getNutritionData.getNutrientsDto().getKcalDto().getQuantity());
        assertEquals(new BigDecimal("20"), getNutritionData.getNutrientsDto().getFatDto().getQuantity());


    }


}
