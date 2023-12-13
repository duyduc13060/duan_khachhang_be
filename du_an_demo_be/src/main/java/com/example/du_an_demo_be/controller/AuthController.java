package com.example.du_an_demo_be.controller;

import com.example.du_an_demo_be.exception.BadRequestException;
import com.example.du_an_demo_be.exception.NotFoundException;
import com.example.du_an_demo_be.model.entity.RoleEntity;
import com.example.du_an_demo_be.model.entity.UserEntity;
import com.example.du_an_demo_be.payload.request.LoginRequest;
import com.example.du_an_demo_be.payload.request.UserRegister;
import com.example.du_an_demo_be.payload.response.DefaultResponse;
import com.example.du_an_demo_be.payload.response.LoginResponse;
import com.example.du_an_demo_be.payload.response.SampleResponse;
import com.example.du_an_demo_be.repository.RoleRepository;
import com.example.du_an_demo_be.repository.UserRepository;
import com.example.du_an_demo_be.security.CustomerDetailService;
import com.example.du_an_demo_be.security.jwt.JwtProvider;
import com.example.du_an_demo_be.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final AuthService authService;
    private final JwtProvider jwtProvider;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Autowired
    public AuthController(
            AuthenticationManager authenticationManager,
            AuthService authService,
            JwtProvider jwtProvider,
            RoleRepository roleRepository,
            UserRepository userRepository
    ){
        this.authenticationManager = authenticationManager;
        this.authService = authService;
        this.jwtProvider = jwtProvider;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }


    //  todo: API này dùng để đăng ký tài khoản
    @PostMapping("/signup")
    public ResponseEntity<DefaultResponse<UserRegister>> registerAccount(@RequestBody UserRegister userRegister){
        return ResponseEntity.ok().body(authService.registerAccount(userRegister));
    }

    // todo: API này dùng để login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginRequest.getUsername(),
                            loginRequest.getPassword()
                    )
            );


            SecurityContextHolder.getContext().setAuthentication(authentication);
            // tạo token bằng thông tin username khi login
            String token = jwtProvider.createToken(authentication);
            CustomerDetailService customerDetailService = (CustomerDetailService) authentication.getPrincipal();


            UserEntity userEntity = userRepository.findByUsername(customerDetailService.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User name not found"));

            if(userEntity.getStatus() == 0){
                throw new BadRequestException("Tài khoản của bạn chưa được kích hoạt");
            }

            RoleEntity roleEntity = roleRepository.findById(userEntity.getRoleId())
                    .orElseThrow(() -> new UsernameNotFoundException("Role name not found"));


            return ResponseEntity.ok(
                    SampleResponse
                        .builder()
                        .success(true)
                        .message("Login success")
                        .data(new LoginResponse(
                                token,
                                customerDetailService.getFullname(),
                                roleEntity.getName(),
                                customerDetailService.getUsername(),
                                customerDetailService.getId(),
                                customerDetailService.getPhone()
                        ))
                        .build());
        } catch (AuthenticationException e) {
            throw e;
        }
    }


}
