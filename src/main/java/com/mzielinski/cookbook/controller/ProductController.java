package com.mzielinski.cookbook.controller;

import com.mzielinski.cookbook.domain.dto.ProductDto;
import com.mzielinski.cookbook.exception.ProductGroupNotFoundException;
import com.mzielinski.cookbook.exception.ProductNotFoundException;
import com.mzielinski.cookbook.exception.RecipeNotFoundException;
import com.mzielinski.cookbook.mapper.ProductMapper;
import com.mzielinski.cookbook.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/v1")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductMapper productMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductController.class);

    @RequestMapping(method = RequestMethod.GET, value = "/products")
    public List<ProductDto> getProducts() {
        return productMapper.mapToProductsDtoList(productService.getAllProducts());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/products/{productId}")
    public ProductDto getProduct(@PathVariable Long productId) throws ProductNotFoundException {
        return productMapper.mapToProductDto(productService.getProduct(productId).orElseThrow(() -> new ProductNotFoundException("Product with id: " + productId + "was not found.")));
    }

    @RequestMapping(method = RequestMethod.GET, value = "/products/productgroups/{productGroupId}")
    public List<ProductDto> getProductByProductGroup(@PathVariable Long productGroupId) {
        return productMapper.mapToProductsDtoList(productService.getProductsByProductGroup(productGroupId));

    }

    @RequestMapping(method = RequestMethod.GET, value = "/products/recipe/list/{recipeId}")
    public List<ProductDto> getProductsByRecipe(@PathVariable Long recipeId) {
        return productMapper.mapToProductsDtoList(productService.getProductsByRecipe(recipeId));

    }

    @RequestMapping(method = RequestMethod.POST, value = "/products", consumes = APPLICATION_JSON_VALUE)
    public ProductDto createProduct(@RequestBody ProductDto productDto) throws ProductGroupNotFoundException {
        LOGGER.info("Creating new Product");
        return productMapper.mapToProductDto(productService.saveProduct(productMapper.mapToProduct(productDto)));
    }

    @RequestMapping(method = RequestMethod.PUT, value = "/products")
    public ProductDto updateProduct(@RequestBody ProductDto productDto) {
        LOGGER.info("Product has been updated");
        return productMapper.mapToProductDto(productService.saveProduct(productMapper.mapToProduct(productDto)));

    }

    @RequestMapping(method = RequestMethod.DELETE, value = "products/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        LOGGER.info("Deleting product with id: " + productId);
        productService.deleteProduct(productId);
    }

    //GET MAIN PRODUCT FROM RECIPE
    @RequestMapping(method = RequestMethod.GET, value = "/products/recipe/{recipeId}")
    public ProductDto getMainProductFormRecipe(@PathVariable Long recipeId) throws RecipeNotFoundException {
        return
                productMapper.mapToProductDto(
                productService.getMainProductFromRecipe(recipeId).orElseThrow(() -> new RecipeNotFoundException("Product with id: " + recipeId + "was not found.")));
    }







}
