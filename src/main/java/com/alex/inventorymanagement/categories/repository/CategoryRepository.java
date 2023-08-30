package com.alex.inventorymanagement.categories.repository;

import com.alex.inventorymanagement.categories.entity.Category;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;


public interface CategoryRepository extends CrudRepository<Category, Long> {

//    Optional<Category> findById(Long i);

}
