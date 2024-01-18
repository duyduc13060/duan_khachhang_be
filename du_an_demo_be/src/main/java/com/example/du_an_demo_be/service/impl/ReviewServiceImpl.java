package com.example.du_an_demo_be.service.impl;

import com.example.du_an_demo_be.exception.BadRequestException;
import com.example.du_an_demo_be.model.dto.ReviewDto;
import com.example.du_an_demo_be.model.entity.MessageEntity;
import com.example.du_an_demo_be.model.entity.ReviewEntity;
import com.example.du_an_demo_be.payload.response.DefaultResponse;
import com.example.du_an_demo_be.repository.MessageRepository;
import com.example.du_an_demo_be.repository.ReviewRepository;
import com.example.du_an_demo_be.security.CustomerDetailService;
import com.example.du_an_demo_be.service.ReviewService;
import com.example.du_an_demo_be.until.CurrentUserUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final ModelMapper modelMapper;
    private final MessageRepository messageRepository;
    private final String ROLE_ADMIN = "ADMIN";

    @Override
    public DefaultResponse<List<ReviewDto>> getListReviewUser(String userName, String userRole) {
//        CustomerDetailService customerDetailService = CurrentUserUtils.getCurrentUserUtils();

        List<ReviewDto> reviewDtoList;
        if (userRole.equals(ROLE_ADMIN)) {
            reviewDtoList = reviewRepository.findAllByRoleAdmin()
                    .stream()
                    .map(item -> modelMapper.map(item, ReviewDto.class))
                    .collect(Collectors.toList());
        } else {
            reviewDtoList = reviewRepository.findAllByUser(userName)
                    .stream()
                    .map(item -> modelMapper.map(item, ReviewDto.class))
                    .collect(Collectors.toList());
        }
        return DefaultResponse.success("success", reviewDtoList);
    }



    @Override
    public DefaultResponse<ReviewDto> createReview(ReviewDto reviewDto){
        CustomerDetailService customerDetailService = CurrentUserUtils.getCurrentUserUtils();

        Optional<MessageEntity> messageEntity = messageRepository.findById(reviewDto.getMessageId());
        if(!messageEntity.isPresent())
            throw new BadRequestException("Message id not found");

        ReviewEntity reviewEntity = modelMapper.map(reviewDto,ReviewEntity.class);
        reviewEntity.setCreateName(customerDetailService.getUsername());
        reviewEntity.setMessageId(messageEntity.get().getId());
        reviewEntity.setMessageRequest(messageEntity.get().getContentRequest());
        reviewEntity.setMessageResponse(messageEntity.get().getContentResponse());
        reviewEntity.setRating(reviewDto.getRating());
        reviewEntity.setType(reviewDto.getType());

        return DefaultResponse.success("success",modelMapper.map(
                reviewRepository.save(reviewEntity),ReviewDto.class)
        );
    }


}
