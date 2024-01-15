package com.example.du_an_demo_be.repository;

import com.example.du_an_demo_be.model.dto.UserDto;
import com.example.du_an_demo_be.payload.request.SearchDTO;

import java.util.List;

public interface UserCustomRepository {
    List<UserDto> searchUser(SearchDTO<UserDto> searchDTO);
}
