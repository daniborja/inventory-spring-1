package com.alex.inventorymanagement.products.service;

import com.alex.inventorymanagement.categories.entity.Category;
import com.alex.inventorymanagement.categories.repository.CategoryRepository;
import com.alex.inventorymanagement.common.exceptions.ResourceNotFoundException;
import com.alex.inventorymanagement.products.dto.ProductRequestDto;
import com.alex.inventorymanagement.products.dto.CreateProductResponseDto;
import com.alex.inventorymanagement.products.entity.Product;
import com.alex.inventorymanagement.products.entity.ProductImage;
import com.alex.inventorymanagement.products.entity.ProductMeasurement;
import com.alex.inventorymanagement.products.repository.ProductImageRepository;
import com.alex.inventorymanagement.products.repository.ProductMeasurementRepository;
import com.alex.inventorymanagement.products.repository.ProductRepository;
import com.alex.inventorymanagement.stocks.entity.Stock;
import com.alex.inventorymanagement.stocks.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMeasurementRepository productMeasurementRepository;
    private final ProductImageRepository productImageRepository;
    private final StockRepository stockRepository;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public CreateProductResponseDto create(ProductRequestDto productDto) {
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", productDto.getCategoryId()));

        Product product = createProductFromDto(productDto, category);
        ProductMeasurement productMeasurement = createProductMeasurementFromDto(productDto, product);
        List<ProductImage> productImages = createProductImagesFromDto(productDto, product);

        product.setImages((List<ProductImage>) productImageRepository.saveAll(productImages));
        Stock stock = createStock(product, productDto.getQuantity(), productMeasurement);

        product = productRepository.save(product);
        stock = stockRepository.save(stock);

        return mapToCreateProductResponseDto(product, productMeasurement, stock);
    }


    private Product createProductFromDto(ProductRequestDto productDto, Category category) {
        // avoid using modelmapper for cache problems >> UPDATE (todo: check if it is for singleton | Inject)
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

    private ProductMeasurement createProductMeasurementFromDto(ProductRequestDto productDto, Product product) {
        ProductMeasurement productMeasurement = ProductMeasurement.builder()
                .product(product)
                .measurementType(productDto.getMeasurementType())
                .measurementValue(productDto.getMeasurementValue())
                .build();

        return productMeasurementRepository.save(productMeasurement);
    }

    private List<ProductImage> createProductImagesFromDto(ProductRequestDto productDto, Product product) {
        return productDto.getImages().stream()
                .map(imageUrl -> {
                    ProductImage productImage = new ProductImage();
                    productImage.setProduct(product);
                    productImage.setImageUrl(imageUrl);
                    return productImage;
                })
                .collect(Collectors.toList());
    }

    private Stock createStock(Product product, Long quantity, ProductMeasurement productMeasurement) {
        Stock stock = new Stock();
        stock.setProduct(product);
        stock.setQuantity(quantity);
        stock.setProductMeasurement(productMeasurement);
        return stock;
    }

    private CreateProductResponseDto mapToCreateProductResponseDto(Product product, ProductMeasurement productMeasurement, Stock stock) {
        CreateProductResponseDto productResponseDto = modelMapper.map(product, CreateProductResponseDto.class);
        productResponseDto.setProductMeasurement(modelMapper.map(productMeasurement, CreateProductResponseDto.ProductMeasurementDTO.class));
        productResponseDto.setStock(modelMapper.map(stock, CreateProductResponseDto.StockDTO.class));
        return productResponseDto;
    }
}
