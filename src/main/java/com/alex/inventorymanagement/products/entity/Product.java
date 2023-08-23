package com.alex.inventorymanagement.products.entity;

import com.alex.inventorymanagement.categories.entity.Category;
import com.alex.inventorymanagement.common.helpers.SkuGenerator;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)     // autoincrement
    private Long id;

    private String title;
    private String sku;
    private Boolean hasStock;
    private BigDecimal price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference("category_ref")
    private Category category;          // SIII crea aqui la FK

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("product_measurement_ref")
    private List<ProductMeasurement> productMeasurements;   // NOO crea FK aqui


    @PrePersist
    private void prePersist() {
        createdAt = LocalDateTime.now();
        if (!StringUtils.hasText(sku)) {
            sku = SkuGenerator.generateSku(category.getName());
        }
    }

    @PreUpdate
    private void preUpdate() {
        updatedAt = LocalDateTime.now();
    }

}
