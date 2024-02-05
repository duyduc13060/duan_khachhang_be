package com.example.du_an_demo_be.model.entity;


import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reviews")
@Data
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;

    @Column(name = "create_date")
    @CreationTimestamp
    private LocalDateTime createDate;

    private String createName;

    private Long messageId;

    @Column(name = "message_response",  columnDefinition = "LONGTEXT")
    private String messageResponse;

    @Column(name = "message_request",  columnDefinition = "LONGTEXT")
    private String messageRequest;

    private String  rating;

    private String  type;

    private String  modelName;
}
