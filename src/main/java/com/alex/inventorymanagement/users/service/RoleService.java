package com.alex.inventorymanagement.users.service;

import com.alex.inventorymanagement.auth.entity.Role;


public interface RoleService {

    Role findOneByName(String name);

}
