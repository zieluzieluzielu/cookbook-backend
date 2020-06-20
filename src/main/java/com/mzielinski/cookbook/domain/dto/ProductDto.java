package com.mzielinski.cookbook.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProductDto {
    private Long productId;
    private String productName;
    private Long groupId;

    public ProductDto(String productName, Long groupId) {
        this.productName = productName;
        this.groupId = groupId;
    }
}
