package com.example.du_an_demo_be.service;

import com.example.du_an_demo_be.model.dto.FunctionsDto;

import java.util.List;

public interface FunctionService {
    void create();

    List<FunctionsDto> search();
}
