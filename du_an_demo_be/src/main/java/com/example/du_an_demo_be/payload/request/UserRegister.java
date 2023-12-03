package com.example.du_an_demo_be.payload.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class UserRegister {

    private String username;

    private String fullname;

    private String phone;

    private String email;

    private String password;

}
