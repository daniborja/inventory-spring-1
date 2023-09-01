package com.alex.inventorymanagement.products.repository;

import com.alex.inventorymanagement.products.entity.ProductMeasurement;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ProductMeasurementRepository extends CrudRepository<ProductMeasurement, Long> {

    @Modifying
    @Query("UPDATE ProductMeasurement pm SET pm.deleted = true WHERE pm.product.id = :productId")
    void markAsDeletedByProductId(Long productId);


    // // UPD
    @Query("SELECT pm FROM ProductMeasurement pm WHERE pm.product.id = :productId") // en auto trae los deleted=false
    List<ProductMeasurement> fetchAllByProductId(Long productId);

}
