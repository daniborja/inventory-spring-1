package com.alex.inventorymanagement.products.dto;

import lombok.Data;


@Data
public class ProductMeasurementDto {
    private Long id;
    private String measurementType;
    private String measurementValue;
//    private Boolean deleted;
}
