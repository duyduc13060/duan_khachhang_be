package com.example.du_an_demo_be.model.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;
    private String fullname;
    private String username;
    private String password;
    private String phone;
    private LocalDateTime createDate;
    private String address;
    private String email;
    private Long roleId;
    private Integer status;
    private String keySearch;
    private String roleName;
    private Long total;

    public UserDto(Long id, String fullname, String username, String password, String phone, LocalDateTime createDate, String address, String email, Long roleId, Integer status, String keySearch, String roleName) {
        this.id = id;
        this.fullname = fullname;
        this.username = username;
        this.password = password;
        this.phone = phone;
        this.createDate = createDate;
        this.address = address;
        this.email = email;
        this.roleId = roleId;
        this.status = status;
        this.keySearch = keySearch;
        this.roleName = roleName;
    }
}
