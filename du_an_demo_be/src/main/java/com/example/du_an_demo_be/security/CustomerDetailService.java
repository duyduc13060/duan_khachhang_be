package com.example.du_an_demo_be.security;

import com.example.du_an_demo_be.model.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class CustomerDetailService implements UserDetails {

    // todo:  đối tượng UserDetails để chứa toàn bộ thông tin về người dùng
    //       tạo ra một class mới giúp chuyển các thông tin của User thành UserDetails

    private Long id;
    private String fullname;
    private String username;
    private String password;
    private String phone;
    private String address;
    private Collection<? extends GrantedAuthority> authorities;

    public static CustomerDetailService build(UserEntity userEntity){
        List<GrantedAuthority> authorities =  Arrays.asList(new SimpleGrantedAuthority(userEntity.getRole().toString()));
        return CustomerDetailService.builder()
                .id(userEntity.getId())
                .fullname(userEntity.getFullname())
                .username(userEntity.getUsername())
                .password(userEntity.getPassword())
                .phone(userEntity.getPhone())
                .address(userEntity.getAddress())
                .authorities(authorities)
                .build();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }
}
