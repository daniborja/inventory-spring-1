package com.alex.inventorymanagement.products.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class ProductUPDRequestDto {

    private String title;
    private String description;
    private BigDecimal price;
    private Long categoryId;
    private List<ImageDto> images;
    private List<ProductMeasurementDto> productMeasurements;
    private List<StockDto> stocks;

    @Data
    public static class ImageDto {
        private Long id;
        private String imageUrl;
    }

    @Data
    public static class ProductMeasurementDto {
        private Long id;
        private String measurementType;
        private String measurementValue;
    }

    @Data
    public static class StockDto {
        private Long quantityId;
        private int quantity;
    }

}
