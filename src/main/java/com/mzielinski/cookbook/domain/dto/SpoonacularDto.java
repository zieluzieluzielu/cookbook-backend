package com.mzielinski.cookbook.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SpoonacularDto {
    @JsonProperty("pairedWines")
    private List<String> wineTypeDtoList;
    @JsonProperty("pairingText")
    private String pairingText;
    @JsonProperty("productMatches")
    private List<ProductMatchesDto> productMatchesDto;
}
