package com.example.du_an_demo_be.payload.response;

import com.example.du_an_demo_be.model.dto.RolesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String token;
    private Long id;
    private String fullname;
    private String username;
    private String phone;
    private String role;
    private RolesDto roleDto;

    public LoginResponse(String token,
                         String fullname,
                         String role,
                         String username,
                         Long id,
                         String phone,
                         RolesDto rolesDto) {
        this.token = token;
        this.fullname = fullname;
        this.role  = role;
        this.username = username;
        this.id = id;
        this.phone = phone;
        this.roleDto = rolesDto;
    }
}
