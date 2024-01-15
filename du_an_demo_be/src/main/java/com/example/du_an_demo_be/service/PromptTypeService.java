package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.model.dto.PromptDto;
import com.example.du_an_demo_be.model.dto.PromptTypeDto;
import com.example.du_an_demo_be.payload.response.ServiceResult;

import java.util.List;

public interface PromptTypeService {
    ServiceResult<List<PromptTypeDto>> getAllPromptType();
}
