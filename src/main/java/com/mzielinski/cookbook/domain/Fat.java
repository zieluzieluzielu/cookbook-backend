package com.mzielinski.cookbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
public class Fat {
    private BigDecimal quantity;
    private String unit;
}
