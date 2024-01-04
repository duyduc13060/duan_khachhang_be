package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.model.dto.ReviewDto;
import com.example.du_an_demo_be.payload.response.DefaultResponse;

import java.util.List;

public interface ReviewService {
    DefaultResponse<List<ReviewDto>> getListReviewUser(Long messageId);

    DefaultResponse<ReviewDto> createReview(ReviewDto reviewDto);
}
