package com.mzielinski.cookbook.repository;

import com.mzielinski.cookbook.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Repository
@Transactional
public interface ProductRepository extends JpaRepository<Product, Long> {
    @Override
    List<Product> findAll();

    @Override
    Product save(Product product);

    @Override
    Optional<Product> findById(Long productId);

    @Query(nativeQuery = true)
    List<Product> retrieveProductByProductGroup(@Param("PRODUCT_GROUP_ID") long productGroupId);

    @Query(nativeQuery = true)
    Optional<Product> retrieveMainProductFromRecipe(@Param("RECIPE_ID") long recipeId);

    @Query(nativeQuery = true)
    List<Product> retrieveProductsByRecipe(@Param("RECIPE_ID") long recipeId);






}
