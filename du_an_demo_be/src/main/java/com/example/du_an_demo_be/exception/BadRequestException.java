package com.example.du_an_demo_be.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BadRequestException extends RuntimeException{
    public BadRequestException(String message){
        super(message);
    }
}
