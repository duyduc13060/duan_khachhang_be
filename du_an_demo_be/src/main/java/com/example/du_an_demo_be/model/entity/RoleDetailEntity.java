package com.example.du_an_demo_be.model.entity;


import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "roleDetails")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RoleDetailEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "role_id")
    private Long roleId;

    @Column(name = "function_id")
    private Long functionId;

    @Column(name = "action")
    private String action;

}
