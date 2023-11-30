package com.example.du_an_demo_be.payload.response;

import com.example.du_an_demo_be.contants.ResponseStatusContants;
import com.example.du_an_demo_be.exception.ProjectException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DefaultResponse<T>{

    private Integer success;
    private String message;
    private T data;

    public static <T> DefaultResponse <T> success(T body){
        DefaultResponse<T> response  = new DefaultResponse<>();
        response.setSuccess(ResponseStatusContants.SUCCESS.getCode());
        response.setMessage(ResponseStatusContants.SUCCESS.getMessage());
        response.setData(body);

        return response;
    }

    public static <T> DefaultResponse <T> success(String message){
        DefaultResponse<T> response  = new DefaultResponse<>();
        response.setSuccess(ResponseStatusContants.SUCCESS.getCode());
        response.setMessage(message);

        return response;
    }

    public static <T> DefaultResponse <T> success(String message, T body){
        DefaultResponse<T> response = new DefaultResponse<>();
        response.success(ResponseStatusContants.SUCCESS.getCode());
        response.setMessage(message);
        response.setData(body);
        return response;
    }



    public static <T> DefaultResponse <T> error(ProjectException e){
        DefaultResponse<T> response = new DefaultResponse<>();
        response.setSuccess(e.getCode());
        response.setMessage(e.getMessage());
        return response;
    }

    public static <T> DefaultResponse <T> error(String message, Integer success, T body){
        DefaultResponse<T> response = new DefaultResponse<>();
        response.setSuccess(success);
        response.setMessage(message);
        response.setData(body);
        return response;
    }



}
