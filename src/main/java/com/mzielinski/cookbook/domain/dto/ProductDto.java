package com.mzielinski.cookbook.domain.dto;

import lombok.Builder;
import lombok.Getter;
@Getter
@Builder
public class ProductDto {
    private Long productId;
    private String productName;
    private Long groupId;

    public ProductDto(Long productId, String productName, Long groupId) {
        this.productId = productId;
        this.productName = productName;
        this.groupId = groupId;
    }
}
