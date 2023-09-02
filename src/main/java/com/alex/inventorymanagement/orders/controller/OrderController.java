package com.alex.inventorymanagement.orders.controller;

import com.alex.inventorymanagement.orders.dto.CreateOrderRequestDto;
import com.alex.inventorymanagement.orders.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;


    @PostMapping
    public ResponseEntity<?> create(@Valid @RequestBody CreateOrderRequestDto createOrderRequestDto, Authentication authentication) {
        String authUserEmail = ((UserDetails) authentication.getPrincipal()).getUsername();

        return new ResponseEntity<>(orderService.create(createOrderRequestDto, authUserEmail), HttpStatus.CREATED);
    }

}
