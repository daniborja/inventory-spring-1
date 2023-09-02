package com.alex.inventorymanagement.orders.entity;

import com.alex.inventorymanagement.addresses.dto.AddressResponseDto;
import com.alex.inventorymanagement.categories.CategoryResponseDto;
import com.alex.inventorymanagement.products.dto.ProductMeasurementDto;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;


@Data
public class OrderResponseDto {
    private Long id;
    private Double tax;
    private Double subtotal;
    private Double totalAmount;
    private String transactionId;
    private List<OrderItemDto> orderItems;
    private UserDto user;
    private AddressResponseDto address;
    private boolean paid;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;


    @Data
    public static class OrderItemDto {
        private Long id;
        private int quantity;
        private Double priceAtPurchase;
        // private String productTitle;  // pilas con el nombre para q funcione este mapping
        private ProductResponseDto product;
    }

    @Data
    public static class UserDto {
        private Long id;
        private String firstname;
        private String lastname;
        private String fullName;
        private String email;
    }

    @Data
    public static class ProductResponseDto {
        private Long id;
        private String title;
        private String sku;
        private String description;
        private CategoryResponseDto category;
        private List<ProductMeasurementDto> productMeasurements;
    }

}
