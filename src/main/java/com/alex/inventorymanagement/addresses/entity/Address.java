package com.alex.inventorymanagement.addresses.entity;

import com.alex.inventorymanagement.users.entity.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;


@Data
@Entity
@Table(name = "address")
@Where(clause = "deleted = false") // filtra los deleted para todos los Select
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private String city;

    private String province;
    private String zipCode;
    private String phone;
    protected boolean deleted;

    @Column(nullable = false)
    private String mainStreet;
    private String secondaryStreet;


    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore
    private Usuario user;

}
