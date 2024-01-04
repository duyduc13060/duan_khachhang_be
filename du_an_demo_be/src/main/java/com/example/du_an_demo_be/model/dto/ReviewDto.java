package com.example.du_an_demo_be.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class ReviewDto {
    private Long id;
    private String content;
    private LocalDateTime createDate;
    private String createName;
    private Long messageId;
}
