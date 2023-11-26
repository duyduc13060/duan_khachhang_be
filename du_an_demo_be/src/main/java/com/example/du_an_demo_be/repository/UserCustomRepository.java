package com.example.du_an_demo_be.repository;

import com.example.du_an_demo_be.model.dto.UserDto;

import java.util.List;

public interface UserCustomRepository {
    List<UserDto> searchUser(UserDto userDto);
}
