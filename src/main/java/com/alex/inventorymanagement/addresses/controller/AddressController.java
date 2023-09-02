package com.alex.inventorymanagement.addresses.controller;

import com.alex.inventorymanagement.addresses.dto.AddressRequestDto;
import com.alex.inventorymanagement.addresses.dto.AddressResponseDto;
import com.alex.inventorymanagement.addresses.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/v1/addresses")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;


    @PostMapping
    public ResponseEntity<AddressResponseDto> create(@Validated @RequestBody AddressRequestDto addressRequestDto, Authentication authentication) {
        String authUserEmail = ((UserDetails) authentication.getPrincipal()).getUsername();

        return new ResponseEntity<>(addressService.create(addressRequestDto, authUserEmail), HttpStatus.CREATED);
    }

}
