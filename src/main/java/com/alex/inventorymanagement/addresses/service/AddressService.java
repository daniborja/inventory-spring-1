package com.alex.inventorymanagement.addresses.service;

import com.alex.inventorymanagement.addresses.dto.AddressRequestDto;
import com.alex.inventorymanagement.addresses.dto.AddressResponseDto;


public interface AddressService {

    AddressResponseDto create(AddressRequestDto addressDto, String authUserEmail);

    AddressResponseDto findOne(Long id);
}
