package com.mzielinski.cookbook.service;

import com.mzielinski.cookbook.domain.Product;
import com.mzielinski.cookbook.domain.ProductGroup;
import com.mzielinski.cookbook.repository.ProductRepository;
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
public class ProductServiceTest {
    @InjectMocks
    ProductService productService;

    @Mock
    ProductRepository productRepository;

    @Test
    public void getAllProducts() {
        //Given
        List<Product> productList = new ArrayList<>();
        Product product = new Product();
        productList.add(product);

        when(productRepository.findAll()).thenReturn(productList);

        //When
        List<Product> allProducts = productService.getAllProducts();

        //Then
        assertEquals(1, allProducts.size());

    }

    @Test
    public void getProductsByProductGroup() {
        //Given
        List<Product> productList = new ArrayList<>();
        Product product = new Product(1L, "Chicken breast", new ProductGroup(6L, "Meat", new ArrayList<>()), new ArrayList<>());
        productList.add(product);

        when(productRepository.retrieveProductByProductGroup(6L)).thenReturn(productList);

        //When
        List<Product> productsByProductGroup = productService.getProductsByProductGroup(6L);

        //Then
        assertEquals(1, productsByProductGroup.size());

    }

    @Test
    public void getProduct() {
        //Given
        Product product = new Product(1L, "Chicken breast", new ProductGroup(), new ArrayList<>());

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        //When
        Optional<Product> getProduct = productService.getProduct(1L);
        //Then
        assertTrue(getProduct.isPresent());
        assertEquals("Chicken breast", getProduct.get().getProductName());
    }

    @Test
    public void getMainProductFromRecipe() {
        //Given
        Product product = new Product(1L, "Chicken breast", new ProductGroup(), new ArrayList<>());

        when(productRepository.retrieveMainProductFromRecipe(6L)).thenReturn(Optional.of(product));

        //When
        Optional<Product> getMainProduct = productService.getMainProductFromRecipe(6L);
        //Then
        assertTrue(getMainProduct.isPresent());
        assertEquals("Chicken breast", getMainProduct.get().getProductName());
    }


    @Test
    public void saveProduct() {
        //Given
        Product product = new Product(1L, "Chicken breast", new ProductGroup(), new ArrayList<>());

        when(productRepository.save(product)).thenReturn(product);
        //When
        Product savedProduct = productService.saveProduct(product);
        //Then
        assertEquals("Chicken breast", savedProduct.getProductName());
    }

    @Test
    public void deleteProduct() {
        //Given
        Product product = new Product(1L, "Chicken breast", new ProductGroup(), new ArrayList<>());


        //When
        productService.deleteProduct(1L);

        //Then
        verify(productRepository, times(1)).deleteById(anyLong());
        assertFalse(productRepository.findById(product.getProductId()).isPresent());

    }
}
