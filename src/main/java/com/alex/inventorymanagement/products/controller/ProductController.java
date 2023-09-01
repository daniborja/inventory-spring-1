package com.alex.inventorymanagement.products.controller;

import com.alex.inventorymanagement.common.constants.PaginationConstants;
import com.alex.inventorymanagement.products.dto.CreateProductResponseDto;
import com.alex.inventorymanagement.products.dto.PaginatedProductsResponseDto;
import com.alex.inventorymanagement.products.dto.ProductRequestDto;
import com.alex.inventorymanagement.products.repository.ProductRepository;
import com.alex.inventorymanagement.products.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<CreateProductResponseDto> create(@Valid @RequestBody ProductRequestDto productRequestDto) {

        return new ResponseEntity<>(productService.create(productRequestDto), HttpStatus.CREATED);

    }

    @GetMapping
    public ResponseEntity<PaginatedProductsResponseDto> findAll(
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_PAGE) int page,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_SIZE) int size,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_SORT_BY) String sortBy,
            @RequestParam(defaultValue = PaginationConstants.DEFAULT_SORT_DIR) String sortDir
    ) {
        Sort.Direction direction = Sort.Direction.fromString(sortDir);
        Sort sort = Sort.by(direction, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @GetMapping("/procedure")
    public ResponseEntity<?> findAllProcedure() {
        return ResponseEntity.ok(productRepository.getProductsWithDetails());
    }

}
