package com.alex.inventorymanagement.products.dto;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class CreateProductResponseDto {
    private Long id;
    private String title;
    private String sku;
    private String description;
    private double price;
    private LocalDateTime createdAt;
    private List<ImageDTO> images;

    // // flat these 'cause we're going to create  1 by 1
    private ProductMeasurementDTO productMeasurement;
    private StockDTO stock;


    @Data
    public static class ProductMeasurementDTO {
        private Long id;
        private String measurementType;
        private String measurementValue;
    }

    @Data
    public static class StockDTO {
        private Long id;
        private Long quantity;
        // // IMPORTANTE: debe coincicir el nombre como esta en la entity para q se haga el mapeo
        // private ProductMeasurementDTO productMeasurement;
    }

    @Data
    public static class ImageDTO {
        private Long id;
        private String imageUrl;
    }

}
