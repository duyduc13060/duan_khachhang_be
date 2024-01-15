package com.example.du_an_demo_be.model.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "click_likes")
@Data
public class ClickLikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "create_date")
    @CreationTimestamp
    private LocalDateTime createDate;

    private String usernameLike;

    private Long promptId;



}
