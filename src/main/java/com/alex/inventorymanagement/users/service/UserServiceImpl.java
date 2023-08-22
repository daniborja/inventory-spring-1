package com.alex.inventorymanagement.users.service;

import com.alex.inventorymanagement.users.entity.Usuario;
import com.alex.inventorymanagement.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Override
    public Usuario findOneByEmail(String email) {
        return userRepository.findOneByEmail(email).orElseThrow(

        );
    }
}
