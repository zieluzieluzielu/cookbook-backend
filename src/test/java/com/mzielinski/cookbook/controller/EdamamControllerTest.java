package com.mzielinski.cookbook.controller;

import com.mzielinski.cookbook.domain.*;
import com.mzielinski.cookbook.domain.dto.*;
import com.mzielinski.cookbook.edamam.facade.EdamamFacade;
import com.mzielinski.cookbook.mapper.IngredientMapper;
import com.mzielinski.cookbook.service.IngredientService;
import com.mzielinski.cookbook.service.RecipeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(NutritionValuesController.class)
public class EdamamControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EdamamFacade edamamFacade;

    @MockBean
    private RecipeService recipeService;

    @MockBean
    private IngredientService ingredientService;

    @MockBean
    private IngredientMapper ingredientMapper;

    @Test
    public void shouldFetchNutritionValues() throws Exception {
        //Given
        EdamamDto edamamDto = new EdamamDto(new NutrientsDto(new KcalDto(new BigDecimal("500"), "kcal"), new FatDto(new BigDecimal("20"), "g"), new ProteinDto(new BigDecimal("100"), "g"), new CarbohydratesDto(new BigDecimal("25"), "g")));

        IngredientDto ingredientDto = new IngredientDto(1L, new BigDecimal(500), "g", 1L, 1L, true);
        Ingredient ingredient1 = new Ingredient(1L, new BigDecimal(500), "g", new Product(), new Recipe(), false);
        Optional<Ingredient> ingredient = Optional.of(ingredient1);


        when(ingredientService.getIngredient(1L)).thenReturn(ingredient);
        when(ingredientMapper.mapToIngredientDto(any())).thenReturn(ingredientDto);
        when(edamamFacade.getNutrition(any())).thenReturn(edamamDto);

        System.out.println(edamamDto.getNutrientsDto().getKcalDto().getUnit());

        //When & Then
        mockMvc.perform(get("/v1/nutrition/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalNutrients.ENERC_KCAL.unit", is("kcal")))
                .andExpect(jsonPath("$.totalNutrients.ENERC_KCAL.quantity", is(500)))
                .andExpect(jsonPath("$.totalNutrients.FAT.unit", is("g")))
                .andExpect(jsonPath("$.totalNutrients.FAT.quantity", is(20)))
                .andExpect(jsonPath("$.totalNutrients.PROCNT.unit", is("g")))
                .andExpect(jsonPath("$.totalNutrients.PROCNT.quantity", is(100)))
                .andExpect(jsonPath("$.totalNutrients.CHOCDF.unit", is("g")))
                .andExpect(jsonPath("$.totalNutrients.CHOCDF.quantity", is(25)));


    }


    @Test
    public void shouldFetchNutritionValuesOfEachIngredientInRecipe() throws Exception {
        //Given
        EdamamDto edamamDto1 = new EdamamDto(new NutrientsDto(new KcalDto(new BigDecimal("500"), "kcal"), new FatDto(new BigDecimal("20"), "g"), new ProteinDto(new BigDecimal("100"), "g"), new CarbohydratesDto(new BigDecimal("25"), "g")));
        EdamamDto edamamDto2 = new EdamamDto(new NutrientsDto(new KcalDto(new BigDecimal("300"), "kcal"), new FatDto(new BigDecimal("10"), "g"), new ProteinDto(new BigDecimal("100"), "g"), new CarbohydratesDto(new BigDecimal("25"), "g")));


        List<EdamamDto> edamamDtoList = new ArrayList<>();
        edamamDtoList.add(edamamDto1);
        edamamDtoList.add(edamamDto2);

        Recipe recipe1 = new Recipe(1L, "Chicken Burger", "Test recipe details", 30L, new RecipeCategory(), new User(), new ArrayList<>());

        Optional<Recipe> recipe = Optional.of(recipe1);

        when(recipeService.getRecipe(1L)).thenReturn(recipe);
        when(edamamFacade.getNutritionList(any())).thenReturn(edamamDtoList);

        //When & Then
        mockMvc.perform(get("/v1/nutrition/recipe/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[1].totalNutrients.ENERC_KCAL.unit", is("kcal")))
                .andExpect(jsonPath("$[0].totalNutrients.ENERC_KCAL.quantity", is(500)))
                .andExpect(jsonPath("$[1].totalNutrients.FAT.unit", is("g")))
                .andExpect(jsonPath("$[1].totalNutrients.FAT.quantity", is(10)))
                .andExpect(jsonPath("$[0].totalNutrients.PROCNT.unit", is("g")))
                .andExpect(jsonPath("$[0].totalNutrients.PROCNT.quantity", is(100)))
                .andExpect(jsonPath("$[0].totalNutrients.CHOCDF.unit", is("g")))
                .andExpect(jsonPath("$[0].totalNutrients.CHOCDF.quantity", is(25)));

    }

    @Test
    public void shouldFetchSumOfNutritionValuesOfRecipe() throws Exception {
        //Given
        EdamamDto edamamDto1 = new EdamamDto(new NutrientsDto(new KcalDto(new BigDecimal("500"), "kcal"), new FatDto(new BigDecimal("20"), "g"), new ProteinDto(new BigDecimal("100"), "g"), new CarbohydratesDto(new BigDecimal("25"), "g")));

        Recipe recipe1 = new Recipe(1L, "Chicken Burger", "Test recipe details", 30L, new RecipeCategory(), new User(), new ArrayList<>());

        Optional<Recipe> recipe = Optional.of(recipe1);

        when(recipeService.getRecipe(1L)).thenReturn(recipe);
        when(edamamFacade.getSumOfNutrition(any())).thenReturn(edamamDto1);

        //When & Then
        mockMvc.perform(get("/v1/nutrition/recipe/sum/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalNutrients.ENERC_KCAL.unit", is("kcal")))
                .andExpect(jsonPath("$.totalNutrients.ENERC_KCAL.quantity", is(500)))
                .andExpect(jsonPath("$.totalNutrients.FAT.unit", is("g")))
                .andExpect(jsonPath("$.totalNutrients.FAT.quantity", is(20)))
                .andExpect(jsonPath("$.totalNutrients.PROCNT.unit", is("g")))
                .andExpect(jsonPath("$.totalNutrients.PROCNT.quantity", is(100)))
                .andExpect(jsonPath("$.totalNutrients.CHOCDF.unit", is("g")))
                .andExpect(jsonPath("$.totalNutrients.CHOCDF.quantity", is(25)));

    }


}
