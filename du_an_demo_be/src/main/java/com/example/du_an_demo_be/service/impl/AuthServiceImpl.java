package com.example.du_an_demo_be.service.impl;

import com.example.du_an_demo_be.contants.RoleEnum;
import com.example.du_an_demo_be.model.entity.UserEntity;
import com.example.du_an_demo_be.payload.request.UserRegister;
import com.example.du_an_demo_be.repository.UserRepository;
import com.example.du_an_demo_be.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
        Optional<UserEntity> findByUsername = userRepository.findByUsername(userRegister.getUsername());
        if (findByUsername.isPresent()) return null;

        UserEntity userEntity = modelMapper.map(userRegister, UserEntity.class);
        userEntity.setUsername(userRegister.getUsername());
        userEntity.setFullname(userRegister.getFullname());
        userEntity.setPassword(passwordEncoder.encode(userRegister.getPassword()));
        userEntity.setRole(RoleEnum.CUSTOMER);

        userRegister = modelMapper.map(userRepository.save(userEntity),UserRegister.class);

        return userRegister;
    }


}
