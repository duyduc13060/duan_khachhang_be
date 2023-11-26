package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.payload.response.DefaultResponse;

public interface UserService {
    //todo: Lấy ra danh sách user
    DefaultResponse<?> getListUser();
}
