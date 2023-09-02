package com.alex.inventorymanagement.orders.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class PayOrderRequestDto {

    @NotEmpty
    private String transactionId;

    @NotNull
    private Long orderId;

}
