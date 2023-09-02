package com.alex.inventorymanagement.addresses.service;

import com.alex.inventorymanagement.addresses.dto.AddressRequestDto;
import com.alex.inventorymanagement.addresses.dto.AddressResponseDto;
import com.alex.inventorymanagement.users.entity.Usuario;

import java.util.List;


public interface AddressService {

    AddressResponseDto create(AddressRequestDto addressDto, String authUserEmail);

    AddressResponseDto findOne(Long id);

    List<AddressResponseDto> findAllByAuthUser(String authUserEmail);
}
