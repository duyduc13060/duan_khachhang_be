package com.example.du_an_demo_be.controller;

import com.example.du_an_demo_be.payload.response.DefaultResponse;
import com.example.du_an_demo_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    public ResponseEntity<DefaultResponse<?>> getListUser(){
        return ResponseEntity.ok().body(userService.getListUser());
    }





}
