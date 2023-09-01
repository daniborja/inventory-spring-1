package com.alex.inventorymanagement.products.repository;

import com.alex.inventorymanagement.products.entity.ProductMeasurement;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ProductMeasurementRepository extends CrudRepository<ProductMeasurement, Long> {

    @Modifying
    @Query("UPDATE ProductMeasurement pm SET pm.deleted = true WHERE pm.product.id = :productId")
    void markAsDeletedByProductId(Long productId);

}
