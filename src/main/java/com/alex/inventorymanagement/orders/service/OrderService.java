package com.alex.inventorymanagement.orders.service;

import com.alex.inventorymanagement.orders.dto.CreateOrderRequestDto;
import com.alex.inventorymanagement.orders.entity.Order;
import com.alex.inventorymanagement.orders.entity.OrderResponseDto;

import java.util.List;


public interface OrderService {

    Order create(CreateOrderRequestDto orderRequestDto, String authUserEmail);

    List<Order> findAll();

    OrderResponseDto findOne(Long id);
}
