package com.mzielinski.cookbook.exception;

public class WineNotFoundException extends RuntimeException {
    public WineNotFoundException (final String message){
        super(message);
    }
}
