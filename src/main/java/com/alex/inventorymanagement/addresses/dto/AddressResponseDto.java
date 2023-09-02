package com.alex.inventorymanagement.addresses.dto;

import lombok.Data;


@Data
public class AddressResponseDto {

    private String country;
    private String city;
    private String province;
    private String mainStreet;
    private String secondaryStreet;
    private String zipCode;
    private String phone;
    private Long userId;

}
