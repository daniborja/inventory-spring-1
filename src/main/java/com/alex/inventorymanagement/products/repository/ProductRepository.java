package com.alex.inventorymanagement.products.repository;

import com.alex.inventorymanagement.products.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {


//    Page<Product> findAll(Pageable pageable);

}
