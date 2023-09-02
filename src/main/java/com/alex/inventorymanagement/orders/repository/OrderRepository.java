package com.alex.inventorymanagement.orders.repository;

import com.alex.inventorymanagement.orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderRepository extends JpaRepository<Order, Long> {

}
