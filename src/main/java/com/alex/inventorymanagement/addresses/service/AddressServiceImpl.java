package com.alex.inventorymanagement.addresses.service;

import com.alex.inventorymanagement.addresses.dto.AddressRequestDto;
import com.alex.inventorymanagement.addresses.dto.AddressResponseDto;
import com.alex.inventorymanagement.addresses.entity.Address;
import com.alex.inventorymanagement.addresses.repository.AddressRepository;
import com.alex.inventorymanagement.common.exceptions.ResourceNotFoundException;
import com.alex.inventorymanagement.users.entity.Usuario;
import com.alex.inventorymanagement.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final UserService userService;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public AddressResponseDto create(AddressRequestDto addressDto, String authUserEmail) {
        Address address = modelMapper.map(addressDto, Address.class);
        Usuario user = userService.findOneByEmail(authUserEmail);
        address.setUser(user);

        return modelMapper.map(addressRepository.save(address), AddressResponseDto.class);
    }

    @Override
    public AddressResponseDto findOne(Long id) {
        Address address = addressRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Address", "ID", id)
        );

        return modelMapper.map(address, AddressResponseDto.class);
    }

    @Override
    public List<AddressResponseDto> findAllByAuthUser(String authUserEmail) {
        Usuario user = userService.findOneByEmail(authUserEmail);

        List<Address> addressesDb = addressRepository.findAllByUserId(user.getId());

        return addressesDb.stream()
                .map(address -> modelMapper.map(address, AddressResponseDto.class))
                .toList();
    }

}
