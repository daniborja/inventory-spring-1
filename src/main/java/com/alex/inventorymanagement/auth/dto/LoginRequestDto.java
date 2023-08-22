package com.alex.inventorymanagement.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.Data;


@Data
public class LoginRequestDto {

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$",
            message = "Password must be at least 8 characters long and contain at least one lowercase letter, one uppercase letter, and one digit."
    )
    private String password;

}
