package com.alex.inventorymanagement.products.utils;

import com.alex.inventorymanagement.categories.entity.Category;
import com.alex.inventorymanagement.products.dto.CreateProductResponseDto;
import com.alex.inventorymanagement.products.dto.ProductRequestDto;
import com.alex.inventorymanagement.products.entity.Product;
import com.alex.inventorymanagement.products.entity.ProductImage;
import com.alex.inventorymanagement.products.entity.ProductMeasurement;
import com.alex.inventorymanagement.stocks.entity.Stock;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class ProductCreatorImpl implements ProductCreator {

    private final ModelMapper modelMapper;


    @Override
    public Product createProduct(ProductRequestDto productDto, Category category) {
        return Product.builder()
                .title(productDto.getTitle())
                .description(productDto.getDescription())
                .category(category)
                .price(productDto.getPrice())
                .images(Collections.emptyList())
                .productMeasurements(Collections.emptyList())
                .deleted(false)
                .build();
    }

    @Override
    public ProductMeasurement createProductMeasurement(ProductRequestDto productDto, Product product) {
        return ProductMeasurement.builder()
                .product(product)
                .measurementType(productDto.getMeasurementType())
                .measurementValue(productDto.getMeasurementValue())
                .build();
    }

    @Override
    public List<ProductImage> createProductImages(ProductRequestDto productDto, Product product) {
        return productDto.getImages().stream()
                .map(imageUrl -> {
                    ProductImage productImage = new ProductImage();
                    productImage.setProduct(product);
                    productImage.setImageUrl(imageUrl);
                    return productImage;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Stock createStock(Product product, Long quantity, ProductMeasurement productMeasurement) {
        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setQuantity(quantity);
        stock.setProductMeasurement(productMeasurement);

        return stock;
    }

    @Override
    public CreateProductResponseDto mapToCreateProductResponseDto(Product product, ProductMeasurement productMeasurement, Stock stock) {

        CreateProductResponseDto productResponseDto = modelMapper.map(product, CreateProductResponseDto.class);
        productResponseDto.setProductMeasurement(modelMapper.map(productMeasurement, CreateProductResponseDto.ProductMeasurementDTO.class));
        productResponseDto.setStock(modelMapper.map(stock, CreateProductResponseDto.StockDTO.class));

        return productResponseDto;
    }

}