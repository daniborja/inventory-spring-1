package com.alex.inventorymanagement.users.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;


@Data   // provee todos los getter/setter, toString, @RequiredArgsConstructor
@Entity
@Table(name = "_user")   // postgresql tiene 1 tabla llamada users, asi q hay q renombrala
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // autoincrement
    private Long id;

    private String firstname;
    private String lastname;

    @Column(name = "fullname")
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    private String password;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
        fullName = firstname.concat(" ").concat(lastname);
    }

    @PreUpdate
    private void preUpdate() {
        createdAt = LocalDateTime.now();
        fullName = firstname.concat(" ").concat(lastname);
    }

}
