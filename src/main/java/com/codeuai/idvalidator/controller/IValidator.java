package com.codeuai.idvalidator.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import com.codeuai.idvalidator.model.DocumentRequest;
import com.codeuai.idvalidator.model.Response;
import com.codeuai.idvalidator.model.ValidationError;

import jakarta.validation.Valid;

public interface IValidator {
    // single validation endpoint
    public ResponseEntity<Response> isValid(@Valid @RequestBody DocumentRequest request) throws ValidationError;

    // batch validation endpoint
    public ResponseEntity<Response> isValid(@Valid @RequestBody List<DocumentRequest> requests) throws ValidationError;
}
