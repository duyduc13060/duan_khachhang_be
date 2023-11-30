package com.example.du_an_demo_be.payload.response;

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
    private Collection<? extends GrantedAuthority> role;

    public LoginResponse(String token, String fullname,Collection<? extends GrantedAuthority> authorities, String username, Long id, String phone) {
        this.token = token;
        this.fullname = fullname;
        this.role  = authorities;
        this.username = username;
        this.id = id;
        this.phone = phone;
    }
}
