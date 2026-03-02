package com.codeuai.idvalidator.service;

import com.codeuai.idvalidator.model.ValidationError;

public final class ValidatorService {

    private ValidatorService() {}

    public static void assertNotNull(String document, String message) throws ValidationError {
        if (document == null) {
            throw new ValidationError(document, message);
        }
        
    }

    public static void assertNotEmpty(String document, String message) throws ValidationError {
        if (document.trim().isEmpty()) {
            throw new ValidationError(document, message);
        }
    }

    public static void assertTrue(boolean condition, String document, String message) throws ValidationError {
        if (!condition) {
            throw new ValidationError(document, message);
        }

    }

}
