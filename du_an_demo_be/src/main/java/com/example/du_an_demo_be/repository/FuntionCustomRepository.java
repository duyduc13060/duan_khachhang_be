package com.example.du_an_demo_be.repository;

import com.example.du_an_demo_be.model.dto.FunctionsDto;

import java.util.List;

public interface FuntionCustomRepository {
    List<FunctionsDto> search();
}
