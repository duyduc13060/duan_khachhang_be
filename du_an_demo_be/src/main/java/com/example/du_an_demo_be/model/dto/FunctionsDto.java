package com.example.du_an_demo_be.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class FunctionsDto {

    private Long id;
    private String code;
    private String name;
    private List<ActionsDto> listActions;
    private List<Integer> listActionSelected;
    private boolean selected;
    private boolean selectedAll;
}
