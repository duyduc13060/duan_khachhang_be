package com.example.du_an_demo_be.service.impl;

import com.example.du_an_demo_be.model.dto.PromptDto;
import com.example.du_an_demo_be.model.dto.PromptTypeDto;
import com.example.du_an_demo_be.payload.response.ServiceResult;
import com.example.du_an_demo_be.repository.PromptRepository;
import com.example.du_an_demo_be.repository.PromptTypeRepository;
import com.example.du_an_demo_be.service.PromptTypeService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PromptTypeServiceImpl implements PromptTypeService {


    private final PromptTypeRepository promptTypeRepository;
    private final ModelMapper modelMapper;

    @Override
    public ServiceResult<List<PromptTypeDto>> getAllPromptType(){
        List<PromptTypeDto> promptTypeDtoList = this.promptTypeRepository.findAll()
                .stream()
                .map(item-> modelMapper.map(item, PromptTypeDto.class))
                .collect(Collectors.toList());

        return new ServiceResult<>(promptTypeDtoList, HttpStatus.OK,"Lấy dữ liệu thành công");
    }


}
