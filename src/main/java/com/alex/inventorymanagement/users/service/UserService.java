package com.alex.inventorymanagement.users.service;

import com.alex.inventorymanagement.users.entity.Usuario;


public interface UserService {

    Usuario findOneByEmail(String email);

}
