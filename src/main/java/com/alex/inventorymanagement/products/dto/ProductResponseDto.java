package com.alex.inventorymanagement.products.dto;

import com.alex.inventorymanagement.categories.CategoryResponseDto;
import com.alex.inventorymanagement.stocks.dto.StockDto;
import lombok.Data;

import java.util.List;


@Data
public class ProductResponseDto {
    private Long id;
    private String title;
    private String sku;
    private String description;
    private Double price;
    private String createdAt;
    private String updatedAt;
    //    private Boolean deleted;
    private CategoryResponseDto category;
    private List<ProductMeasurementDto> productMeasurements;
    private List<StockDto> stocks;
    private List<ProductImageDto> images;
}
