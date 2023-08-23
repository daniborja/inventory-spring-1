package com.alex.inventorymanagement.products.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_measurement")
public class ProductMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "measurement_type")
    private Double measurementType;

    @Column(name = "measurement_value")
    private Double measurementValue;


    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference("product_measurement_ref")
    private Product product;            // SII crea la FK aqui


}
