package com.mzielinski.cookbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Nutrients {
    private Kcal kcal;
    private Fat fat;
    private Protein protein;
    private Carbohydrates carbohydrates;
}
