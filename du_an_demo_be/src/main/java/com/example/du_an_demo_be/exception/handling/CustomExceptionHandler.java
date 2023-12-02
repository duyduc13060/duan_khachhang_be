package com.example.du_an_demo_be.exception.handling;


import com.example.du_an_demo_be.payload.response.ApiErrors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();
        for (FieldError error : ex.getBindingResult().getFieldErrors()){
            errors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        ApiErrors apiError =
                new ApiErrors(HttpStatus.BAD_REQUEST,"Validate filed !!!",errors);

        return handleExceptionInternal(ex,apiError, headers, apiError.getStatus(), request);
    }
}
