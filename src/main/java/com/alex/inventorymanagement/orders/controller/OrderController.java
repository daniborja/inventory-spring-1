package com.alex.inventorymanagement.orders.controller;

import com.alex.inventorymanagement.common.constants.RoleConstants;
import com.alex.inventorymanagement.orders.dto.CreateOrderRequestDto;
import com.alex.inventorymanagement.orders.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


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

    @GetMapping
    @Secured(RoleConstants.ADMIN)
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(orderService.findAll());
    }

    @GetMapping("/{id}")
    @Secured(RoleConstants.ADMIN)
    public ResponseEntity<?> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOne(id));
    }

}
