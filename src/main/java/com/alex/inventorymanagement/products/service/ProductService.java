package com.alex.inventorymanagement.products.service;

import com.alex.inventorymanagement.products.dto.ProductRequestDto;
import com.alex.inventorymanagement.products.dto.CreateProductResponseDto;


public interface ProductService {

    CreateProductResponseDto create(ProductRequestDto productDto);

}
