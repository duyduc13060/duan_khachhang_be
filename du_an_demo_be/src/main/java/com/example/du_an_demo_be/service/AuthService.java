package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.payload.request.UserRegister;

public interface AuthService {
    UserRegister registerAccount(UserRegister userRegister);
}
