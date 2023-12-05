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
    public DefaultResponse<UserRegister> registerAccount(UserRegister userRegister){

        DefaultResponse<UserRegister> response = this.validateRegister(userRegister);
        if(response.getSuccess() != 200){
            return response;
        }

        UserEntity userEntity = modelMapper.map(userRegister, UserEntity.class);
        userEntity.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        userEntity.setRoleId(2L);
        userEntity.setStatus(1);

        userRegister = modelMapper.map(userRepository.save(userEntity),UserRegister.class);

        response.setData(userRegister);

        return response;
    }


    private DefaultResponse<UserRegister> validateRegister(UserRegister userRegister){

        DefaultResponse<UserRegister> response = new DefaultResponse<>();

        Optional<UserEntity> findByUserName = userRepository.findByUsername(userRegister.getUsername());
        if(findByUserName.isPresent()){
            response.setSuccess(HttpStatus.NOT_FOUND.value());
            response.setMessage("username đã tồn tại bạn !!!");
            response.setData(null);
            return response;
        }

        if(Objects.isNull(userRegister.getUsername())){
            response.setSuccess(HttpStatus.NOT_FOUND.value());
            response.setMessage("username không được để trống");
            response.setData(null);
            return response;
        }

        if(userRegister.getUsername().length() > 15){
            response.setSuccess(HttpStatus.NOT_FOUND.value());
            response.setMessage("username không được vượt quá 15 ký tự");
            response.setData(null);
            return response;
        }


        if(Objects.isNull(userRegister.getFullname())){
            response.setSuccess(HttpStatus.NOT_FOUND.value());
            response.setMessage("fullname không được để trống");
            response.setData(null);
            return response;
        }

        if(userRegister.getFullname().length() > 100){
            response.setSuccess(HttpStatus.NOT_FOUND.value());
            response.setMessage("fullname không được vượt quá 100 ký tự");
            response.setData(null);
            return response;
        }

        if(Objects.isNull(userRegister.getEmail())){
            response.setSuccess(HttpStatus.NOT_FOUND.value());
            response.setMessage("Email không được để trống");
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
