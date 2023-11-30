package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.model.dto.UserDto;
import com.example.du_an_demo_be.payload.response.DefaultResponse;

import java.util.List;

public interface UserService {
    //todo: Lấy ra danh sách user
    DefaultResponse<?> getListUser();

    DefaultResponse<List<UserDto>> search(UserDto userDto);
}
