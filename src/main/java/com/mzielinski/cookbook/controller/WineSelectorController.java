package com.mzielinski.cookbook.controller;

import com.mzielinski.cookbook.domain.Product;
import com.mzielinski.cookbook.domain.dto.SpoonacularDto;
import com.mzielinski.cookbook.exception.ProductNotFoundException;
import com.mzielinski.cookbook.service.ProductService;
import com.mzielinski.cookbook.spoonacular.facade.SpoonacularFacade;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/v1")
public class WineSelectorController {
    private static final Logger LOGGER = LoggerFactory.getLogger(WineSelectorController.class);

    @Autowired
    private ProductService productService;

    @Autowired
    private SpoonacularFacade spoonacularFacade;

    @RequestMapping(method = RequestMethod.GET, value = "wine/product/{productId}")
    public ResponseEntity<SpoonacularDto> getWine(@PathVariable Long productId) throws ProductNotFoundException   {
        Product product = productService.getProduct(productId).orElseThrow(() -> new ProductNotFoundException("Product with id: " + productId + " was not found"));
        //return spoonacularClient.getWineData(product);
        return spoonacularFacade.getWineDetailsResponseEntity(product);


    }

    @RequestMapping(method = RequestMethod.GET, value = "wine/recipe/{recipeId}")
    public ResponseEntity<SpoonacularDto> getWineFromRecipe(@PathVariable Long recipeId) throws ProductNotFoundException {
        Product product = productService.getMainProductFromRecipe(recipeId).orElseThrow(() -> new ProductNotFoundException("Recipe with id: " + recipeId + " was not found, or main product was not set"));
        //return spoonacularClient.getWineData(product);
        return spoonacularFacade.getWineDetailsResponseEntity(product);


    }
}

