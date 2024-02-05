package com.example.du_an_demo_be.controller;


import com.example.du_an_demo_be.model.dto.ReviewDto;
import com.example.du_an_demo_be.model.dto.UserDto;
import com.example.du_an_demo_be.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/QLRV")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/get-list-reviews")
    public ResponseEntity<?> getListReviewUser(
            @RequestBody Map<String, String> requestBody
    ) {
        String username = requestBody.get("username");
        String userrole = requestBody.get("userrole");

        return ResponseEntity.ok().body(reviewService.getListReviewUser(username, userrole));
    }



    @PostMapping("/create")
    public ResponseEntity<?> createReview(
            @RequestBody ReviewDto reviewDto
            ){
        return ResponseEntity.ok().body(reviewService.createReview(reviewDto));
    }

    @DeleteMapping("/delete/review//{reviewId}")
    public ResponseEntity<?> deleteReview(
            @PathVariable("reviewId") Long reviewId
    ){
        this.reviewService.deleteReview(reviewId);
        return ResponseEntity.ok().body("success");
    }
}
