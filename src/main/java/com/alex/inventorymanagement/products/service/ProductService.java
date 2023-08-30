package com.alex.inventorymanagement.products.service;

import com.alex.inventorymanagement.products.dto.CreateProductResponseDto;
import com.alex.inventorymanagement.products.dto.PaginatedProductsResponseDto;
import com.alex.inventorymanagement.products.dto.ProductRequestDto;
import org.springframework.data.domain.Pageable;


public interface ProductService {

    CreateProductResponseDto create(ProductRequestDto productDto);

    PaginatedProductsResponseDto findAll(Pageable pageable);

}
