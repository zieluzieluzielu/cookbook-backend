package com.mzielinski.cookbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;


@NamedNativeQuery(
        name = "Recipe.retrieveRecipesByPreparationTimeShorterThan",
        query ="SELECT * FROM RECIPES WHERE PREPARATION_TIME <= :PREPARATION_TIME",
        resultClass = Recipe.class
)

@NamedNativeQuery(
        name = "Recipe.retrieveRecipesByCategoryId",
        query ="SELECT * FROM RECIPES WHERE RECIPE_CATEGORY_ID = :RECIPE_CATEGORY_ID",
        resultClass = Recipe.class
)

@NamedNativeQuery(
        name = "Recipe.retrieveRecipesByProduct",
        query ="SELECT R.* FROM RECIPES R "
        +"JOIN INGREDIENTS I ON R.RECIPE_ID=I.RECIPE_ID "
                +"JOIN PRODUCTS P ON P.PRODUCT_ID=I.PRODUCT_ID "
        +"WHERE P.PRODUCT_ID = :PRODUCT_ID",
        resultClass = Recipe.class
)

@NamedNativeQuery(
        name = "Recipe.retrieveRecipesByUser",
        query ="SELECT * FROM RECIPES "
                +"WHERE USER_ID = :USER_ID",
        resultClass = Recipe.class
)

@NamedNativeQuery(
        name = "Recipe.randomRecipe",
        query ="SELECT * FROM RECIPES "
        +"ORDER BY RAND() "
        +"LIMIT 1",
        resultClass = Recipe.class
)

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "RECIPES")
public class Recipe {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "RECIPE_ID", unique = true)
    private Long recipeId;

    @Column(name = "RECIPE_NAME")
    private String recipeName;

    @Column(name = "RECIPE_DETAILS")
    private String recipeDetails;

    @Column(name = "PREPARATION_TIME")
    private Long preparationTime;

    @ManyToOne
    @JoinColumn(name = "RECIPE_CATEGORY_ID")
    private RecipeCategory recipeCategory;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(
            mappedBy = "recipe",
            orphanRemoval = true,
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    private List<Ingredient> ingredients = new ArrayList<>();

    public Recipe(String recipeName, String recipeDetails, Long preparationTime, RecipeCategory recipeCategory, User user) {
        this.recipeName = recipeName;
        this.recipeDetails = recipeDetails;
        this.preparationTime = preparationTime;
        this.recipeCategory = recipeCategory;
        this.user = user;
    }
}
