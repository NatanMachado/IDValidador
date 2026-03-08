package com.codeuai.idvalidator.service;

import com.codeuai.idvalidator.model.ValidationError;

public interface DocumentValidator {
    boolean isValid(String document) throws ValidationError;
}
