package com.mzielinski.cookbook.spoonacular.validator;

import com.mzielinski.cookbook.domain.Product;
import com.mzielinski.cookbook.domain.Spoonacular;
import com.mzielinski.cookbook.domain.dto.SpoonacularDto;
import com.mzielinski.cookbook.exception.WineNotFoundException;
import com.mzielinski.cookbook.service.SpoonacularService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SpoonacularValidator {
    @Autowired
    private SpoonacularService spoonacularService;

    private static final Logger LOGGER = LoggerFactory.getLogger(SpoonacularValidator.class);

    public ResponseEntity<SpoonacularDto> validateWineSelector(final Spoonacular spoonacular, final Product product) throws WineNotFoundException {


        if (spoonacular.getWineTypeDtoList().size() != 0 || (!spoonacular.getPairingText().equals("") || !spoonacular.getPairingText().isEmpty()) || spoonacular.getProductMatchesDto().size() != 0) {
            LOGGER.info("Spoonacular app was used properly");
            return spoonacularService.getWine(product);
        } else {
            LOGGER.info("Something could be wrong with product. Unable to find wine in Spoonacular database");
            throw new WineNotFoundException("Wine matching not found");

        }


    }
}
