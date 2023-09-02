package com.alex.inventorymanagement.addresses.repository;

import com.alex.inventorymanagement.addresses.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;


public interface AddressRepository extends JpaRepository<Address, Long> {

}
