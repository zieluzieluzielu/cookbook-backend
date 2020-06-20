package com.mzielinski.cookbook.mapper;

import com.mzielinski.cookbook.domain.Product;
import com.mzielinski.cookbook.domain.dto.ProductDto;
import com.mzielinski.cookbook.exception.ProductGroupNotFoundException;
import com.mzielinski.cookbook.repository.ProductGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductMapper {
    @Autowired
    private ProductGroupRepository productGroupRepository;

    public Product mapToProduct(final ProductDto productDto) throws ProductGroupNotFoundException {
        return new Product(
                productDto.getProductName(),
                productGroupRepository.findById(productDto.getGroupId()).orElseThrow(() -> new ProductGroupNotFoundException("Product group was not found")));

    }

    public ProductDto mapToProductDto(final Product product) {
        return new ProductDto(product.getProductName(), product.getProductGroup().getProductGroupId());
    }

    public List<ProductDto> mapToProductsDtoList(final List<Product> productsList) {
        return productsList.stream()
                .map(this::mapToProductDto)
                .collect(Collectors.toList());
    }

}
