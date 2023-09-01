package com.alex.inventorymanagement.products.repository;

import com.alex.inventorymanagement.products.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {

    // // // PSQL - OK  |  more efficient than  .findAll()  <-- FETCH
    @Query("SELECT p FROM Product p LEFT JOIN FETCH Category c ON c.id = p.category.id " +
            "LEFT JOIN FETCH ProductMeasurement pm ON pm.product.id = p.id LEFT JOIN FETCH Stock s ON s.product.id = p.id " +
            "LEFT JOIN FETCH ProductImage pim ON pim.product.id = p.id WHERE p.deleted = false "
    )
    Page<Product> fetchAll(Pageable pageable);

    @Query("SELECT p FROM Product p LEFT JOIN FETCH Category c ON c.id = p.category.id " +
            "LEFT JOIN FETCH ProductMeasurement pm ON pm.product.id = p.id LEFT JOIN FETCH Stock s ON s.product.id = p.id " +
            "LEFT JOIN FETCH ProductImage pim ON pim.product.id = p.id WHERE p.deleted = false AND p.id = :id "
    )
    Optional<Product> fetchOneById(Long id);


    // // // Store Procedure (PostgreSQL Function): 'cause of the Mapping DTO create another calls to DB
    @Query(nativeQuery = true, value = "SELECT * FROM get_products_with_details()")
    List<Product> getProductsWithDetails();



    // // // Soft Delete
    @Modifying  // indicate that this query performs data modification operation (all default @Query expects SELECT statement)
    @Query("UPDATE Product p SET p.deleted = true WHERE p.id = :id ")  // product.deleted = false ya lo coloca x el @Where de la Entity
    int markAsDeleted(Long id);

}
