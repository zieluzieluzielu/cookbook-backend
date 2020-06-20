package com.mzielinski.cookbook.controller;

import com.google.gson.Gson;
import com.mzielinski.cookbook.domain.*;
import com.mzielinski.cookbook.domain.dto.ProductDto;
import com.mzielinski.cookbook.mapper.ProductMapper;
import com.mzielinski.cookbook.service.ProductService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(ProductController.class)
public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductService productService;

    @MockBean
    private ProductMapper productMapper;


    @Test
    public void shouldRetrieveProductList() throws Exception {
        //Given
        List<ProductDto> productsListDto = new ArrayList<>();
        ProductDto productDto1 = ProductDto.builder()
                .productId(1L)
                .productName("Chicken")
                .groupId(1L)
                .build();

        ProductDto productDto2 = ProductDto.builder()
                .productId(2L)
                .productName("Pork")
                .groupId(1L)
                .build();

        ProductDto productDto3 = ProductDto.builder()
                .productId(3L)
                .productName("Beef")
                .groupId(1L)
                .build();

        productsListDto.add(productDto1);
        productsListDto.add(productDto2);
        productsListDto.add(productDto3);

        when(productMapper.mapToProductsDtoList(productService.getAllProducts())).thenReturn(productsListDto);


        //When & Then
        mockMvc.perform(get("/v1/products").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].productId", is(productsListDto.get(0).getProductId().intValue())))
                .andExpect(jsonPath("$[1].productName", is("Pork")))
                .andExpect(jsonPath("$[2].groupId", is(1)));
    }

    @Test
    public void shouldRetrieveMainProductFromRecipe() throws Exception {
        //Given

        ProductDto productDto = ProductDto.builder()
                .productId(1L)
                .productName("Chicken breast")
                .groupId(1L)
                .build();

        Product product1 = new Product(1L, "Chicken breast", new ProductGroup(), new ArrayList<>());
        Optional<Product> product = Optional.of(product1);


        when(productService.getMainProductFromRecipe(6L)).thenReturn(product);
        when(productMapper.mapToProductDto(any())).thenReturn(productDto);

        //When & Then
        mockMvc.perform(get("/v1/products/recipe/6").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(productDto.getProductId().intValue())))
                .andExpect(jsonPath("$.productName", is("Chicken breast")));

    }

    @Test
    public void shouldRetrieveProductListByProductGroup() throws Exception {
        //Given
        ProductGroup productGroup1 = new ProductGroup(1L, "Meat", new ArrayList<>());

        List<ProductDto> productsListDto = new ArrayList<>();
        ProductDto productDto1 = ProductDto.builder()
                .productId(1L)
                .productName("Chicken")
                .groupId(1L)
                .build();

        ProductDto productDto2 = ProductDto.builder()
                .productId(2L)
                .productName("Pork")
                .groupId(1L)
                .build();

        productsListDto.add(productDto1);
        productsListDto.add(productDto2);

        when(productMapper.mapToProductsDtoList(productService.getProductsByProductGroup(productGroup1.getProductGroupId()))).thenReturn(productsListDto);

        //When & Then
        mockMvc.perform(get("/v1/products/productgroups/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].productId", is(productsListDto.get(0).getProductId().intValue())))
                .andExpect(jsonPath("$[1].productName", is("Pork")))
                .andExpect(jsonPath("$[0].groupId", is(1)));
    }




    @Test
    public void shouldRetrieveProduct() throws Exception {
        //Given
        ProductDto productDto = ProductDto.builder()
                .productId(1L)
                .productName("Chicken")
                .groupId(1L)
                .build();

        Product product1 = new Product(1L, "Chicken", new ProductGroup(), new ArrayList<>());
        Optional<Product> product = Optional.of(product1);

        when(productService.getProduct(any())).thenReturn(product);
        when(productMapper.mapToProductDto(any())).thenReturn(productDto);

        //When & Then
        mockMvc.perform(get("/v1/products/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(productDto.getProductId().intValue())))
                .andExpect(jsonPath("$.productName", is("Chicken")));

    }


    @Test
    public void shouldUpdateProduct() throws Exception {
        //Given
        ProductDto productDto = ProductDto.builder()
                .productId(1L)
                .productName("Chicken")
                .groupId(1L)
                .build();

        Product product1 = new Product(1L, "Chicken", new ProductGroup(), new ArrayList<>());

        Optional<Product> product = Optional.of(product1);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(productDto);

        when(productService.getProduct(any())).thenReturn(product);
        when(productMapper.mapToProductDto(any())).thenReturn(productDto);

        //When & Then
        mockMvc.perform(put("/v1/products").contentType(MediaType.APPLICATION_JSON).characterEncoding("UTF-8").content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(productDto.getProductId().intValue())))
                .andExpect(jsonPath("$.productName", is("Chicken")));

    }


    @Test
    public void shouldCreateProduct() throws Exception {
        //Given
        ProductDto productDto = ProductDto.builder()
                .productId(1L)
                .productName("Chicken")
                .groupId(1L)
                .build();

        Product product1 = new Product(1L, "Chicken", new ProductGroup(), new ArrayList<>());

        Gson gson = new Gson();
        String jsonContent = gson.toJson(productDto);

        when(productMapper.mapToProduct(any(ProductDto.class))).thenReturn(product1);
        when(productMapper.mapToProductDto(product1)).thenReturn(productDto);
        when(productService.saveProduct(any(Product.class))).then(returnsFirstArg());

        System.out.println(jsonContent);
        //When & Then
        mockMvc.perform(post("/v1/products")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.productId", is(1)))
                .andExpect(jsonPath("$.productName", is("Chicken")));


    }

    @Test
    public void shouldDeleteProduct() throws Exception {
        //When&Then
        mockMvc.perform(delete("/v1/products/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        verify(productService, times(1)).deleteProduct(1L);
    }


}