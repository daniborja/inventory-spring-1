package com.alex.inventorymanagement.stocks.entity;

import com.alex.inventorymanagement.products.entity.Product;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.Where;


@Data
@Entity
@Table(name = "stock")
@Where(clause = "product.deleted = false")
//@Where(clause = "deleted = false")
public class Stock {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long quantity;

    @Column(columnDefinition = "boolean default false")
    private boolean deleted = false;


    @ManyToOne  // no quiero traer los products si cargo stok x id
    @JoinColumn(name = "product_id")
    @JsonBackReference("product_stock_ref")
    private Product product;    // SII crea la FK

}
