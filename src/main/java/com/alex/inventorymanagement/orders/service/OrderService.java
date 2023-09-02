package com.alex.inventorymanagement.orders.service;

import com.alex.inventorymanagement.orders.dto.CreateOrderRequestDto;
import com.alex.inventorymanagement.orders.dto.PaginatedOrdersResponseDto;
import com.alex.inventorymanagement.orders.entity.Order;
import com.alex.inventorymanagement.orders.entity.OrderResponseDto;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface OrderService {

    Order create(CreateOrderRequestDto orderRequestDto, String authUserEmail);

    PaginatedOrdersResponseDto findAll(Pageable pageable);

    OrderResponseDto findOne(Long id);
}
