package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.model.dto.ClickLikeDto;
import com.example.du_an_demo_be.payload.response.ServiceResult;
import org.springframework.data.domain.Page;

public interface ClickService {
    ServiceResult<ClickLikeDto> clickLike(ClickLikeDto clickLikeDto);

    ServiceResult<Page<ClickLikeDto>> listUsernameClickLike(ClickLikeDto clickLikeDto);

    ServiceResult<ClickLikeDto> viewDetail(ClickLikeDto clickLikeDto);

    ServiceResult<ClickLikeDto> countNumberLike(ClickLikeDto clickLikeDto);
}
