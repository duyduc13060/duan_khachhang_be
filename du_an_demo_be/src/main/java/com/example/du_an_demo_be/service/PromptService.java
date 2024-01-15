package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.model.dto.PromptDto;
import com.example.du_an_demo_be.payload.response.ServiceResult;
import org.springframework.data.domain.Page;

public interface PromptService {
    ServiceResult<Page<PromptDto>> searchPrompt(PromptDto promptDto);

    ServiceResult<PromptDto> createPrompt(PromptDto promptDto);

    ServiceResult<PromptDto> updatePrompt(PromptDto promptDto);

    void deletePrompt(Long idPrompt);
}
