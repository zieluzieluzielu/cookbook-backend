package com.mzielinski.cookbook.exception;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException(final String message) {
        super(message);
    }
}
