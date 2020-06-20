package com.mzielinski.cookbook.exception;

public class ProductGroupNotFoundException extends RuntimeException {
   public ProductGroupNotFoundException(final String message) {
       super(message);
    }
}
