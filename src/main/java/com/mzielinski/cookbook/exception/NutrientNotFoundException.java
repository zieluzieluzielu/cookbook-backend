package com.mzielinski.cookbook.exception;

public class NutrientNotFoundException extends RuntimeException {
    public NutrientNotFoundException (final String message){
        super(message);
    }
}
