package com.mzielinski.cookbook.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class IngredientDto {
    Long ingredientId;
    BigDecimal amount;
    String unit;
    Long productId;
    Long recipeId;
    boolean mainProduct;

    public IngredientDto(BigDecimal amount, String unit, Long productId, Long recipeId, boolean mainProduct) {
        this.amount = amount;
        this.unit = unit;
        this.productId = productId;
        this.recipeId = recipeId;
        this.mainProduct = mainProduct;
    }

}
