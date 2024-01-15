package com.example.du_an_demo_be.model.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "prompt_type")
@Data
public class PromptTypeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
}
