package com.example.du_an_demo_be.controller;


import com.example.du_an_demo_be.model.dto.ReviewDto;
import com.example.du_an_demo_be.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/QLRV")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @GetMapping("/detail")
    public ResponseEntity<?> getListReviewUser(
            @RequestParam("messageId") Long messageId
    ){
        return ResponseEntity.ok().body(reviewService.getListReviewUser(messageId));
    }

    @PostMapping("/create")
    public ResponseEntity<?> createReview(
            @RequestBody ReviewDto reviewDto
            ){
        return ResponseEntity.ok().body(reviewService.createReview(reviewDto));
    }

}
