package com.mzielinski.cookbook.service;

import com.mzielinski.cookbook.domain.Product;
import com.mzielinski.cookbook.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    //private final ProductGroup productGroup;


    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }


//getProductsByRecipe

    public Product saveProduct(final Product product) {
        return productRepository.save(product);
    }

    public Optional<Product> getProduct(final Long id) {
        return productRepository.findById(id);
    }

    public void deleteProduct(final Long id) {
        productRepository.deleteById(id);
    }

    public List<Product> getProductsByProductGroup(Long productGroupId) {
        return productRepository.retrieveProductByProductGroup(productGroupId);
    }

    public List<Product> getProductsByRecipe(Long recipeId) {
        return productRepository.retrieveProductsByRecipe(recipeId);
    }

    public Optional<Product> getMainProductFromRecipe(final Long recipeId) {
        return productRepository.retrieveMainProductFromRecipe(recipeId);

    }

}
