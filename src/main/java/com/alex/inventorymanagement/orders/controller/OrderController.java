package com.alex.inventorymanagement.orders.controller;

import com.alex.inventorymanagement.common.constants.PaginationConstants;
import com.alex.inventorymanagement.common.constants.RoleConstants;
import com.alex.inventorymanagement.orders.dto.CreateOrderRequestDto;
import com.alex.inventorymanagement.orders.dto.PayOrderRequestDto;
import com.alex.inventorymanagement.orders.entity.OrderResponseDto;
import com.alex.inventorymanagement.orders.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public ResponseEntity<?> findAll(
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_SIZE) int size,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_SORT_DIR) String sortDir
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(orderService.findAll(pageable));
    }

    @GetMapping("/{id}")
    @Secured(RoleConstants.ADMIN)
    public ResponseEntity<OrderResponseDto> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(orderService.findOne(id));
    }

    @PostMapping("/pay")
    public ResponseEntity<?> payOrder(@Valid @RequestBody PayOrderRequestDto payOrderRequestDto) {
        orderService.payOrder(payOrderRequestDto);

        return ResponseEntity.ok(null);
    }

}
