package com.mzielinski.cookbook.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class ProductGroupDto {
    private Long productGroupId;
    private String groupName;

}
