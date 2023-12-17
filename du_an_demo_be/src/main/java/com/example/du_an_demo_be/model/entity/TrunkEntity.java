package com.example.du_an_demo_be.model.entity;


import lombok.Data;

import javax.persistence.*;

@Entity
@Table(name = "trucks")
@Data
public class TrunkEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    private String trunkContent;

}
