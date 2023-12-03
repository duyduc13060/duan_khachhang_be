package com.example.du_an_demo_be.model.dto;

import lombok.Data;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class RolesDto {
    private Long id;

    private String code;

    private String name;

    private Long status;

    private String description;

    private LocalDateTime createTime;

    private String createName;

    private LocalDateTime updateTime;

    private String updateName;

    private Long countUser;
    private String functionId;
    private String functionCode;
    private List<String> functionAction;
    private List<FunctionsDto> listFunctions;
    private String action;
    private String textSearch;
    private String order;
    private String orderName;
}
