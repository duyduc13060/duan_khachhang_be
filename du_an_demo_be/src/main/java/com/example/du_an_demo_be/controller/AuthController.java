package com.example.du_an_demo_be.controller;

import com.example.du_an_demo_be.payload.request.LoginRequest;
import com.example.du_an_demo_be.payload.request.UserRegister;
import com.example.du_an_demo_be.payload.response.DefaultResponse;
import com.example.du_an_demo_be.payload.response.LoginResponse;
import com.example.du_an_demo_be.payload.response.SampleResponse;
import com.example.du_an_demo_be.security.CustomerDetailService;
import com.example.du_an_demo_be.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;

    private final AuthService authService;


    //  todo: API này dùng để đăng ký tài khoản
    @PostMapping("/signup")
    public ResponseEntity<?> registerAccount(@RequestBody UserRegister userRegister){
        return ResponseEntity.ok(
                DefaultResponse.success(authService.registerAccount(userRegister)));
    }

    // todo: API này dùng để login
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomerDetailService customerDetailService = (CustomerDetailService) authentication.getPrincipal();

        return ResponseEntity.ok(
                SampleResponse
                        .builder()
                        .success(true)
                        .message("Login success")
                        .data(new LoginResponse(
                                customerDetailService.getFullname(),
                                customerDetailService.getAuthorities(),
                                customerDetailService.getUsername(),
                                customerDetailService.getId(),
                                customerDetailService.getPhone()
                        ))
                        .build());

    }


}
