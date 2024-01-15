package com.example.du_an_demo_be.service.impl;

import com.example.du_an_demo_be.exception.NotFoundException;
import com.example.du_an_demo_be.model.dto.PromptDto;
import com.example.du_an_demo_be.model.entity.PromptEntity;
import com.example.du_an_demo_be.payload.response.ServiceResult;
import com.example.du_an_demo_be.repository.PromptRepository;
import com.example.du_an_demo_be.security.CustomerDetailService;
import com.example.du_an_demo_be.service.PromptService;
import com.example.du_an_demo_be.until.CurrentUserUtils;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PromptServiceImpl implements PromptService {

    private final PromptRepository promptRepository;
    private final ModelMapper modelMapper;


    @Override
    public ServiceResult<Page<PromptDto>> searchPrompt(PromptDto promptDto){
        Page<PromptDto> promptDtoPage = this.promptRepository.searchPrompt(
                promptDto.getPromptTypeId(),
                promptDto.getPromptName(),
                promptDto.getSearchDate(),
                PageRequest.of(promptDto.getPage(), promptDto.getPageSize())
                );
        return new ServiceResult<>(
                promptDtoPage,
                HttpStatus.OK,
                "Lấy ra danh sách thành công"
        );
    }

    @Override
    public ServiceResult<PromptDto> createPrompt(PromptDto promptDto){
        CustomerDetailService customer = CurrentUserUtils.getCurrentUserUtils();
        PromptEntity promptEntity = modelMapper.map(promptDto,PromptEntity.class);
        promptEntity.setCreateUsername(customer.getUsername());
        return new ServiceResult<>(
                modelMapper.map(this.promptRepository.save(promptEntity),PromptDto.class),
                HttpStatus.OK,
                "Thêm mới thành công"
        );
    }

    @Override
    public ServiceResult<PromptDto> updatePrompt(PromptDto promptDto){
        PromptEntity promptFindById = this.promptRepository.findById(promptDto.getId())
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Id prompt not found: " + promptDto.getId()));

        PromptEntity promptEntity = this.modelMapper.map(promptDto,PromptEntity.class);
        promptEntity.setId(promptFindById.getId());
        promptEntity.setCreateDate(promptFindById.getCreateDate());
        promptEntity.setCreateUsername(promptFindById.getCreateUsername());

        return new ServiceResult<>(
                modelMapper.map(this.promptRepository.save(promptEntity),PromptDto.class),
                HttpStatus.OK,
                "Cập nhật thành công"
        );
    }

    @Override
    public void deletePrompt(Long idPrompt){
        PromptEntity promptFindById = this.promptRepository.findById(idPrompt)
                .orElseThrow(() -> new NotFoundException(HttpStatus.NOT_FOUND.value(), "Id prompt not found: " + idPrompt));
        this.promptRepository.deleteById(promptFindById.getId());
    }


}
