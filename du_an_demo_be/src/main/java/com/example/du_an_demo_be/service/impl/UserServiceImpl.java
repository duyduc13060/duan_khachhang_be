package com.example.du_an_demo_be.service.impl;

import com.example.du_an_demo_be.exception.BadRequestException;
import com.example.du_an_demo_be.model.dto.UserDto;
import com.example.du_an_demo_be.model.entity.UserEntity;
import com.example.du_an_demo_be.payload.response.DefaultResponse;
import com.example.du_an_demo_be.repository.UserRepository;
import com.example.du_an_demo_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    //todo: Lấy ra danh sách user
    @Override
    public DefaultResponse<?> getListUser(){
        DefaultResponse<List<UserDto>> respose = new DefaultResponse<>();
        List<UserEntity> userEntityList = userRepository.findAll();

        List<UserDto> userDtoList = userEntityList.stream()
                .map(e -> modelMapper.map(e,UserDto.class))
                .collect(Collectors.toList());

        respose.setData(userDtoList);
        return respose;
    }


//    public DefaultResponse<?> searchUser(UserDto userDto){
//
//        return userDto;
//    }


    public DefaultResponse<?> updateUser(UserDto userDto){
        DefaultResponse<UserDto> respose = new DefaultResponse<>();

        UserEntity findById = this.userRepository.getById(userDto.getId());
        if(findById == null){
            throw new BadRequestException("Id not found" + userDto.getId());
        }

        findById = modelMapper.map(userDto, UserEntity.class);
        this.userRepository.save(findById);

        respose.setData(modelMapper.map(findById, UserDto.class));
        return respose;
    }

    public DefaultResponse<?> deleteUser(Long userId){
        DefaultResponse<UserDto> respose = new DefaultResponse<>();

        UserEntity findById = this.userRepository.getById(userId);
        if(findById == null){
            throw new BadRequestException("Id not dfound" + userId);
        }

        respose.setMessage("Xóa user thành công");
        return respose;
    }




}
