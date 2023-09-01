package com.alex.inventorymanagement.products.repository;

import com.alex.inventorymanagement.products.entity.ProductImage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductImageRepository extends CrudRepository<ProductImage, Long> {

    List<ProductImage> findAllByProductIdOrderByIdAsc(Long productId); // en auto deleted=false

}
