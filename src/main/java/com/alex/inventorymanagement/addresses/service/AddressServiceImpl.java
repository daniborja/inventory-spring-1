package com.alex.inventorymanagement.addresses.service;

import com.alex.inventorymanagement.addresses.dto.AddressRequestDto;
import com.alex.inventorymanagement.addresses.entity.Address;
import com.alex.inventorymanagement.addresses.repository.AddressRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final ModelMapper modelMapper;


    @Override
    public Address create(AddressRequestDto addressDto) {
        Address address = modelMapper.map(addressDto, Address.class);

        return addressRepository.save(address);
    }

}
