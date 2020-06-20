package com.mzielinski.cookbook.service;

import com.mzielinski.cookbook.domain.Ingredient;
import com.mzielinski.cookbook.domain.dto.EdamamDto;
import com.mzielinski.cookbook.edamam.client.EdamamClient;
import com.mzielinski.cookbook.exception.IngredientNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EdamamService {

    @Autowired
    private EdamamClient edamamClient;

    @Autowired
    private IngredientService ingredientService;

    public EdamamDto getNutrition(Ingredient ingredient) throws IngredientNotFoundException {
        return edamamClient.getNutritionData(ingredientService.ingredientToAnalyze(ingredient));

    }

}
