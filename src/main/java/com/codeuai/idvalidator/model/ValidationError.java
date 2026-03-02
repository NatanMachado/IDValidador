package com.codeuai.idvalidator.model;

import lombok.Getter;

@Getter
public class ValidationError extends Throwable {
    private final String document;
    private final String message;

    public ValidationError(String document, String message) {
        this.document = document;
        this.message = message;
    }
}