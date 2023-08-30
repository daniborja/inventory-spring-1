package com.alex.inventorymanagement.products.utils;

import com.alex.inventorymanagement.categories.entity.Category;
import com.alex.inventorymanagement.products.dto.CreateProductResponseDto;
import com.alex.inventorymanagement.products.dto.ProductRequestDto;
import com.alex.inventorymanagement.products.entity.Product;
import com.alex.inventorymanagement.products.entity.ProductImage;
import com.alex.inventorymanagement.products.entity.ProductMeasurement;
import com.alex.inventorymanagement.stocks.entity.Stock;

import java.util.List;


public interface ProductCreator {

    Product createProduct(ProductRequestDto productDto, Category category);

    ProductMeasurement createProductMeasurement(ProductRequestDto productDto, Product product);

    List<ProductImage> createProductImages(ProductRequestDto productDto, Product product);

    Stock createStock(Product product, Long quantity, ProductMeasurement productMeasurement);

    CreateProductResponseDto mapToCreateProductResponseDto(Product product, ProductMeasurement productMeasurement, Stock stock);

}

