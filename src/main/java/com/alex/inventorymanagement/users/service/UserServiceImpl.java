package com.alex.inventorymanagement.users.service;

import com.alex.inventorymanagement.auth.entity.Role;
import com.alex.inventorymanagement.auth.repository.RoleRepository;
import com.alex.inventorymanagement.common.constants.RoleConstants;
import com.alex.inventorymanagement.common.exceptions.BadRequestException;
import com.alex.inventorymanagement.common.exceptions.ResourceNotFoundException;
import com.alex.inventorymanagement.users.entity.Usuario;
import com.alex.inventorymanagement.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;


    @Override
    @Transactional
    public Usuario create(Usuario user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("User Already Registered");
        }

        Role role = roleRepository.findByName(RoleConstants.USER).orElseThrow(
                () -> new ResourceNotFoundException("Role", "name", RoleConstants.USER)
        );
        user.setRoles(Collections.singleton(role));  // Collections.singleton()  --> return  Set<T>

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findOneByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                // CustomUserDetailsService needs this exception
                () -> new UsernameNotFoundException("User not found with email: ".concat(email))
        );
    }

}
