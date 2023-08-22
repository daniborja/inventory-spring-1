package com.alex.inventorymanagement.auth.service;

import com.alex.inventorymanagement.auth.entity.Role;


public interface RoleService {

    Role findOneByName(String name);

}
