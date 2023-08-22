package com.alex.inventorymanagement.auth.service;

import com.alex.inventorymanagement.auth.entity.Role;
import com.alex.inventorymanagement.auth.repository.RoleRepository;
import com.alex.inventorymanagement.common.exceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;


    @Override
    public Role findOneByName(String name) {
        return roleRepository.findOneByName(name).orElseThrow(
                () -> new ResourceNotFoundException("Role", "name", name)
        );
    }

}
