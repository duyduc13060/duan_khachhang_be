package com.example.du_an_demo_be.model.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserDto {

    private Long id;
    private String fullname;
    private String username;
    private String password;
    private String phone;
    private LocalDateTime createDate;
    private String address;
    private String email;
    private Integer role;
    private Integer status;
    private String keySearch;
}
