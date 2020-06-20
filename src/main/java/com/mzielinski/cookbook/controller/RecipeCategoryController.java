package com.mzielinski.cookbook.controller;

import com.mzielinski.cookbook.domain.dto.RecipeCategoryDto;
import com.mzielinski.cookbook.exception.RecipeCategoryNotFoundException;
import com.mzielinski.cookbook.mapper.RecipeCategoryMapper;
import com.mzielinski.cookbook.service.RecipeCategoryService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1")
public class RecipeCategoryController {
    @Autowired
    private RecipeCategoryMapper recipeCategoryMapper;

    @Autowired
    private RecipeCategoryService recipeCategoryService;

    private static final Logger LOGGER = LoggerFactory.getLogger(RecipeCategoryController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/recipecategories")
    public List<RecipeCategoryDto> getRecipeCategories() {
        return recipeCategoryMapper.mapToRecipeCategoriesDtoList(recipeCategoryService.getAllRecipeCategories());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/recipecategories/{recipeCategoryId}")
    public RecipeCategoryDto getRecipeCategory(@PathVariable Long recipeCategoryId) throws RecipeCategoryNotFoundException {
        return recipeCategoryMapper.mapToRecipeCategoryDto(recipeCategoryService.getRecipeCategory(recipeCategoryId).orElseThrow(() -> new RecipeCategoryNotFoundException("Recipe category with id: "+recipeCategoryId+" was not found")));
    }

    @RequestMapping(method = RequestMethod.POST, value = "/recipecategories", consumes = APPLICATION_JSON_VALUE)
    public RecipeCategoryDto createRecipeCategory(@RequestBody RecipeCategoryDto recipeCategoryDto) {
        LOGGER.info("Creating new recipe category");
        return recipeCategoryMapper.mapToRecipeCategoryDto(recipeCategoryService.saveRecipeCategory(recipeCategoryMapper.mapToRecipeCategory(recipeCategoryDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/recipecategories")
    public RecipeCategoryDto updateRecipeCategory(@RequestBody RecipeCategoryDto recipeCategoryDto) {
        LOGGER.info("Recipe category has been updated");
        return recipeCategoryMapper.mapToRecipeCategoryDto(recipeCategoryService.saveRecipeCategory(recipeCategoryMapper.mapToRecipeCategory(recipeCategoryDto)));
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "recipecategories/{recipeCategoryId}")
    public void deleteRecipeCategory(@PathVariable Long recipeCategoryId) {
        LOGGER.info("Deleting recipe category with id: " + recipeCategoryId);
        recipeCategoryService.deleteRecipeCategory(recipeCategoryId);
    }


}
