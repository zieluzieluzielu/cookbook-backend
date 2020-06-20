package com.mzielinski.cookbook.mapper;

import com.mzielinski.cookbook.domain.RecipeCategory;
import com.mzielinski.cookbook.domain.dto.RecipeCategoryDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class RecipeCategoryMapperTest {
    @InjectMocks
    RecipeCategoryMapper recipeCategoryMapper;

    @Test
    public void mapToRecipeCategoryTest() {
        //Given
        RecipeCategoryDto recipeCategoryDto = new RecipeCategoryDto(1L, "Fast Food");


        //When
        RecipeCategory recipeCategory = recipeCategoryMapper.mapToRecipeCategory(recipeCategoryDto);

        //Then
        assertEquals(recipeCategoryDto.getRecipeCategoryName(), recipeCategory.getRecipeCategoryName());
    }

    @Test
    public void mapToRecipeCategoryDtoTest() {
        //Given
        RecipeCategory recipeCategory = new RecipeCategory(1L, "Fast Food", new ArrayList<>());

        //When
        RecipeCategoryDto recipeCategoryDto = recipeCategoryMapper.mapToRecipeCategoryDto(recipeCategory);
        //Then
        assertEquals(recipeCategory.getRecipeCategoryName(), recipeCategoryDto.getRecipeCategoryName());
    }

    @Test
    public void mapToRecipeCategoryDtoListTest() {
        //Given
        List<RecipeCategory> recipeCategorysList = new ArrayList<>();

        RecipeCategory recipeCategory1 = new RecipeCategory(1L, "Fast Food", new ArrayList<>());
        ;
        RecipeCategory recipeCategory2 = new RecipeCategory(2L, "Chinese", new ArrayList<>());
        recipeCategorysList.add(recipeCategory1);
        recipeCategorysList.add(recipeCategory2);


        //When
        List<RecipeCategoryDto> recipeCategoryDtoList = recipeCategoryMapper.mapToRecipeCategoriesDtoList(recipeCategorysList);

        //Then
        assertEquals(2, recipeCategoryDtoList.size());
        assertEquals("Fast Food", recipeCategoryDtoList.get(0).getRecipeCategoryName());
        assertEquals("Chinese", recipeCategoryDtoList.get(1).getRecipeCategoryName());
    }
}
