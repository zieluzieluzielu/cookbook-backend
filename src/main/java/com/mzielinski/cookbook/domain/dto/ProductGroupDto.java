package com.mzielinski.cookbook.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class ProductGroupDto {
    private Long productGroupId;
    private String groupName;

    public ProductGroupDto(String groupName) {
        this.groupName = groupName;
    }
}
