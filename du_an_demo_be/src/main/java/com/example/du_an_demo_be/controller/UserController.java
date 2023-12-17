package com.example.du_an_demo_be.controller;

import com.example.du_an_demo_be.model.dto.UserDto;
import com.example.du_an_demo_be.payload.response.DefaultResponse;
import com.example.du_an_demo_be.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/QLU/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/list")
    public ResponseEntity<DefaultResponse<?>> getListUser(){
        return ResponseEntity.ok().body(userService.getListUser());
    }

    @PostMapping("/search")
    public ResponseEntity<DefaultResponse<?>> search(
            @RequestBody UserDto userDto
            ){
        return ResponseEntity.ok().body(userService.search(userDto));
    }

    @PostMapping("/create/user")
    public ResponseEntity<DefaultResponse<?>> createUser(
            @RequestBody UserDto userDto
    ){
        return ResponseEntity.ok().body(userService.createUser(userDto));
    }

    @PutMapping("/update/user")
    public ResponseEntity<DefaultResponse<?>> updateUser(
            @RequestBody UserDto userDto
    ){
        return ResponseEntity.ok().body(userService.updateUser(userDto));
    }

    @DeleteMapping("/delete/user/{id}")
    public ResponseEntity<DefaultResponse<?>> deleteUser(
            @PathVariable("id") Long idUser
    ){
        return ResponseEntity.ok().body(userService.deleteUser(idUser));
    }

    @GetMapping("/detail/user/{id}")
    public ResponseEntity<DefaultResponse<?>> viewDetailUser(
            @PathVariable("id") Long idUser
    ){
        return ResponseEntity.ok().body(userService.viewDetailUser(idUser));
    }


}
