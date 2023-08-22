package com.alex.inventorymanagement.auth.dto;

import lombok.Data;

import java.util.List;


public class AuthResponseDto {

    private String token;
    private UserDto user;

    @Data
    public static class UserDto {
        private String name;
        private String lastName;
        private String email;
        private List<RoleDto> roles;
        private long id;
    }

    @Data
    public static class RoleDto {
        private int id;
        private String name;
    }

}
