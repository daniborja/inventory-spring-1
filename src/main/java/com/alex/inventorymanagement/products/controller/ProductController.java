package com.alex.inventorymanagement.products.controller;

import com.alex.inventorymanagement.common.constants.PaginationConstants;
import com.alex.inventorymanagement.common.constants.RoleConstants;
import com.alex.inventorymanagement.products.dto.*;
import com.alex.inventorymanagement.products.repository.ProductRepository;
import com.alex.inventorymanagement.products.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
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
        // just for test this StoredProcedure (it should be in service)
        return ResponseEntity.ok(productRepository.getProductsWithDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDto> findOne(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findOne(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @Valid @RequestBody ProductUPDRequestDto productUPDRequestDto) {
        return ResponseEntity.ok(productService.update(id, productUPDRequestDto));
    }

    @DeleteMapping("/{id}")
    @Secured(RoleConstants.ADMIN)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.ok().build();
    }

}
