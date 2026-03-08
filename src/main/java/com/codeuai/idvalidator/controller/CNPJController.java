package com.codeuai.idvalidator.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codeuai.idvalidator.model.DocumentRequest;
import com.codeuai.idvalidator.model.ErrorDto;
import com.codeuai.idvalidator.model.Response;
import com.codeuai.idvalidator.model.ValidationError;
import com.codeuai.idvalidator.service.CNPJService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cnpj")
@RequiredArgsConstructor
public class CNPJController {

    private final CNPJService cnpjService;

    @PostMapping
    public ResponseEntity<Response> isValid(@Valid @RequestBody DocumentRequest request) throws ValidationError {
        boolean isValid = cnpjService.isValid(request.document());
        return ResponseEntity.ok(new Response(isValid, new ErrorDto[0]));
    }

    @PostMapping("/batch")
    public ResponseEntity<Response> isValid(@Valid @RequestBody List<DocumentRequest> requests) throws ValidationError {
        var errors = new ArrayList<ErrorDto>();

        for (DocumentRequest request : requests) {
            try {
                cnpjService.isValid(request.document());
            } catch (ValidationError error) {
                errors.add(new ErrorDto(request.document(), error.getMessage()));
            }
        }
        if(errors.isEmpty()) {
            return ResponseEntity.ok(new Response(true, new ErrorDto[0]));
        }
        return ResponseEntity.badRequest().body(new Response(false, errors.toArray(new ErrorDto[0])));
    }
}
