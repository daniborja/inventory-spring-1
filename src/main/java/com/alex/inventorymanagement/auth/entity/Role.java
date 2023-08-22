package com.alex.inventorymanagement.auth.entity;

import jakarta.persistence.*;
import lombok.Data;


@Data
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY )
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

}
