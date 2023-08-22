package com.alex.inventorymanagement.users.service;

import com.alex.inventorymanagement.users.dto.UserResponseDto;
import com.alex.inventorymanagement.users.entity.Usuario;


public interface UserService {

    Usuario create(Usuario user);

    Usuario findOneByEmail(String email);

    UserResponseDto findOne(Long id);

}
