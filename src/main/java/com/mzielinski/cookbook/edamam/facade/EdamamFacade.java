package com.mzielinski.cookbook.edamam.facade;

import com.mzielinski.cookbook.domain.Edamam;
import com.mzielinski.cookbook.domain.Ingredient;
import com.mzielinski.cookbook.domain.dto.*;
import com.mzielinski.cookbook.edamam.validator.EdamamValidator;
import com.mzielinski.cookbook.exception.IngredientNotFoundException;
import com.mzielinski.cookbook.mapper.EdamamMapper;
import com.mzielinski.cookbook.service.EdamamService;
import com.mzielinski.cookbook.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class EdamamFacade {

    @Autowired
    private EdamamService edamamService;

    @Autowired
    private EdamamMapper edamamMapper;

    @Autowired
    private EdamamValidator edamamValidator;

    @Autowired
    private IngredientService ingredientService;

    public EdamamDto getNutrition(Ingredient ingredient) throws IngredientNotFoundException {

        Edamam edamam = edamamMapper.mapToEdamam(edamamService.getNutrition(ingredient));
        edamamValidator.validateNutrition(edamam, ingredient);
        return edamamMapper.mapToEdamamDto(edamam);
    }

    public List<EdamamDto> getNutritionList(List<Ingredient> ingredientList) {
        List<Edamam> edamamList = ingredientList.stream()
                .map(x -> getNutrition(x))
                .map(x -> edamamMapper.mapToEdamam(x))
                .collect(Collectors.toList());

        return edamamMapper.mapToEdamamDtoList(edamamList);
    }


    public EdamamDto getSumOfNutrition(List<Ingredient> ingredientList) {

        List<Edamam> edamamList = ingredientList.stream()
                .map(x -> getNutrition(x))
                .map(x -> edamamMapper.mapToEdamam(x))
                .collect(Collectors.toList());

        BigDecimal calories =
                edamamList.stream()
                        .map(x -> x.getNutrients().getKcal().getQuantity()).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal fat =
                edamamList.stream()
                        .map(x -> x.getNutrients().getFat().getQuantity()).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal protein =
                edamamList.stream()
                        .map(x -> x.getNutrients().getProtein().getQuantity()).reduce(BigDecimal.ZERO, BigDecimal::add);
        BigDecimal carbo =
                edamamList.stream()
                        .map(x -> x.getNutrients().getCarbohydrates().getQuantity()).reduce(BigDecimal.ZERO, BigDecimal::add);

        EdamamDto edamamSum = new EdamamDto(new NutrientsDto(new KcalDto(calories, "kcal"), new FatDto(fat, "g"), new ProteinDto(protein, "g"), new CarbohydratesDto(carbo, "g")));

        return edamamSum;
    }


}
