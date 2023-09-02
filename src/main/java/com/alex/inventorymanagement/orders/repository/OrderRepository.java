package com.alex.inventorymanagement.orders.repository;

import com.alex.inventorymanagement.orders.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;


public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("SELECT o FROM Order o LEFT JOIN FETCH OrderItem oi ON oi.order.id = o.id " +
            "LEFT JOIN FETCH Usuario u ON u.id = o.user.id LEFT JOIN FETCH Address a ON a.id = o.address.id"
    )
    Optional<Order> fetchOneById(Long id);

}
