package com.alex.inventorymanagement.products.service;

import com.alex.inventorymanagement.categories.entity.Category;
import com.alex.inventorymanagement.categories.repository.CategoryRepository;
import com.alex.inventorymanagement.common.exceptions.ResourceNotFoundException;
import com.alex.inventorymanagement.products.dto.*;
import com.alex.inventorymanagement.products.entity.Product;
import com.alex.inventorymanagement.products.entity.ProductImage;
import com.alex.inventorymanagement.products.entity.ProductMeasurement;
import com.alex.inventorymanagement.products.repository.ProductImageRepository;
import com.alex.inventorymanagement.products.repository.ProductMeasurementRepository;
import com.alex.inventorymanagement.products.repository.ProductRepository;
import com.alex.inventorymanagement.products.utils.ProductCreator;
import com.alex.inventorymanagement.products.utils.ProductUpdater;
import com.alex.inventorymanagement.stocks.entity.Stock;
import com.alex.inventorymanagement.stocks.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMeasurementRepository productMeasurementRepository;
    private final ProductImageRepository productImageRepository;
    private final StockRepository stockRepository;
    private final ProductCreator productCreator;
    private final ProductUpdater productUpdater;

    private final ModelMapper modelMapper;


    /*
    @Override
    @Transactional
    // genera la "sesión de persistencia" y administra las relaciones para W asi (sin productID hasta el final)
    // Hibernate hace Updates al final para garantizar las relaciones <- @Transactional
    public CreateProductResponseDto create(ProductRequestDto productDto) {
        Category category = getCategoryById(productDto.getCategoryId());

        // // Si NO depende de otros calculos, PERSISTIR antes q nada para tener el ID y q Hibernate NOO haga 1 UPDATE x c/relacion en la @Transactional
        // NO hace falta la reasigacion para q product adquiera el ID, tras el .save() las demas entities pueden acceder al id
        Product product = productCreator.createProduct(productDto, category);
        productRepository.save(product);    // No UPD los asociados. NOO agregar nada mas al product xq sino va a hacer UPD.
        // Las asociaciones dejarlas al Mapping del DTO y solo agregar al DTO, ya NO tocar l product xq genera el UPD


        ProductMeasurement productMeasurement = productCreator.createProductMeasurement(productDto, product);
        List<ProductImage> productImages = productCreator.createProductImages(productDto, product);


        // // Hibernate administra las relacones entre entidades en Memoria ANTES de la persistencia en DB. X ello, cuando se Persiste el Product, Hiberate
        // actualiza las refs en memoria para setear el ProductID real de las asociaciones y ya culminar con la Persistencia en BD (@Transactional)
        // esto genera 1 UPDATE en DB x cada Entidad relacionada, x eso Persistir cuanto antes el MainTable para tener su ID y evitar tantos
        // UPDATES en DB, sobrecargando la DB innecesariamente.
        productMeasurementRepository.save(productMeasurement);
        productImageRepository.saveAll(productImages);

        // ya tengo el ID, evito update innecesrio
        Stock stock = productCreator.createStock(product, productDto.getQuantity(), productMeasurement);
        stockRepository.save(stock);

        // productRepository.save(product);  // aqui NOOO se UPD el product, pero los asociados SI (no eficiente)

        // // todos el mapping en el DTO para evitar UPD en el product. Nooo agregar nada ni tocar el Product Instance para evitar UPD
        // con esto, en este punto YAAA evito los UPD innecesarios
        return productCreator.mapToCreateProductResponseDto(product, productMeasurement, stock, productImages);
    }
    */

    @Override
    @Transactional
    public CreateProductResponseDto create(ProductRequestDto productDto) {
        Category category = getCategoryById(productDto.getCategoryId());

        // Crear todas las asociaciones primero
        Product product = productCreator.createProduct(productDto, category);
        ProductMeasurement productMeasurement = productCreator.createProductMeasurement(productDto, product);
        List<ProductImage> productImages = productCreator.createProductImages(productDto, product);
        Stock stock = productCreator.createStock(product, productDto.getQuantity(), productMeasurement);

        // Persistir el producto después de crear las asociaciones
        productRepository.save(product);
        productMeasurementRepository.save(productMeasurement);
        productImageRepository.saveAll(productImages);
        stockRepository.save(stock);

        // Mapear a DTO
        return productCreator.mapToCreateProductResponseDto(product, productMeasurement, stock, productImages);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginatedProductsResponseDto findAll(Pageable pageable) {
        Page<Product> productPage = productRepository.fetchAll(pageable);  // @override do not required in Repository <- JpaRepository
        List<Product> products = productPage.getContent();
        List<PaginatedProductsResponseDto.ProductDto> productDtoList = products
                .stream()
                .map(product -> modelMapper.map(product, PaginatedProductsResponseDto.ProductDto.class))
                .toList(); // jdk 20
//                .collect(Collectors.toList());


        return PaginatedProductsResponseDto.builder()
                .products(productDtoList)
                .pageNumber(productPage.getNumber())
                .size(productPage.getSize())
                .totalElements(productPage.getTotalElements())
                .totalPages(productPage.getTotalPages())
                .isLastOne(productPage.isLast())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ProductResponseDto findOne(Long id) {
        Product product = getProductById(id);

        return modelMapper.map(product, ProductResponseDto.class);
    }

    /*
    @Override
    @Transactional
    public ProductResponseDto update(Long productId, ProductUPDRequestDto productDto) {
        Product product = productRepository.fetchOneById(productId).orElseThrow(
                () -> new ResourceNotFoundException("Product", "ID", productId)
        );

        Category category = categoryRepository.findById(productDto.getCategoryId()).orElseThrow(
                () -> new ResourceNotFoundException("Category", "ID", productDto.getCategoryId())
        );

        List<ProductMeasurement> productMeasurementsDb = productMeasurementRepository.fetchAllByProductId(productId);
        List<ProductMeasurement> updatedProductMeasurements = new ArrayList<>();
        // NO es Flexible xq requiere q el front envie tal cual se recupera de la DB (sort by ID desc)
        for (int i = 0; i < productDto.getProductMeasurements().size(); i++) {
            if (Objects.equals(productDto.getProductMeasurements().get(i).getId(), productMeasurementsDb.get(i).getId())) {
                ProductMeasurement productMeasurement = modelMapper.map(productDto.getProductMeasurements().get(i), ProductMeasurement.class);
                productMeasurement.setProduct(product);
                updatedProductMeasurements.add(productMeasurement);
            } else {
                throw new ResourceNotFoundException("Product Measurement", "ID", productDto.getProductMeasurements().get(i).getId());
            }
        }
        productMeasurementRepository.saveAll(updatedProductMeasurements);

        List<ProductImage> productImagesDb = productImageRepository.findAllByProductIdOrderByIdAsc(productId);
        List<ProductImage> updatedProductImages = new ArrayList<>();
        for (int i = 0; i < productDto.getImages().size(); i++) {
            if (Objects.equals(productDto.getImages().get(i).getId(), productImagesDb.get(i).getId())) {
                ProductImage productImage = modelMapper.map(productDto.getImages().get(i), ProductImage.class);
                productImage.setProduct(product);
                updatedProductImages.add(productImage);
            } else {
                throw new ResourceNotFoundException("Product Image", "ID", productImagesDb.get(i).getId());
            }
        }
        productImageRepository.saveAll(updatedProductImages);

        List<Stock> stocksDb = stockRepository.findAllByProductIdOrderByIdAsc(productId);
        List<Stock> updatedStocks = new ArrayList<>();
        for (int i = 0; i < productDto.getStocks().size(); i++) {
            if (Objects.equals(productDto.getStocks().get(i).getQuantityId(), stocksDb.get(i).getId())) {
                Stock stock = modelMapper.map(productDto.getStocks().get(i), Stock.class);
                stock.setProduct(product);
                stock.setProductMeasurement(stocksDb.get(i).getProductMeasurement()); // OJO con el ID al hacer el UPD
                updatedStocks.add(stock);
            } else {
                throw new ResourceNotFoundException("Stock", "ID", productDto.getStocks().get(i).getQuantityId());
            }
        }
        stockRepository.saveAll(updatedStocks);


        // // set category before mapping, otherwise it throw an error
        product.setCategory(category);
        product.setImages(updatedProductImages);
        product.setProductMeasurements(updatedProductMeasurements);
        product.setStocks(updatedStocks);
        product.setCategory(category);
        product.setTitle(productDto.getTitle());
        product.setDescription(productDto.getDescription());
        product.setPrice(productDto.getPrice());

        // modelMapper.map(productDto, product); // NOOO usar para DTO to ENTITY cuando son Complejas, tiene otros Objects dentro (Table Associations)
        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, ProductResponseDto.class);
    }
    */


    @Override
    @Transactional
    public ProductResponseDto update(Long productId, ProductUPDRequestDto productDto) {
        Product product = getProductById(productId);
        Category category = getCategoryById(productDto.getCategoryId());

        List<ProductMeasurement> updatedProductMeasurements =
                productUpdater.updateProductMeasurements(productId, productDto.getProductMeasurements(), product);
        List<ProductImage> updatedProductImages =
                productUpdater.updateProductImages(productId, productDto.getImages(), product);
        List<Stock> updatedStocks =
                productUpdater.updateStocks(productId, productDto.getStocks(), product);

        // // do not use ModelMapper to avoid cache/fk/upd errors in complex entities like this (Associations) | err of change FK in DB
        productUpdater.updateProductFields(product, category, updatedProductMeasurements, updatedProductImages, updatedStocks, productDto);

        Product savedProduct = productRepository.save(product);

        return modelMapper.map(savedProduct, ProductResponseDto.class);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        int updatedRows = productRepository.markAsDeleted(id);
        if (updatedRows == 0) throw new ResourceNotFoundException("Product", "ID", id);

        // mark all records associated with this productId
        productMeasurementRepository.markAsDeletedByProductId(id);
        stockRepository.markAsDeletedByProductId(id);

    }


    private Product getProductById(Long productId) {
        return productRepository.fetchOneById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("Product", "ID", productId));
    }

    private Category getCategoryById(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Category", "ID", categoryId));
    }

}
