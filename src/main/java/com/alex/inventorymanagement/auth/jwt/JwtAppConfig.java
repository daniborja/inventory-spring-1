package com.alex.inventorymanagement.auth.jwt;

import com.alex.inventorymanagement.auth.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


@Configuration      // register as @Bean Config
@RequiredArgsConstructor     // Inject all final properties by constructor
public class JwtAppConfig {

    // // Inject in auto by @RequiredArgsConstructor (final attributes - constructor)
    private final CustomUserDetailsService customUserDetailsService;
    // private final UserRepository userRepository;


    @Bean   // la 1ra implementacion q Spring va a encontrar para INJECT in  WebSecurityConfig
    public AuthenticationProvider authenticationProvider() {
        // es el encargado de hacer Fetch del userDetails, Encode Password, etc.
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(customUserDetailsService);
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }


    // // auth user with this implementation
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    // set PasswordEncode as @Bean to inject it
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
