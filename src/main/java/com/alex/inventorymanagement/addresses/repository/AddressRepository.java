package com.alex.inventorymanagement.addresses.repository;

import com.alex.inventorymanagement.addresses.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findAllByUserId(Long userId);

}
