package com.mzielinski.cookbook.edamam.client;

import com.mzielinski.cookbook.domain.dto.EdamamDto;
import com.mzielinski.cookbook.edamam.config.EdamamConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@Component
public class EdamamClient {

    private static final Logger LOGGER = LoggerFactory.getLogger(EdamamClient.class);
    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private EdamamConfig edamamConfig;


    public URI prepareUrl(String ingredient) {

        //String product = ingredient.getProduct().getProductName();
        return UriComponentsBuilder.fromHttpUrl(edamamConfig.getEdamamApiEndpoint())
                .queryParam("ingr", ingredient)
                .queryParam("app_key", edamamConfig.getEdamamAppKey())
                .queryParam("app_id", edamamConfig.getEdamamAppId())
                .build().encode().toUri();


    }


    public EdamamDto getNutritionData(String ingredient) {
        try {
            EdamamDto edamamResponse = restTemplate.getForObject(prepareUrl(ingredient), EdamamDto.class);
            return edamamResponse;

        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }

    }
}
