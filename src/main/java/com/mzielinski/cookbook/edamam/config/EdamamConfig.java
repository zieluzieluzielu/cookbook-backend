package com.mzielinski.cookbook.edamam.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class EdamamConfig {

    @Value("${edamam.api.endpoint.prod}")
    public String edamamApiEndpoint;

    @Value("${edamam.app.key}")
    public String edamamAppKey;

    @Value("${edamam.app.id}")
    public String edamamAppId;






}
