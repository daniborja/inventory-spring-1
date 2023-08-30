package com.alex.inventorymanagement.products.repository;

import com.alex.inventorymanagement.products.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

}
