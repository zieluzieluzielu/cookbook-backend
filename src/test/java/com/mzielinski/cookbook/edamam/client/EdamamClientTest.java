package com.mzielinski.cookbook.edamam.client;

import com.mzielinski.cookbook.domain.Ingredient;
import com.mzielinski.cookbook.domain.Product;
import com.mzielinski.cookbook.domain.ProductGroup;
import com.mzielinski.cookbook.domain.Recipe;
import com.mzielinski.cookbook.domain.dto.*;
import com.mzielinski.cookbook.edamam.config.EdamamConfig;
import com.mzielinski.cookbook.service.IngredientService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class EdamamClientTest {
    @InjectMocks
    private EdamamClient edamamClient;

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private EdamamConfig edamamConfig;

    @Mock
    private IngredientService ingredientService;


    @Before
    public void init() {
        when(edamamConfig.getEdamamApiEndpoint()).thenReturn("http://test.com");
        when(edamamConfig.getEdamamAppId()).thenReturn("test");
        when(edamamConfig.getEdamamAppKey()).thenReturn("test");
    }

    @Test
    public void getNutritionDataTest() throws URISyntaxException {

        //Given
        Product product = new Product(1L, "Chicken breast", new ProductGroup(6L, "Meat", new ArrayList<>()), new ArrayList<>());
        EdamamDto edamamDto = new EdamamDto(new NutrientsDto(new KcalDto(new BigDecimal("500"), "kcal"), new FatDto(new BigDecimal("20"), "g"), new ProteinDto(new BigDecimal("100"), "g"), new CarbohydratesDto(new BigDecimal("25"), "g")));
        Ingredient ingredient1 = new Ingredient(1L, new BigDecimal(500), "g", product, new Recipe(), false);

        String ingredientToAnalyze = ingredientService.ingredientToAnalyze(ingredient1);
        //URI uri = new URI("http://test.com?ingr=Chicken%20breast&app_key=test&app_id=test");


        URI url = edamamClient.prepareUrl(ingredientToAnalyze);
        when(restTemplate.getForObject(url, EdamamDto.class)).thenReturn(edamamDto);

        //When
        EdamamDto getNutritionData = edamamClient.getNutritionData(ingredientToAnalyze);

        //Then
        assertEquals("kcal", getNutritionData.getNutrientsDto().getKcalDto().getUnit());
        assertEquals(new BigDecimal("500"), getNutritionData.getNutrientsDto().getKcalDto().getQuantity());
        assertEquals(new BigDecimal("20"), getNutritionData.getNutrientsDto().getFatDto().getQuantity());


    }

    @Test
    public void uriTest() {

        Product product = new Product(1L, "Chicken breast", new ProductGroup(6L, "Meat", new ArrayList<>()), new ArrayList<>());
        Ingredient ingredient1 = new Ingredient(1L, new BigDecimal(500), "g", product, new Recipe(), false);

        String ingredientToAnalyze = ingredient1.ingredientToAnalyze();
        //When
        URI url = edamamClient.prepareUrl(ingredientToAnalyze);


        //Then
        assertEquals("http://test.com?ingr=500%20g%20Chicken%20breast&app_key=test&app_id=test", url.toString());


    }

}
