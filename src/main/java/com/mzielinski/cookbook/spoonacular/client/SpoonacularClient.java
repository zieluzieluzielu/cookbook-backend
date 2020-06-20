package com.mzielinski.cookbook.spoonacular.client;

import com.mzielinski.cookbook.domain.Product;
import com.mzielinski.cookbook.domain.dto.SpoonacularDto;
import com.mzielinski.cookbook.exception.SpoonacularNotAvailableException;
import com.mzielinski.cookbook.spoonacular.config.SpoonacularConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.Arrays;

import static java.util.Optional.ofNullable;

@Component
public class SpoonacularClient {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpoonacularClient.class);

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    private SpoonacularConfig spoonacularConfig;

    public URI prepareUrl(Product product) {

        String foodName = product.getProductName();
        return UriComponentsBuilder.fromHttpUrl(spoonacularConfig.getSpoonacularApiEndpoint())
                .queryParam("food", foodName)
                .queryParam("apiKey", spoonacularConfig.getSpoonacularApiKey())
                .build().encode().toUri();
    }

    public ResponseEntity<SpoonacularDto> getWineData(Product product)  {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            headers.add("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.99 Safari/537.36");
            HttpEntity<String> entity = new HttpEntity<String>("parameters", headers);
            ResponseEntity<SpoonacularDto> wineResponse = restTemplate.exchange(prepareUrl(product), HttpMethod.GET, entity, SpoonacularDto.class);
            return ofNullable(wineResponse).orElseThrow(SpoonacularNotAvailableException::new);

        } catch (RestClientException e) {
            LOGGER.error(e.getMessage(), e);
            return null;
        }

    }

}
