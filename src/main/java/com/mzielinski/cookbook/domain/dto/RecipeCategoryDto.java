package com.mzielinski.cookbook.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class RecipeCategoryDto {
    private Long recipeCategoryId;
    private String recipeCategoryName;

    public RecipeCategoryDto(String recipeCategoryName) {
        this.recipeCategoryName = recipeCategoryName;
    }
}
