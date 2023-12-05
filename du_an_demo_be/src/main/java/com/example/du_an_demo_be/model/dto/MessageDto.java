package com.example.du_an_demo_be.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MessageDto {
    private Long id;
    private LocalDateTime createTime;
    private String contentRequest;
    private String contentResponse;
    private String model;
    private String completionsId;
    private String roleChoices;
}
