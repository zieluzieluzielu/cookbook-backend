package com.mzielinski.cookbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Spoonacular {

    private List<String> wineTypeDtoList;
    private String pairingText;
    private List<ProductMatches> productMatchesDto;
}
