package com.example.du_an_demo_be.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class NoteDto {
    private Long id;
    private String description;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String title;
    private String content;
    private Long userId;
}
