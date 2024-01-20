package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.model.dto.UserDto;
import com.example.du_an_demo_be.payload.request.SearchDTO;
import com.example.du_an_demo_be.payload.response.DefaultResponse;
import com.example.du_an_demo_be.payload.response.SearchResponseDTO;
import com.example.du_an_demo_be.payload.response.ServiceResult;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    //todo: Lấy ra danh sách user
    DefaultResponse<?> getListUser();

    ServiceResult<Page<UserDto>> search(SearchDTO<UserDto> searchDTO);

    DefaultResponse<?> createUser(UserDto userDto);

    DefaultResponse<?> updateUser(UserDto userDto);

    DefaultResponse<?> deleteUser(Long userId);

    DefaultResponse<?> viewDetailUser(Long userId);
}
