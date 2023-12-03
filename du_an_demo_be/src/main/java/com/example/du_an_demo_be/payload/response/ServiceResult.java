package com.example.du_an_demo_be.payload.response;

import org.springframework.http.HttpStatus;

import java.io.Serializable;

public class ServiceResult<T> implements Serializable {
    private HttpStatus status;
    private String message;
    private T data;
    private String code;
    private String codeCallApi;

    public ServiceResult() {

    }

    public ServiceResult(T data, HttpStatus status, String message) {
        this.data = data;
        this.status = status;
        this.message = message;
    }

    public ServiceResult(HttpStatus status, String message, T data, String code) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.code = code;
    }

    public ServiceResult(HttpStatus status, String message, T data, String code, String codeCallApi) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.code = code;
        this.codeCallApi = codeCallApi;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCodeCallApi() {
        return codeCallApi;
    }

    public void setCodeCallApi(String codeCallApi) {
        this.codeCallApi = codeCallApi;
    }
}
