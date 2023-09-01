package com.alex.inventorymanagement.products.service;

import com.alex.inventorymanagement.products.dto.CreateProductResponseDto;
import com.alex.inventorymanagement.products.dto.PaginatedProductsResponseDto;
import com.alex.inventorymanagement.products.dto.ProductRequestDto;
import com.alex.inventorymanagement.products.dto.ProductResponseDto;
import com.alex.inventorymanagement.products.entity.Product;
import org.springframework.data.domain.Pageable;


public interface ProductService {

    CreateProductResponseDto create(ProductRequestDto productDto);

    PaginatedProductsResponseDto findAll(Pageable pageable);

    ProductResponseDto findOne(Long id);
    ProductResponseDto update(Long id, ProductRequestDto productDto);

    void delete(Long id);

}
