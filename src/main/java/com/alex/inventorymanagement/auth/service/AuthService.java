package com.alex.inventorymanagement.auth.service;

import com.alex.inventorymanagement.auth.dto.AuthResponseDto;
import com.alex.inventorymanagement.auth.dto.LoginRequestDto;
import com.alex.inventorymanagement.auth.dto.RegisterRequestDto;


public interface AuthService {

    AuthResponseDto register(RegisterRequestDto registerDto);

    AuthResponseDto login(LoginRequestDto loginDto);

    AuthResponseDto renewJwt(String userEmail);

}
