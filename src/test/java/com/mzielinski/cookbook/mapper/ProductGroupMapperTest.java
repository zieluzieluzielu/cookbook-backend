package com.mzielinski.cookbook.mapper;

import com.mzielinski.cookbook.domain.ProductGroup;
import com.mzielinski.cookbook.domain.dto.ProductGroupDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class ProductGroupMapperTest {
    @InjectMocks
    ProductGroupMapper productGroupMapper;

    @Test
    public void mapToProductGroupTest() {
        //Given
        ProductGroupDto productGroupDto = new ProductGroupDto(1L, "Vegetables");

        //When
        ProductGroup productGroup = productGroupMapper.mapToProductGroup(productGroupDto);

        //Then
        assertEquals(productGroupDto.getGroupName(), productGroup.getGroupName());
    }

    @Test
    public void mapToProductGroupDtoTest() {
        //Given
        ProductGroup productGroup = new ProductGroup(1L, "Vegetables", new ArrayList<>());

        //When
        ProductGroupDto productGroupDto = productGroupMapper.mapToProductGroupDto(productGroup);
        //Then
        assertEquals(productGroup.getGroupName(), productGroupDto.getGroupName());
    }

    @Test
    public void mapToProductGroupDtoListTest() {
        //Given
        List<ProductGroup> productGroupsList = new ArrayList<>();

        ProductGroup productGroup1 = new ProductGroup(1L, "Vegetables", new ArrayList<>());
        ;
        ProductGroup productGroup2 = new ProductGroup(2L, "Meat", new ArrayList<>());
        productGroupsList.add(productGroup1);
        productGroupsList.add(productGroup2);


        //When
        List<ProductGroupDto> productGroupDtoList = productGroupMapper.mapToProductGroupsDtoList(productGroupsList);

        //Then
        assertEquals(2, productGroupDtoList.size());
        assertEquals("Vegetables", productGroupDtoList.get(0).getGroupName());
        assertEquals("Meat", productGroupDtoList.get(1).getGroupName());
    }
}
