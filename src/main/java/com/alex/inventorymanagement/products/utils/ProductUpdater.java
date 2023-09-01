package com.alex.inventorymanagement.products.utils;

import com.alex.inventorymanagement.categories.entity.Category;
import com.alex.inventorymanagement.products.dto.ProductUPDRequestDto;
import com.alex.inventorymanagement.products.entity.Product;
import com.alex.inventorymanagement.products.entity.ProductImage;
import com.alex.inventorymanagement.products.entity.ProductMeasurement;
import com.alex.inventorymanagement.stocks.entity.Stock;

import java.util.List;


public interface ProductUpdater {
    List<ProductMeasurement> updateProductMeasurements(
            Long productId,
            List<ProductUPDRequestDto.ProductMeasurementDto> measurementsDto,
            Product product
    );

    List<ProductImage> updateProductImages(
            Long productId,
            List<ProductUPDRequestDto.ImageDto> imagesDto,
            Product product
    );

    List<Stock> updateStocks(
            Long productId,
            List<ProductUPDRequestDto.StockDto> stocksDto,
            Product product
    );

    void updateProductFields(
            Product product,
            Category category,
            List<ProductMeasurement> measurements,
            List<ProductImage> images,
            List<Stock> stocks,
            ProductUPDRequestDto productDto
    );

}
