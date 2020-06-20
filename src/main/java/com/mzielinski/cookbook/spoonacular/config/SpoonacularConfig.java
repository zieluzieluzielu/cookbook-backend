package com.mzielinski.cookbook.spoonacular.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class SpoonacularConfig {
    @Value("${spoonacular.api.endpoint.prod}")
    public String spoonacularApiEndpoint;

    @Value("${spoonacular.api.key}")
    public String spoonacularApiKey;

}
