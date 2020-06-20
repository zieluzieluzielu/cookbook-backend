package com.mzielinski.cookbook.mapper;

import com.mzielinski.cookbook.domain.Product;
import com.mzielinski.cookbook.domain.ProductGroup;
import com.mzielinski.cookbook.domain.dto.ProductDto;
import com.mzielinski.cookbook.repository.ProductGroupRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ProductMapperTest {
    @InjectMocks
    ProductMapper productMapper;

    @Mock
    ProductGroupRepository productGroupRepository;

    @Test
    public void mapToProductTest() {
        //Given
        ProductDto productDto = new ProductDto(1L, "Chicken", 1L);
        ProductGroup productGroup = new ProductGroup(1L, "Vegetables", new ArrayList<>());

        //When
        when(productGroupRepository.findById(1L)).thenReturn(Optional.of(productGroup));
        Product product = productMapper.mapToProduct(productDto);

        //Then
        assertEquals(productDto.getProductName(), product.getProductName());
    }

    @Test
    public void mapToProductDtoTest() {
        //Given
        Product product = new Product(1L, "Chicken", new ProductGroup(), new ArrayList<>());

        //When

        ProductDto productDto = productMapper.mapToProductDto(product);
        //Then
        assertEquals(product.getProductName(), productDto.getProductName());
    }

    @Test
    public void mapToProductDtoListTest() {
        //Given
        List<Product> productsList = new ArrayList<>();

        Product product1 = new Product(1L, "Chicken", new ProductGroup(), new ArrayList<>());
        ;
        Product product2 = new Product(2L, "Pork", new ProductGroup(), new ArrayList<>());
        productsList.add(product1);
        productsList.add(product2);


        //When
        List<ProductDto> productDtoList = productMapper.mapToProductsDtoList(productsList);

        //Then
        assertEquals(2, productDtoList.size());
        assertEquals("Chicken", productDtoList.get(0).getProductName());
        assertEquals("Pork", productDtoList.get(1).getProductName());
    }
}
