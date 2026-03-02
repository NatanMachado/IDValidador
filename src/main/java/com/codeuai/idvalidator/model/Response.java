package com.codeuai.idvalidator.model;

public record Response(Boolean valid, ErrorDto[] errors) {
    
}
