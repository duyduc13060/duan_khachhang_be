package com.example.du_an_demo_be.payload.response;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.List;

@Getter
public class ApiErrors {
    private HttpStatus status;
    private String message;
    private List<String> errors;

    public ApiErrors(HttpStatus status, String message, List<String> errors){
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }

//    public ApiErrors(HttpStatus status , String message, String errors){
//        super();
//        this.status = status;
//        this.message = message;
//        errors = Arrays.asList(errors);
//    }




}
