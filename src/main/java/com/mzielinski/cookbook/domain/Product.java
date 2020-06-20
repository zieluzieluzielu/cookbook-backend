package com.mzielinski.cookbook.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@NamedNativeQuery(
        name = "Product.retrieveProductByProductGroup",
        query ="SELECT * FROM PRODUCTS WHERE PRODUCT_GROUP_ID = :PRODUCT_GROUP_ID",
        resultClass = Product.class
)

@NamedNativeQuery(
        name = "Product.retrieveMainProductFromRecipe",
        query ="SELECT P.* FROM COOKBOOK.PRODUCTS P" +
                " JOIN COOKBOOK.INGREDIENTS I" +
                " ON P.PRODUCT_ID = I.PRODUCT_ID" +
                " JOIN COOKBOOK.RECIPES R" +
                " ON I.RECIPE_ID=R.RECIPE_ID" +
                " WHERE R.RECIPE_ID= :RECIPE_ID" +
                " AND I.MAIN_PRODUCT IS TRUE"+
                " LIMIT 1",
        resultClass = Product.class
)

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Entity(name = "PRODUCTS")
public class Product {
    @Id
    @NotNull
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PRODUCT_ID", unique = true)
    private Long productId;

    @Column(name = "PRODUCT_NAME")
    private String productName;

    @ManyToOne
    @JoinColumn(name = "PRODUCT_GROUP_ID")
    private ProductGroup productGroup;

    @OneToMany(
            mappedBy = "product",
            orphanRemoval = true,
            cascade = CascadeType.PERSIST,
            fetch = FetchType.EAGER
    )
    private List<Ingredient> ingredients = new ArrayList<>();

    public Product(String productName, ProductGroup productGroup) {
        this.productName = productName;
        this.productGroup = productGroup;
    }
}
