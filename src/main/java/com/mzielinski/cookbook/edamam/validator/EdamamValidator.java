package com.mzielinski.cookbook.edamam.validator;

import com.mzielinski.cookbook.domain.Edamam;
import com.mzielinski.cookbook.domain.Ingredient;
import com.mzielinski.cookbook.domain.dto.EdamamDto;
import com.mzielinski.cookbook.exception.NutrientNotFoundException;
import com.mzielinski.cookbook.service.EdamamService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component

public class EdamamValidator {
    @Autowired
    private EdamamService edamamService;

    private static final Logger LOGGER = LoggerFactory.getLogger(EdamamValidator.class);

    public EdamamDto validateNutrition(final Edamam edamam, final Ingredient ingredient) throws NutrientNotFoundException {
        if ((edamam.getNutrients().getKcal().getQuantity().equals(new BigDecimal(0)) &&
                edamam.getNutrients().getFat().getQuantity().equals(new BigDecimal(0)) &&
                edamam.getNutrients().getProtein().getQuantity().equals(new BigDecimal(0)) &&
                edamam.getNutrients().getCarbohydrates().getQuantity().equals(new BigDecimal(0)))
                ||
                (edamam.getNutrients().getKcal().getQuantity() == null &&
                        edamam.getNutrients().getFat().getQuantity() == null &&
                        edamam.getNutrients().getProtein().getQuantity() == null &&
                        edamam.getNutrients().getCarbohydrates().getQuantity() == null)) {
            LOGGER.info("Something could be wrong with ingredient - null / zero values provided");
            throw new NutrientNotFoundException("Nutriend data not found");
        } else {
            LOGGER.info("Edamam app was used properly");
            return edamamService.getNutrition(ingredient);
        }

    }
}
