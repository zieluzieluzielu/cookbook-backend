package com.mzielinski.cookbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Carbohydrates {
    private BigDecimal quantity;
    private String unit;
}
