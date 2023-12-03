package com.example.du_an_demo_be.controller;


import com.example.du_an_demo_be.model.dto.RolesDto;
import com.example.du_an_demo_be.model.dto.UserDto;
import com.example.du_an_demo_be.service.FunctionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class FunctionControler {

    private final FunctionService functionService;

    @GetMapping("/function/fake-list")
    public ResponseEntity<?> getNoteByUser(
    ){
        this.functionService.create();
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PostMapping("/role/search/functions/get-all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok(this.functionService.search());
    }

}
