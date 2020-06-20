package com.mzielinski.cookbook.service;

import com.mzielinski.cookbook.domain.Product;
import com.mzielinski.cookbook.domain.dto.SpoonacularDto;
import com.mzielinski.cookbook.spoonacular.client.SpoonacularClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SpoonacularService {

    @Autowired
    SpoonacularClient spoonacularClient;

    public ResponseEntity<SpoonacularDto> getWine(Product product) {
        return spoonacularClient.getWineData(product);
    }
}

