package com.example.du_an_demo_be.model.dto;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Data
public class TaskDto {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime dueDate;
    private Integer priority;
    private Integer status;
    private LocalDateTime createDate;
    private LocalDateTime updateDate;
    private String username;
    private Long noteId;
}
