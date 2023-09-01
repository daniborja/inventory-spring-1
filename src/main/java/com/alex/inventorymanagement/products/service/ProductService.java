package com.alex.inventorymanagement.products.service;

import com.alex.inventorymanagement.products.dto.*;
import org.springframework.data.domain.Pageable;


public interface ProductService {

    CreateProductResponseDto create(ProductRequestDto productDto);

    PaginatedProductsResponseDto findAll(Pageable pageable);

    ProductResponseDto findOne(Long id);

    ProductResponseDto update(Long id, ProductUPDRequestDto productDto);

    void delete(Long id);

}
