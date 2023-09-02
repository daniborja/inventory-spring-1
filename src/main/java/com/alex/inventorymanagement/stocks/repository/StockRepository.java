package com.alex.inventorymanagement.stocks.repository;

import com.alex.inventorymanagement.stocks.entity.Stock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;


public interface StockRepository extends CrudRepository<Stock, Long> {

    @Modifying
    @Query("UPDATE Stock s SET s.deleted = true WHERE s.product.id = :productId")
    void markAsDeletedByProductId(Long productId);

    List<Stock> findAllByProductIdOrderByIdAsc(Long productId);

    Optional<Stock> findByProductMeasurementId(Long productMeasurementId);

}
