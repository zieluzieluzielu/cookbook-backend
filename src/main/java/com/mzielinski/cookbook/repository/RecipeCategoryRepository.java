package com.mzielinski.cookbook.repository;

import com.mzielinski.cookbook.domain.RecipeCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;
@Repository
@Transactional
public interface RecipeCategoryRepository extends JpaRepository<RecipeCategory, Long> {
    @Override
    List<RecipeCategory> findAll();

    @Override
    RecipeCategory save(RecipeCategory recipeCategory);

    @Override
    Optional<RecipeCategory> findById(Long recipeCategoryId);

}
