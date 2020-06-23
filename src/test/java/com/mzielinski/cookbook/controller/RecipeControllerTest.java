package com.mzielinski.cookbook.controller;

import com.google.gson.Gson;
import com.mzielinski.cookbook.domain.Recipe;
import com.mzielinski.cookbook.domain.RecipeCategory;
import com.mzielinski.cookbook.domain.User;
import com.mzielinski.cookbook.domain.dto.RecipeDto;
import com.mzielinski.cookbook.mapper.RecipeMapper;
import com.mzielinski.cookbook.service.RecipeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

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
@WebMvcTest(RecipeController.class)
public class RecipeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeService recipeService;

    @MockBean
    private RecipeMapper recipeMapper;

    @Test
    public void shouldRetrieveRecipeList() throws Exception {
        //Given
        List<RecipeDto> recipesListDto = new ArrayList<>();

        RecipeDto recipeDto1 = RecipeDto.builder()
                .recipeId(1L)
                .recipeName("Chicken Burger")
                .recipeDetails("Test recipe details")
                .preparationTime(30L)
                .recipeCategoryId(1L)
                .userId(1L).build();
        RecipeDto recipeDto2 = RecipeDto.builder()
                .recipeId(2L)
                .recipeName("Chicken with cheese")
                .recipeDetails("Test recipe details2")
                .preparationTime(20L)
                .recipeCategoryId(1L)
                .userId(1L).build();

        recipesListDto.add(recipeDto1);
        recipesListDto.add(recipeDto2);


        when(recipeMapper.mapToRecipesDtoList(recipeService.getAllRecipes())).thenReturn(recipesListDto);

        //When & Then
        mockMvc.perform(get("/v1/recipes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].recipeId", is(recipesListDto.get(0).getRecipeId().intValue())))
                .andExpect(jsonPath("$[1].recipeName", is("Chicken with cheese")))
                .andExpect(jsonPath("$[0].recipeName", is("Chicken Burger")))
                .andExpect(jsonPath("$[0].recipeDetails", is("Test recipe details")))
                .andExpect(jsonPath("$[0].preparationTime", is(30)))
                .andExpect(jsonPath("$[1].recipeCategoryId", is(1)))
                .andExpect(jsonPath("$[0].userId", is(1)));
    }


    @Test
    public void shouldRetrieveRecipeListByPreparationTimeShorterThan() throws Exception {
        //Given
        List<RecipeDto> recipesListDto = new ArrayList<>();

        RecipeDto recipeDto1 = RecipeDto.builder()
                .recipeId(1L)
                .recipeName("Chicken Burger")
                .recipeDetails("Test recipe details")
                .preparationTime(30L)
                .recipeCategoryId(1L)
                .userId(1L).build();
        RecipeDto recipeDto2 = RecipeDto.builder()
                .recipeId(2L)
                .recipeName("Chicken with cheese")
                .recipeDetails("Test recipe details2")
                .preparationTime(20L)
                .recipeCategoryId(1L)
                .userId(1L).build();
        recipesListDto.add(recipeDto1);
        recipesListDto.add(recipeDto2);

        when(recipeMapper.mapToRecipesDtoList(recipeService.getRecipesByPreparationTimeShorterThan(30L))).thenReturn(recipesListDto);

        //When & Then
        mockMvc.perform(get("/v1/recipes/preparationtime/30").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].recipeId", is(recipesListDto.get(0).getRecipeId().intValue())))
                .andExpect(jsonPath("$[1].recipeName", is("Chicken with cheese")))
                .andExpect(jsonPath("$[0].recipeName", is("Chicken Burger")))
                .andExpect(jsonPath("$[0].recipeDetails", is("Test recipe details")))
                .andExpect(jsonPath("$[0].preparationTime", is(30)))
                .andExpect(jsonPath("$[1].recipeCategoryId", is(1)))
                .andExpect(jsonPath("$[0].userId", is(1)));
    }


    @Test
    public void shouldRetrieveRecipeListByCategory() throws Exception {
        //Given
        List<RecipeDto> recipesListDto = new ArrayList<>();

        RecipeDto recipeDto1 = RecipeDto.builder()
                .recipeId(1L)
                .recipeName("Chicken Burger")
                .recipeDetails("Test recipe details")
                .preparationTime(30L)
                .recipeCategoryId(1L)
                .userId(1L).build();
        RecipeDto recipeDto2 = RecipeDto.builder()
                .recipeId(2L)
                .recipeName("Chicken with cheese")
                .recipeDetails("Test recipe details2")
                .preparationTime(20L)
                .recipeCategoryId(1L)
                .userId(1L).build();
        recipesListDto.add(recipeDto1);
        recipesListDto.add(recipeDto2);

        when(recipeMapper.mapToRecipesDtoList(recipeService.getRecipesByCategory(1L))).thenReturn(recipesListDto);

        //When & Then
        mockMvc.perform(get("/v1/recipes/recipecategory/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].recipeId", is(recipesListDto.get(0).getRecipeId().intValue())))
                .andExpect(jsonPath("$[1].recipeName", is("Chicken with cheese")))
                .andExpect(jsonPath("$[0].recipeName", is("Chicken Burger")))
                .andExpect(jsonPath("$[0].recipeDetails", is("Test recipe details")))
                .andExpect(jsonPath("$[0].preparationTime", is(30)))
                .andExpect(jsonPath("$[1].recipeCategoryId", is(1)))
                .andExpect(jsonPath("$[0].userId", is(1)));
    }

    @Test
    public void shouldRetrieveRecipeListByProduct() throws Exception {
        //Given
        List<RecipeDto> recipesListDto = new ArrayList<>();

        RecipeDto recipeDto1 = RecipeDto.builder()
                .recipeId(1L)
                .recipeName("Chicken Burger")
                .recipeDetails("Test recipe details")
                .preparationTime(30L)
                .recipeCategoryId(1L)
                .userId(1L).build();
        RecipeDto recipeDto2 = RecipeDto.builder()
                .recipeId(2L)
                .recipeName("Chicken with cheese")
                .recipeDetails("Test recipe details2")
                .preparationTime(20L)
                .recipeCategoryId(1L)
                .userId(1L).build();

        recipesListDto.add(recipeDto1);
        recipesListDto.add(recipeDto2);

        when(recipeMapper.mapToRecipesDtoList(recipeService.getRecipesByProduct(1L))).thenReturn(recipesListDto);

        //When & Then
        mockMvc.perform(get("/v1/recipes/product/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].recipeId", is(recipesListDto.get(0).getRecipeId().intValue())))
                .andExpect(jsonPath("$[1].recipeName", is("Chicken with cheese")))
                .andExpect(jsonPath("$[0].recipeName", is("Chicken Burger")))
                .andExpect(jsonPath("$[0].recipeDetails", is("Test recipe details")))
                .andExpect(jsonPath("$[0].preparationTime", is(30)))
                .andExpect(jsonPath("$[1].recipeCategoryId", is(1)))
                .andExpect(jsonPath("$[0].userId", is(1)));
    }




    @Test
    public void shouldRetrieveRecipeListByUser() throws Exception {
        //Given
        List<RecipeDto> recipesListDto = new ArrayList<>();

        RecipeDto recipeDto1 = RecipeDto.builder()
                .recipeId(1L)
                .recipeName("Chicken Burger")
                .recipeDetails("Test recipe details")
                .preparationTime(30L)
                .recipeCategoryId(1L)
                .userId(1L).build();
        RecipeDto recipeDto2 = RecipeDto.builder()
                .recipeId(2L)
                .recipeName("Chicken with cheese")
                .recipeDetails("Test recipe details2")
                .preparationTime(20L)
                .recipeCategoryId(1L)
                .userId(1L).build();

        recipesListDto.add(recipeDto1);
        recipesListDto.add(recipeDto2);

        when(recipeMapper.mapToRecipesDtoList(recipeService.getRecipesByUser(1L))).thenReturn(recipesListDto);

        //When & Then
        mockMvc.perform(get("/v1/recipes/user/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].recipeId", is(recipesListDto.get(0).getRecipeId().intValue())))
                .andExpect(jsonPath("$[1].recipeName", is("Chicken with cheese")))
                .andExpect(jsonPath("$[0].recipeName", is("Chicken Burger")))
                .andExpect(jsonPath("$[0].recipeDetails", is("Test recipe details")))
                .andExpect(jsonPath("$[0].preparationTime", is(30)))
                .andExpect(jsonPath("$[1].recipeCategoryId", is(1)))
                .andExpect(jsonPath("$[0].userId", is(1)));
    }





    @Test
    public void shouldRetrieveRecipe() throws Exception {
        //Given
        RecipeDto recipeDto = RecipeDto.builder()
                .recipeId(1L)
                .recipeName("Chicken Burger")
                .recipeDetails("Test recipe details")
                .preparationTime(30L)
                .recipeCategoryId(1L)
                .userId(1L).build();

        Recipe recipe1 = new Recipe(1L, "Chicken Burger", "Test recipe details", 30L, new RecipeCategory(), new User(), new ArrayList<>());

        Optional<Recipe> recipe = Optional.of(recipe1);

        when(recipeService.getRecipe(ArgumentMatchers.any())).thenReturn(recipe);
        when(recipeMapper.mapToRecipeDto(ArgumentMatchers.any())).thenReturn(recipeDto);

        //When & Then
        mockMvc.perform(get("/v1/recipes/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId", is(recipeDto.getRecipeId().intValue())))
                .andExpect(jsonPath("$.recipeName", is("Chicken Burger")))
                .andExpect(jsonPath("$.recipeDetails", is("Test recipe details")))
                .andExpect(jsonPath("$.preparationTime", is(30)))
                .andExpect(jsonPath("$.recipeCategoryId", is(1)))
                .andExpect(jsonPath("$.userId", is(1)));

    }

    @Test
    public void shouldUpdateRecipe() throws Exception {
        //Given

        RecipeDto recipeDto = RecipeDto.builder()
                .recipeId(1L)
                .recipeName("Chicken Burger")
                .recipeDetails("Test recipe details")
                .preparationTime(30L)
                .recipeCategoryId(1L)
                .userId(1L).build();


        Recipe recipe1 = new Recipe(1L, "Chicken Burger", "Test recipe details", 30L, new RecipeCategory(), new User(), new ArrayList<>());

        Optional<Recipe> recipe = Optional.of(recipe1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(recipeDto);

        when(recipeService.getRecipe(ArgumentMatchers.any())).thenReturn(recipe);
        when(recipeMapper.mapToRecipeDto(ArgumentMatchers.any())).thenReturn(recipeDto);

        //When & Then
        mockMvc.perform(put("/v1/recipes").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId", is(recipeDto.getRecipeId().intValue())))
                .andExpect(jsonPath("$.recipeName", is("Chicken Burger")))
                .andExpect(jsonPath("$.recipeDetails", is("Test recipe details")))
                .andExpect(jsonPath("$.preparationTime", is(30)))
                .andExpect(jsonPath("$.recipeCategoryId", is(1)))
                .andExpect(jsonPath("$.userId", is(1)));

    }


    @Test
    public void shouldCreateRecipe() throws Exception {
        //Given
        RecipeDto recipeDto = RecipeDto.builder()
                .recipeId(1L)
                .recipeName("Chicken Burger")
                .recipeDetails("Test recipe details")
                .preparationTime(30L)
                .recipeCategoryId(1L)
                .userId(1L).build();
        Recipe recipe1 = new Recipe(1L, "Chicken Burger", "Test recipe details", 30L, new RecipeCategory(), new User(), new ArrayList<>());

        Gson gson = new Gson();
        String jsonContent = gson.toJson(recipeDto);

        when(recipeMapper.mapToRecipe(any(RecipeDto.class))).thenReturn(recipe1);
        when(recipeMapper.mapToRecipeDto(recipe1)).thenReturn(recipeDto);
        when(recipeService.saveRecipe(any(Recipe.class))).then(returnsFirstArg());

        System.out.println(jsonContent);
        //When & Then
        mockMvc.perform(post("/v1/recipes")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeId", is(recipeDto.getRecipeId().intValue())))
                .andExpect(jsonPath("$.recipeName", is("Chicken Burger")))
                .andExpect(jsonPath("$.recipeDetails", is("Test recipe details")))
                .andExpect(jsonPath("$.preparationTime", is(30)))
                .andExpect(jsonPath("$.recipeCategoryId", is(1)))
                .andExpect(jsonPath("$.userId", is(1)));


    }

    @Test
    public void shouldDeleteRecipe() throws Exception {
        //When&Then
        mockMvc.perform(delete("/v1/recipes/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(recipeService, times(1)).deleteRecipe(1L);
    }


}