package com.alex.inventorymanagement.users.service;

import com.alex.inventorymanagement.auth.entity.Role;
import com.alex.inventorymanagement.auth.repository.RoleRepository;
import com.alex.inventorymanagement.common.constants.RoleType;
import com.alex.inventorymanagement.common.exceptions.BadRequestException;
import com.alex.inventorymanagement.common.exceptions.ResourceNotFoundException;
import com.alex.inventorymanagement.common.exceptions.UserNotFoundException;
import com.alex.inventorymanagement.users.dto.UserResponseDto;
import com.alex.inventorymanagement.users.entity.Usuario;
import com.alex.inventorymanagement.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ModelMapper modelMapper;


    @Override
    @Transactional
    public Usuario create(Usuario user) {
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new BadRequestException("User Already Registered");
        }

        Role role = roleRepository.findOneByName(RoleType.USER_ROLE.name()).orElseThrow(
                () -> new ResourceNotFoundException("Role", "name", RoleType.USER_ROLE.name())
        );
        user.setRoles(Collections.singleton(role));  // Collections.singleton()  --> return  Set<T>

        return userRepository.save(user);
    }

    @Override
    @Transactional(readOnly = true)
    public Usuario findOneByEmail(String email) {
        return userRepository.findOneByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email: ".concat(email))
        );
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDto findOne(Long id) {
        Usuario user = userRepository.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with id: ".concat(id.toString()))
        );

        return modelMapper.map(user, UserResponseDto.class);
    }

}
