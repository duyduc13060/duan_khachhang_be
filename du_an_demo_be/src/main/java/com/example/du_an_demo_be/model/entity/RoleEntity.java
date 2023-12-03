package com.example.du_an_demo_be.model.entity;


import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "roles")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "code")
    private String code;

    @Column(name = "description")
    private String description;

    @Column(name = "create_Time")
    @CreationTimestamp
    private LocalDateTime createTime;

    @Column(name = "update_Time")
    @UpdateTimestamp
    private LocalDateTime updateTime;

    private Integer status;

    public RoleEntity(Long id, String code, String name, Integer status, String description){
        this.id = id;
        this.code = code;
        this.name = name;
        this.status = status;
        this.description = description;
    }



}
