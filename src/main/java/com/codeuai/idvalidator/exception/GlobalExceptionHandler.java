package com.codeuai.idvalidator.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.codeuai.idvalidator.model.ErrorDto;
import com.codeuai.idvalidator.model.Response;
import com.codeuai.idvalidator.model.ValidationError;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationError.class)
    public ResponseEntity<Response> handleValidationError(ValidationError ex) {
        ErrorDto error = new ErrorDto(ex.getDocument(), ex.getMessage());
        Response payload = new Response(false, new ErrorDto[] { error });
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(payload);
    }

}
