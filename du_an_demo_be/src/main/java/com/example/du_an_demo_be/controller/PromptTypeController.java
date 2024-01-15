package com.example.du_an_demo_be.controller;

import com.example.du_an_demo_be.service.PromptService;
import com.example.du_an_demo_be.service.PromptTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/QLPTT")
@RequiredArgsConstructor
public class PromptTypeController {

    private final PromptTypeService promptTypeService;

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll(){
        return ResponseEntity.ok().body(this.promptTypeService.getAllPromptType());
    }


}
