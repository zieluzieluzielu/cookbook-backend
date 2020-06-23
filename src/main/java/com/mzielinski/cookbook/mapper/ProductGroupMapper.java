package com.mzielinski.cookbook.mapper;

import com.mzielinski.cookbook.domain.ProductGroup;
import com.mzielinski.cookbook.domain.dto.ProductGroupDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductGroupMapper {

    public ProductGroup mapToProductGroup(final ProductGroupDto productGroupDto) {
        return new ProductGroup(productGroupDto.getGroupName());

    }

    public ProductGroupDto mapToProductGroupDto(final ProductGroup productGroup) {
        return new ProductGroupDto(productGroup.getProductGroupId(),productGroup.getGroupName());

    }

    public List<ProductGroupDto> mapToProductGroupsDtoList(final List<ProductGroup> productGroupsList) {
        return productGroupsList.stream()
                .map(this::mapToProductGroupDto)
                .collect(Collectors.toList());
    }
}
