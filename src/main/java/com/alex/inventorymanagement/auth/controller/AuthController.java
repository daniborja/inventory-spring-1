package com.alex.inventorymanagement.auth.controller;

import com.alex.inventorymanagement.auth.dto.AuthResponseDto;
import com.alex.inventorymanagement.auth.dto.LoginRequestDto;
import com.alex.inventorymanagement.auth.dto.RegisterRequestDto;
import com.alex.inventorymanagement.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private static final String ADMIN_ROLE = "ADMIN_ROLE";

    private final AuthService authService;

    @PostMapping("/register")
//    @Secured({ADMIN_ROLE, "USER"})
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        return new ResponseEntity<>(authService.register(request), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @GetMapping("/renew-token")
    public ResponseEntity<AuthResponseDto> renewJwt(Authentication authentication) {
        String authUserEmail = ((UserDetails) authentication.getPrincipal()).getUsername();

        return ResponseEntity.ok(authService.renewJwt(authUserEmail));
    }

}
