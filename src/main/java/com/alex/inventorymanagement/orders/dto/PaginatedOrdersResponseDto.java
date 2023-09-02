package com.alex.inventorymanagement.orders.dto;

import com.alex.inventorymanagement.orders.entity.OrderResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaginatedOrdersResponseDto {

    private List<OrderResponseDto> orders;
    private int pageNumber;
    private int size;
    private long totalElements;
    private int totalPages;
    private boolean isLastOne;

}
