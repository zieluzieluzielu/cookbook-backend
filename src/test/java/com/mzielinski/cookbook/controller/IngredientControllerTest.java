package com.mzielinski.cookbook.controller;

import com.google.gson.Gson;
import com.mzielinski.cookbook.domain.*;
import com.mzielinski.cookbook.domain.dto.IngredientDto;
import com.mzielinski.cookbook.mapper.IngredientMapper;
import com.mzielinski.cookbook.service.IngredientService;
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
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(IngredientController.class)
public class IngredientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IngredientService ingredientService;

    @MockBean
    private IngredientMapper ingredientMapper;


    @Test
    public void shouldRetrieveIngredientList() throws Exception {

        List<IngredientDto> ingredientsListDto = new ArrayList<>();
        IngredientDto ingredientDto1 = IngredientDto.builder().
                ingredientId(2L)
                .amount(new BigDecimal(500))
                .unit("g")
                .productId(1L)
                .recipeId(2L)
                .mainProduct(true)
                .build();

        IngredientDto ingredientDto2 = IngredientDto.builder().
                ingredientId(2L)
                .amount(new BigDecimal(10))
                .unit("ml")
                .productId(2L)
                .recipeId(2L)
                .mainProduct(true)
                .build();
        IngredientDto ingredientDto3 = IngredientDto.builder().
                ingredientId(2L)
                .amount(new BigDecimal(200))
                .unit("g")
                .productId(3L)
                .recipeId(1L)
                .mainProduct(true)
                .build();

        ingredientsListDto.add(ingredientDto1);
        ingredientsListDto.add(ingredientDto2);
        ingredientsListDto.add(ingredientDto3);

        when(ingredientMapper.mapToIngredientsDtoList(ingredientService.getAllIngredients())).thenReturn(ingredientsListDto);


        //When & Then
        mockMvc.perform(get("/v1/ingredients").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].ingredientId", is(ingredientsListDto.get(0).getIngredientId().intValue())))
                .andExpect(jsonPath("$[1].unit", is("ml")))
                .andExpect(jsonPath("$[2].mainProduct", is(true)))
                .andExpect(jsonPath("$[0].amount", is(500)));
    }


    @Test
    public void shouldRetrieveIngredientListFromRecipe() throws Exception {
        //Given
        Recipe recipe1 = new Recipe(1L, "Chicken Curry", "Test details of recipe 1", 10L, new RecipeCategory(), new User(), new ArrayList<>());
        List<IngredientDto> ingredientsListDto = new ArrayList<>();
        IngredientDto ingredientDto1 = IngredientDto.builder().
                ingredientId(2L)
                .amount(new BigDecimal(500))
                .unit("g")
                .productId(1L)
                .recipeId(2L)
                .mainProduct(true)
                .build();

        IngredientDto ingredientDto2 = IngredientDto.builder().
                ingredientId(2L)
                .amount(new BigDecimal(10))
                .unit("ml")
                .productId(2L)
                .recipeId(2L)
                .mainProduct(true)
                .build();
        ingredientsListDto.add(ingredientDto1);
        ingredientsListDto.add(ingredientDto2);

        when(ingredientMapper.mapToIngredientsDtoList(ingredientService.getIngredientsByRecipe(recipe1.getRecipeId()))).thenReturn(ingredientsListDto);

        //When & Then
        mockMvc.perform(get("/v1/ingredients/recipes/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].ingredientId", is(ingredientsListDto.get(0).getIngredientId().intValue())))
                .andExpect(jsonPath("$[1].unit", is("ml")))
                .andExpect(jsonPath("$[0].amount", is(500)));
    }


    @Test
    public void shouldRetrieveIngredient() throws Exception {
        //Given
        IngredientDto ingredientDto = IngredientDto.builder().
                ingredientId(2L)
                .amount(new BigDecimal(500))
                .unit("g")
                .productId(1L)
                .recipeId(2L)
                .mainProduct(true)
                .build();
        Ingredient ingredient1 = new Ingredient(1L, new BigDecimal(500), "g", new Product(), new Recipe(), false);
        Optional<Ingredient> ingredient = Optional.of(ingredient1);

        when(ingredientService.getIngredient(any())).thenReturn(ingredient);
        when(ingredientMapper.mapToIngredientDto(any())).thenReturn(ingredientDto);

        //When & Then
        mockMvc.perform(get("/v1/ingredients/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredientId", is(ingredientDto.getIngredientId().intValue())))
                .andExpect(jsonPath("$.amount", is(500)));
    }


    @Test
    public void shouldUpdateIngredient() throws Exception {
        //Given
        IngredientDto ingredientDto = IngredientDto.builder().
                ingredientId(2L)
                .amount(new BigDecimal(500))
                .unit("g")
                .productId(1L)
                .recipeId(2L)
                .mainProduct(true)
                .build();
        Ingredient ingredient1 = new Ingredient(1L, new BigDecimal(500), "g", new Product(), new Recipe(), false);

        Optional<Ingredient> ingredient = Optional.of(ingredient1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(ingredientDto);

        when(ingredientService.getIngredient(any())).thenReturn(ingredient);
        when(ingredientMapper.mapToIngredientDto(any())).thenReturn(ingredientDto);

        //When & Then
        mockMvc.perform(put("/v1/ingredients").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredientId", is(ingredientDto.getIngredientId().intValue())))
                .andExpect(jsonPath("$.amount", is(500)));
    }


    @Test
    public void shouldCreateIngredient() throws Exception {
        //Given
        IngredientDto ingredientDto = IngredientDto.builder().
                ingredientId(2L)
                .amount(new BigDecimal(500))
                .unit("g")
                .productId(1L)
                .recipeId(2L)
                .mainProduct(true)
                .build();
        Ingredient ingredient1 = new Ingredient(1L, new BigDecimal(500), "g", new Product(), new Recipe(), false);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(ingredientDto);

        when(ingredientMapper.mapToIngredient(any(IngredientDto.class))).thenReturn(ingredient1);
        when(ingredientMapper.mapToIngredientDto(ingredient1)).thenReturn(ingredientDto);
        when(ingredientService.saveIngredient(any(Ingredient.class))).then(returnsFirstArg());

        System.out.println(jsonContent);
        //When & Then
        mockMvc.perform(post("/v1/ingredients")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.ingredientId", is(ingredientDto.getIngredientId().intValue())))
                .andExpect(jsonPath("$.amount", is(500)));
    }

    @Test
    public void shouldDeleteIngredient() throws Exception {
        //When&Then
        mockMvc.perform(delete("/v1/ingredients/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(ingredientService, times(1)).deleteIngredient(1L);
    }


}