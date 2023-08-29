package com.alex.inventorymanagement.products.entity;

import com.alex.inventorymanagement.categories.entity.Category;
import com.alex.inventorymanagement.common.helpers.SkuGenerator;
import com.alex.inventorymanagement.stocks.entity.Stock;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;


@Data
@Entity
@Table(name = "product")
@Where(clause = "deleted = false") // filtra los deleted para todos los Select
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

    @Column(columnDefinition = "boolean default false")
    private boolean deleted = false;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    @JsonBackReference("category_ref")
    private Category category;          // SIII crea aqui la FK

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("product_measurement_ref")
    private List<ProductMeasurement> productMeasurements;   // NOO crea FK aqui

    @OneToMany(mappedBy = "product", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonManagedReference("product_stock_ref")
    private List<Stock> stocks;         // NOO crea la FK


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
