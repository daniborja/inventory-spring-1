package com.alex.inventorymanagement.orders.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;


@Data
public class CreateOrderRequestDto {

    @NotNull
    private Long shippingAddressId;

    @Valid
    @NotEmpty
    private List<OrderItemDto> items;


    @Data
    public static class OrderItemDto {

        @NotNull
        private BigDecimal price;

        @NotNull
        private Integer quantity;

        @NotNull
        private Long productId;

        @NotNull
        private Long productMeasurementId;
    }

}





