package com.alex.inventorymanagement.products.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "product_measurement")
@Where(clause = "product.deleted = false")
//@Where(clause = "deleted = false")
public class ProductMeasurement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "measurement_type")
    private String measurementType;

    @Column(name = "measurement_value")
    private String measurementValue;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted = false;


    @ManyToOne
    @JoinColumn(name = "product_id")
    @JsonBackReference("product_measurement_ref")
    private Product product;            // SII crea la FK aqui

//    @OneToMany(mappedBy = "productMeasurement")
//    @JsonIgnore
//    private Set<Stock> stocks;

}
