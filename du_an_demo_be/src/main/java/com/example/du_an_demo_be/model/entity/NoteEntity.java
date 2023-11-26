package com.example.du_an_demo_be.model.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
@Data
public class NoteEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(name = "create_date")
    @CreationTimestamp
    private LocalDateTime createDate;

    @Column(name = "update_date",columnDefinition = "DATETIME")
    @UpdateTimestamp
    private LocalDateTime updateDate;

    private String title;

    private String content;

    private String username;

}
