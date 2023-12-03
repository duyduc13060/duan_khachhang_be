package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.payload.request.UserRegister;
import com.example.du_an_demo_be.payload.response.DefaultResponse;

public interface AuthService {
    DefaultResponse<UserRegister> registerAccount(UserRegister userRegister);
}
