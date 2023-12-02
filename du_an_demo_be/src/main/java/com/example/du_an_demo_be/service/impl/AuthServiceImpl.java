package com.example.du_an_demo_be.service.impl;

import com.example.du_an_demo_be.contants.RoleEnum;
import com.example.du_an_demo_be.model.dto.UserDto;
import com.example.du_an_demo_be.model.entity.UserEntity;
import com.example.du_an_demo_be.payload.request.UserRegister;
import com.example.du_an_demo_be.payload.response.DefaultResponse;
import com.example.du_an_demo_be.repository.UserRepository;
import com.example.du_an_demo_be.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;

    private final ModelMapper modelMapper;

    private final PasswordEncoder passwordEncoder;


    // todo: hàm này dùng để đăng ký tài khoản
    @Override
    public UserRegister registerAccount(UserRegister userRegister){

        // todo: tìm kiếm username có tồn tại trong database không -> nếu không return null cái này có thể trả ra message cụ thể cũng được
        this.validateRegister(userRegister);

        UserEntity userEntity = modelMapper.map(userRegister, UserEntity.class);
        userEntity.setUsername(userRegister.getUsername());
        userEntity.setFullname(userRegister.getFullname());
        userEntity.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        userEntity.setRole(0);

        userRegister = modelMapper.map(userRepository.save(userEntity),UserRegister.class);

        return userRegister;
    }


    private DefaultResponse<?> validateRegister(UserRegister userRegister){

        DefaultResponse<UserRegister> response = new DefaultResponse<>();

        if(Objects.isNull(userRegister.getUsername())){
            response.setSuccess(HttpStatus.NOT_FOUND.value());
            response.setMessage("username không được để trống");
            response.setData(null);
            return response;
        }

        if(Objects.isNull(userRegister.getFullname())){
            response.setSuccess(HttpStatus.NOT_FOUND.value());
            response.setMessage("fullname không được để trống");
            response.setData(null);
            return response;
        }

        if(Objects.isNull(userRegister.getPhone())){
            response.setSuccess(HttpStatus.NOT_FOUND.value());
            response.setMessage("phone không được để trống");
            response.setData(null);
            return response;
        }

        if(Objects.isNull(userRegister.getPassword())){
            response.setSuccess(HttpStatus.NOT_FOUND.value());
            response.setMessage("password không được để trống");
            response.setData(null);
            return response;
        }

        response.setSuccess(HttpStatus.OK.value());
        response.setMessage("Thành công");
        response.setData(userRegister);

        return response;
    }



}
