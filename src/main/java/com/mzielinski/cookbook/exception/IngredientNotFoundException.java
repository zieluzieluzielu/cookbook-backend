package com.mzielinski.cookbook.exception;

public class IngredientNotFoundException extends RuntimeException {
    public IngredientNotFoundException(final String message) {
        super(message);
    }
}
