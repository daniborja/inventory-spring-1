package com.alex.inventorymanagement.orders.repository;

import com.alex.inventorymanagement.orders.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
