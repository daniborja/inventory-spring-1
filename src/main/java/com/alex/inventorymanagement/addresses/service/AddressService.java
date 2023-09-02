package com.alex.inventorymanagement.addresses.service;

import com.alex.inventorymanagement.addresses.dto.AddressRequestDto;
import com.alex.inventorymanagement.addresses.entity.Address;


public interface AddressService {

    Address create(AddressRequestDto addressDto);

}
