package com.mzielinski.cookbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "RECIPE_CATEGORIES")
public class RecipeCategory {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RECIPE_CATEGORY_ID", unique = true)
    private Long recipeCategoryId;

    @Column(name = "RECIPE_CATEGORY_NAME")
    private String recipeCategoryName;

    @OneToMany(
            targetEntity = Recipe.class,
            mappedBy = "recipeCategory",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER
    )
    public List<Recipe> recipes = new ArrayList<>();

    public RecipeCategory(String recipeCategoryName) {
        this.recipeCategoryName = recipeCategoryName;
    }
}
