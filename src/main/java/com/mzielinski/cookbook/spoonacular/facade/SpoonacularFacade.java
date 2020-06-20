package com.mzielinski.cookbook.spoonacular.facade;

import com.mzielinski.cookbook.domain.Product;
import com.mzielinski.cookbook.domain.Spoonacular;
import com.mzielinski.cookbook.domain.dto.SpoonacularDto;
import com.mzielinski.cookbook.mapper.SpoonacularMapper;
import com.mzielinski.cookbook.service.SpoonacularService;
import com.mzielinski.cookbook.spoonacular.validator.SpoonacularValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class SpoonacularFacade {


    @Autowired
    private SpoonacularService spoonacularService;

    @Autowired
    private SpoonacularMapper spoonacularMapper;

    @Autowired
    private SpoonacularValidator spoonacularValidator;


    public ResponseEntity<SpoonacularDto> getWineDetailsResponseEntity(Product product) {
        Spoonacular spoonacular = spoonacularMapper.mapToSpoonacularFromResponseEntity(spoonacularService.getWine(product));
        return spoonacularValidator.validateWineSelector(spoonacular, product);


    }


}
