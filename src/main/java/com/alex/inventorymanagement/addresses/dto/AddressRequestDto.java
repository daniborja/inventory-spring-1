package com.alex.inventorymanagement.addresses.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;


@Data
public class AddressRequestDto {

    @NotEmpty
    private String country;

    @NotEmpty
    private String city;

    private String province;

    @NotEmpty
    private String mainStreet;
    private String secondaryStreet;

    private String zipCode;
    private String phone;

    // user by Auth

}
