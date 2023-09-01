package com.alex.inventorymanagement.products.utils;

import com.alex.inventorymanagement.categories.entity.Category;
import com.alex.inventorymanagement.common.exceptions.ResourceNotFoundException;
import com.alex.inventorymanagement.products.dto.ProductUPDRequestDto;
import com.alex.inventorymanagement.products.entity.Product;
import com.alex.inventorymanagement.products.entity.ProductImage;
import com.alex.inventorymanagement.products.entity.ProductMeasurement;
import com.alex.inventorymanagement.products.repository.ProductImageRepository;
import com.alex.inventorymanagement.products.repository.ProductMeasurementRepository;
import com.alex.inventorymanagement.stocks.entity.Stock;
import com.alex.inventorymanagement.stocks.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Component
@RequiredArgsConstructor
public class ProductUpdaterImpl implements ProductUpdater {

    private final ProductMeasurementRepository productMeasurementRepository;
    private final ProductImageRepository productImageRepository;
    private final StockRepository stockRepository;

    private final ModelMapper modelMapper;


    @Override
    public List<ProductMeasurement> updateProductMeasurements(
            Long productId,
            List<ProductUPDRequestDto.ProductMeasurementDto> measurementsDto,
            Product product
    ) {
        List<ProductMeasurement> productMeasurementsDb = productMeasurementRepository.fetchAllByProductId(productId);
        List<ProductMeasurement> updatedProductMeasurements = new ArrayList<>();

        for (int i = 0; i < measurementsDto.size(); i++) {
            ProductUPDRequestDto.ProductMeasurementDto measurementDto = measurementsDto.get(i);
            ProductMeasurement dbMeasurement = productMeasurementsDb.get(i);

            if (!Objects.equals(measurementDto.getId(), dbMeasurement.getId())) {
                throw new ResourceNotFoundException("Product Measurement", "ID", measurementDto.getId());
            }

            ProductMeasurement productMeasurement = modelMapper.map(measurementDto, ProductMeasurement.class);
            productMeasurement.setProduct(product);
            updatedProductMeasurements.add(productMeasurement);
        }

        productMeasurementRepository.saveAll(updatedProductMeasurements);
        return updatedProductMeasurements;
    }

    @Override
    public List<ProductImage> updateProductImages(Long productId, List<ProductUPDRequestDto.ImageDto> imagesDto, Product product) {
        List<ProductImage> productImagesDb = productImageRepository.findAllByProductIdOrderByIdAsc(productId);
        List<ProductImage> updatedProductImages = new ArrayList<>();

        for (int i = 0; i < imagesDto.size(); i++) {
            ProductUPDRequestDto.ImageDto imageDto = imagesDto.get(i);
            ProductImage dbImage = productImagesDb.get(i);

            if (!Objects.equals(imageDto.getId(), dbImage.getId())) {
                throw new ResourceNotFoundException("Product Image", "ID", imageDto.getId());
            }

            ProductImage productImage = modelMapper.map(imageDto, ProductImage.class);
            productImage.setProduct(product);
            updatedProductImages.add(productImage);
        }

        productImageRepository.saveAll(updatedProductImages);
        return updatedProductImages;
    }

    @Override
    public List<Stock> updateStocks(Long productId, List<ProductUPDRequestDto.StockDto> stocksDto, Product product) {
        List<Stock> stocksDb = stockRepository.findAllByProductIdOrderByIdAsc(productId);
        List<Stock> updatedStocks = new ArrayList<>();

        for (int i = 0; i < stocksDto.size(); i++) {
            ProductUPDRequestDto.StockDto stockDto = stocksDto.get(i);
            Stock dbStock = stocksDb.get(i);

            if (!Objects.equals(stockDto.getQuantityId(), dbStock.getId())) {
                throw new ResourceNotFoundException("Stock", "ID", stockDto.getQuantityId());
            }

            Stock stock = modelMapper.map(stockDto, Stock.class);
            stock.setProduct(product);
            stock.setProductMeasurement(dbStock.getProductMeasurement());
            updatedStocks.add(stock);
        }

        stockRepository.saveAll(updatedStocks);
        return updatedStocks;
    }


    // do not use ModelMapper to avoid cache/fk/upd errors in complex entities like this
    @Override
    public void updateProductFields(
            Product product, Category category, List<ProductMeasurement> measurements,
            List<ProductImage> images, List<Stock> stocks, ProductUPDRequestDto productDto
    ) {
        product.setCategory(category);
        product.setImages(images);
        product.setProductMeasurements(measurements);
        product.setStocks(stocks);
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());
    }

}
