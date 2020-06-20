package com.mzielinski.cookbook.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductMatchesDto {
    @JsonProperty("title")
    private String wineTitle;
    @JsonProperty("description")
    private String wineDescription;
    @JsonProperty("price")
    private String winePrice;

}
