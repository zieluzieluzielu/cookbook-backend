package com.mzielinski.cookbook.controller;

import com.google.gson.Gson;
import com.mzielinski.cookbook.domain.RecipeCategory;
import com.mzielinski.cookbook.domain.dto.RecipeCategoryDto;
import com.mzielinski.cookbook.mapper.RecipeCategoryMapper;
import com.mzielinski.cookbook.service.RecipeCategoryService;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(RecipeCategoryController.class)
public class RecipeCategoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RecipeCategoryService recipeCategoryService;

    @MockBean
    private RecipeCategoryMapper recipeCategoryMapper;

    @Test
    public void shouldRetrieveRecipeCategoryList() throws Exception {
        //Given
        List<RecipeCategoryDto> recipeCategoriesListDto = new ArrayList<>();

        RecipeCategoryDto recipeCategoryDto1 = RecipeCategoryDto.builder()
                .recipeCategoryId(1L)
                .recipeCategoryName("Fast Food")
                .build();
        RecipeCategoryDto recipeCategoryDto2 = RecipeCategoryDto.builder()
                .recipeCategoryId(2L)
                .recipeCategoryName("Chinese")
                .build();
        recipeCategoriesListDto.add(recipeCategoryDto1);
        recipeCategoriesListDto.add(recipeCategoryDto2);


        when(recipeCategoryMapper.mapToRecipeCategoriesDtoList(recipeCategoryService.getAllRecipeCategories())).thenReturn(recipeCategoriesListDto);

        //When & Then
        mockMvc.perform(get("/v1/recipecategories").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].recipeCategoryId", is(recipeCategoriesListDto.get(0).getRecipeCategoryId().intValue())))
                .andExpect(jsonPath("$[1].recipeCategoryName", is("Chinese")))
                .andExpect(jsonPath("$[0].recipeCategoryName", is("Fast Food")));
    }

    @Test
    public void shouldRetrieveRecipeCategory() throws Exception {
        //Given
        RecipeCategoryDto recipeCategoryDto = RecipeCategoryDto.builder()
                .recipeCategoryId(1L)
                .recipeCategoryName("Fast Food")
                .build();
        RecipeCategory recipeCategory1 = new RecipeCategory(1L, "Fast Food", new ArrayList<>());
        Optional<RecipeCategory> recipeCategory = Optional.of(recipeCategory1);

        when(recipeCategoryService.getRecipeCategory(ArgumentMatchers.any())).thenReturn(recipeCategory);
        when(recipeCategoryMapper.mapToRecipeCategoryDto(ArgumentMatchers.any())).thenReturn(recipeCategoryDto);

        //When & Then
        mockMvc.perform(get("/v1/recipecategories/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeCategoryId", is(recipeCategoryDto.getRecipeCategoryId().intValue())))
                .andExpect(jsonPath("$.recipeCategoryName", is("Fast Food")));

    }

    @Test
    public void shouldUpdateRecipeCategory() throws Exception {
        //Given
        RecipeCategoryDto recipeCategoryDto = RecipeCategoryDto.builder()
                .recipeCategoryId(1L)
                .recipeCategoryName("Fast Food")
                .build();
        RecipeCategory recipeCategory1 = new RecipeCategory(1L, "Fast Food", new ArrayList<>());

        Optional<RecipeCategory> recipeCategory = Optional.of(recipeCategory1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(recipeCategoryDto);

        when(recipeCategoryService.getRecipeCategory(ArgumentMatchers.any())).thenReturn(recipeCategory);
        when(recipeCategoryMapper.mapToRecipeCategoryDto(ArgumentMatchers.any())).thenReturn(recipeCategoryDto);

        //When & Then
        mockMvc.perform(put("/v1/recipecategories").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeCategoryId", is(recipeCategoryDto.getRecipeCategoryId().intValue())))
                .andExpect(jsonPath("$.recipeCategoryName", is("Fast Food")));

    }


    @Test
    public void shouldCreateRecipeCategory() throws Exception {
        //Given
        RecipeCategoryDto recipeCategoryDto = RecipeCategoryDto.builder()
                .recipeCategoryId(1L)
                .recipeCategoryName("Fast Food")
                .build();
        RecipeCategory recipeCategory1 = new RecipeCategory(1L, "Fast Food", new ArrayList<>());

        Gson gson = new Gson();
        String jsonContent = gson.toJson(recipeCategoryDto);

        when(recipeCategoryMapper.mapToRecipeCategory(any(RecipeCategoryDto.class))).thenReturn(recipeCategory1);
        when(recipeCategoryMapper.mapToRecipeCategoryDto(recipeCategory1)).thenReturn(recipeCategoryDto);
        when(recipeCategoryService.saveRecipeCategory(any(RecipeCategory.class))).then(returnsFirstArg());

        System.out.println(jsonContent);
        //When & Then
        mockMvc.perform(post("/v1/recipecategories")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recipeCategoryId", is(1)))
                .andExpect(jsonPath("$.recipeCategoryName", is("Fast Food")));


    }

    @Test
    public void shouldDeleteRecipeCategory() throws Exception {
        //When&Then
        mockMvc.perform(delete("/v1/recipecategories/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(recipeCategoryService, times(1)).deleteRecipeCategory(1L);
    }


}