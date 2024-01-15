package com.example.du_an_demo_be.controller;


import com.example.du_an_demo_be.model.dto.ClickLikeDto;
import com.example.du_an_demo_be.service.ClickService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/QLCL")
@RequiredArgsConstructor
public class ClickLikeController {

    private final ClickService clickService;

    @PostMapping("/click")
    public ResponseEntity<?> clickLike(
            @RequestBody ClickLikeDto clickLikeDto
            ){
        return ResponseEntity.ok().body(this.clickService.clickLike(clickLikeDto));
    }

    @PostMapping("/list-like")
    public ResponseEntity<?> listUsernameClickLike(
            @RequestBody ClickLikeDto clickLikeDto
    ){
        return ResponseEntity.ok().body(this.clickService.listUsernameClickLike(clickLikeDto));
    }


    @PostMapping("/view-detail-like")
    public ResponseEntity<?> viewDetailClick(
            @RequestBody ClickLikeDto clickLikeDto
    ){
        return ResponseEntity.ok().body(this.clickService.viewDetail(clickLikeDto));
    }


    @PostMapping("/count-like")
    public ResponseEntity<?> countNumberLike(
            @RequestBody ClickLikeDto clickLikeDto
    ){
        return ResponseEntity.ok().body(this.clickService.countNumberLike(clickLikeDto));
    }


}
