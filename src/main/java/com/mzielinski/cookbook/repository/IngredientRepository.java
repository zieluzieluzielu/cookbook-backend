package com.mzielinski.cookbook.repository;

import com.mzielinski.cookbook.domain.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface IngredientRepository extends JpaRepository<Ingredient, Long> {
    @Override
    List<Ingredient> findAll();

    @Override
    Ingredient save(Ingredient ingredient);

    @Override
    Optional<Ingredient> findById(Long ingredientId);

    //List<Ingredient> findByRecipe(Recipe recipe);

    @Query(nativeQuery = true)
    List<Ingredient> retrieveIngredientByRecipe(@Param("RECIPE_ID") long recipe);

    @Override
    void deleteById(Long ingredientId);


}
