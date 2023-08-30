package com.alex.inventorymanagement.products.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequestDto {

    @NotEmpty
    private String title;

    private String description;

    @NotEmpty
    private List<String> images;

    @NotNull
    private Long categoryId;

    @NotNull
    private BigDecimal price;

    @NotEmpty
    private String measurementType;

    @NotEmpty
    private String measurementValue;

    @NotNull
    private Long quantity;

}
