package com.alex.inventorymanagement.stocks.repository;

import com.alex.inventorymanagement.stocks.entity.Stock;
import org.springframework.data.repository.CrudRepository;


public interface StockRepository extends CrudRepository<Stock, Long> {

}
