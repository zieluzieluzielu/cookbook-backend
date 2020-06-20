package com.mzielinski.cookbook.repository;

import com.mzielinski.cookbook.domain.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Repository
@Transactional
public interface RecipeRepository extends JpaRepository<Recipe, Long> {
    @Override
    List<Recipe> findAll();

    @Override
    Recipe save(Recipe recipe);

    @Override
    Optional<Recipe> findById(Long recipeId);

    //List<Recipe> findByPreparationTime(Long preparationTime);

    @Query(nativeQuery = true)
    List<Recipe> retrieveRecipesByPreparationTimeShorterThan(@Param("PREPARATION_TIME") long preparationTime);

    @Query(nativeQuery = true)
    List<Recipe> retrieveRecipesByCategoryId(@Param("RECIPE_CATEGORY_ID") long recipeCategory);

    @Query(nativeQuery = true)
    List<Recipe> retrieveRecipesByProduct(@Param("PRODUCT_ID") long productId);

    @Override
    long count();

 /*   @Query(nativeQuery = true)
    Optional<Recipe> retrieveRandomRecipe();
*/
}
