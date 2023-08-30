package com.alex.inventorymanagement.products.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedProductsResponseDto {
    private List<ProductDto> products;
    private int pageNumber;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean isLastOne;


    @Data
    public static class ProductDto {
        private Long id;
        private String title;
        private String sku;
        private String description;
        private Double price;
        private String createdAt;
        private String updatedAt;
        private Boolean deleted;
        private List<ProductMeasurementDto> productMeasurements;
        private List<StockDto> stocks;
        private List<ProductImageDto> images;
    }

    @Data
    public static class ProductMeasurementDto {
        private Long id;
        private String measurementType;
        private String measurementValue;
        private Boolean deleted;
    }

    @Data
    public static class StockDto {
        private Long id;
        private Long quantity;
        private Boolean deleted;
    }

    @Data
    public static class ProductImageDto {
        private Long id;
        private String imageUrl;
    }

}
