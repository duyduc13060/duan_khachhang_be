package com.example.du_an_demo_be.exception.handling;

import com.example.du_an_demo_be.exception.BadRequestException;
import com.example.du_an_demo_be.exception.NotFoundException;
import com.example.du_an_demo_be.exception.ProjectException;
import com.example.du_an_demo_be.exception.WrongFomatException;
import com.example.du_an_demo_be.payload.response.DefaultResponse;
import com.example.du_an_demo_be.payload.response.SampleResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ApiExceptionHandler{

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseStatus(value = HttpStatus.NOT_FOUND)
    public SampleResponse handlerNotFoundExcetion(NotFoundException ex) {
        return SampleResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();
    }

    @ExceptionHandler(value = BadRequestException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public SampleResponse handlerBadRequestException(BadRequestException ex) {
        return SampleResponse.builder()
                .success(false)
                .message(ex.getMessage())
                .data(null)
                .build();
    }

    @ExceptionHandler(value = WrongFomatException.class)
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public DefaultResponse<?> handlerWrongFormatException(WrongFomatException e) {
        return this.handleProjectException(e);
    }

    @ExceptionHandler(value = ProjectException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public DefaultResponse<?> handleProjectException(ProjectException e) {
        return DefaultResponse.error(e);
    }





}
