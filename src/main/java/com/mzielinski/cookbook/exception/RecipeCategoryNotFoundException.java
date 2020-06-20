package com.mzielinski.cookbook.exception;

public class RecipeCategoryNotFoundException extends RuntimeException {
    public RecipeCategoryNotFoundException (final String message) {
        super(message);
    }
}
