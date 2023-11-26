package com.example.du_an_demo_be.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;


@Data
public class LoginRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
