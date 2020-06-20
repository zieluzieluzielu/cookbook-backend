package com.mzielinski.cookbook.service;

import com.mzielinski.cookbook.domain.RecipeCategory;
import com.mzielinski.cookbook.repository.RecipeCategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeCategoryService {

    @Autowired
    RecipeCategoryRepository recipeCategoryRepository;

    public List<RecipeCategory> getAllRecipeCategories() {
        return recipeCategoryRepository.findAll();
    }


    public RecipeCategory saveRecipeCategory(final RecipeCategory recipeCategory) {
        return recipeCategoryRepository.save(recipeCategory);
    }

    public Optional<RecipeCategory> getRecipeCategory(final Long id) {
        return recipeCategoryRepository.findById(id);
    }

    public void deleteRecipeCategory(final Long id) {
        recipeCategoryRepository.deleteById(id);
    }

}
