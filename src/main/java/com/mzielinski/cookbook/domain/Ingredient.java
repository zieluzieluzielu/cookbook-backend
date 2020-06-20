package com.mzielinski.cookbook.domain;

import com.mzielinski.cookbook.exception.IngredientNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@NamedNativeQuery(
        name = "Ingredient.retrieveIngredientByRecipe",
        query = "SELECT * FROM INGREDIENTS WHERE RECIPE_ID = :RECIPE_ID",
        resultClass = Ingredient.class
)

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity(name = "INGREDIENTS")
public class Ingredient {

    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "INGREDIENT_ID", unique = true)
    private Long ingredientPortionId;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "UNIT")
    private String unit;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    @ManyToOne
    @JoinColumn(name = "RECIPE_ID")
    private Recipe recipe;
    //@JoinColumn(name = "RECIPE_ID", unique = true)
    //@OneToOne(fetch = FetchType.EAGER)

    @Column(name = "MAIN_PRODUCT")
    boolean mainProduct;

    public Ingredient(BigDecimal amount, String unit, Product product, Recipe recipe, boolean mainProduct) {
        this.amount = amount;
        this.unit = unit;
        this.product = product;
        this.recipe = recipe;
        this.mainProduct = mainProduct;
    }


    public String ingredientToAnalyze() throws IngredientNotFoundException {

        return (this.amount + " " + this.unit + " " + this.product.getProductName());


    }

}
