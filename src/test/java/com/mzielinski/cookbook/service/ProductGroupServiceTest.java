package com.mzielinski.cookbook.service;

import com.mzielinski.cookbook.domain.ProductGroup;
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
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductGroupServiceTest {
    @InjectMocks
    ProductGroupService productGroupService;

    @Mock
    ProductGroupRepository productGroupRepository;

    @Test
    public void getAllProductGroups() {
        //Given
        List<ProductGroup> productGroupList = new ArrayList<>();
        ProductGroup productGroup = new ProductGroup();
        productGroupList.add(productGroup);

        when(productGroupRepository.findAll()).thenReturn(productGroupList);

        //When
        List<ProductGroup> allProductGroups = productGroupService.getAllProductGroups();

        //Then
        assertEquals(1, allProductGroups.size());

    }

    @Test
    public void getProductGroup() {
        //Given
        ProductGroup productGroup = new ProductGroup(1L, "Vegetables", new ArrayList<>());
        when(productGroupRepository.findById(1L)).thenReturn(Optional.of(productGroup));

        //When
        Optional<ProductGroup> getProductGroup = productGroupService.getProductGroup(1L);
        //Then
        assertTrue(getProductGroup.isPresent());
        assertEquals("Vegetables", getProductGroup.get().getGroupName());
    }


    @Test
    public void saveProductGroup() {
        //Given
        ProductGroup productGroup = new ProductGroup(1L, "Vegetables", new ArrayList<>());
        when(productGroupRepository.save(productGroup)).thenReturn(productGroup);
        //When
        ProductGroup savedProductGroup = productGroupService.saveProductGroup(productGroup);
        //Then
        assertEquals("Vegetables", savedProductGroup.getGroupName());
    }

    @Test
    public void deleteProductGroup() {
        //Given
        ProductGroup productGroup = new ProductGroup(1L, "Vegetables", new ArrayList<>());

        //When
        productGroupService.deleteProductGroup(1L);

        //Then
        verify(productGroupRepository, times(1)).deleteById(anyLong());
        assertFalse(productGroupRepository.findById(productGroup.getProductGroupId()).isPresent());

    }
}
