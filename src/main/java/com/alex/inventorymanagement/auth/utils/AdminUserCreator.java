package com.alex.inventorymanagement.auth.utils;

import com.alex.inventorymanagement.auth.entity.Role;
import com.alex.inventorymanagement.auth.repository.RoleRepository;
import com.alex.inventorymanagement.common.constants.RoleConstants;
import com.alex.inventorymanagement.users.entity.Usuario;
import com.alex.inventorymanagement.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;


@Component
@RequiredArgsConstructor
public class AdminUserCreator implements ApplicationListener<ApplicationReadyEvent> {

    @Value("${app.adminusercreator.email}")
    private String adminEmail;

    @Value("${app.adminusercreator.password}")
    private String adminPassword;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    @Transactional
    public void onApplicationEvent(ApplicationReadyEvent event) {
        createAdminRoleIfNotExists();
        createAdminUserIfNotExists();
    }

    private void createAdminRoleIfNotExists() {
        Role adminRole = roleRepository.findByName(RoleConstants.ADMIN).orElse(null);
        if (adminRole == null) {
            adminRole = new Role();
            adminRole.setName(RoleConstants.ADMIN);
            roleRepository.save(adminRole);
        }
    }

    private void createAdminUserIfNotExists() {
        Usuario adminUser = userRepository.findByEmail(adminEmail).orElse(null);
        Role adminRole = roleRepository.findByName(RoleConstants.ADMIN).orElse(null);

        if (adminUser == null) {
            adminUser = new Usuario();
            adminUser.setRoles(Collections.singleton(adminRole));
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setDeleted(false);
            adminUser.setFirstname("Admin");
            adminUser.setLastname("Admin");

            userRepository.save(adminUser);
        }
    }
}
