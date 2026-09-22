package com.bank.recipebook.dto.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(

        @NotBlank
        @Email
        @Size(max = 255)
        String email,

        @NotBlank
        @Size(min = 6, max = 255)
        String password,

        @NotBlank
        @Size(max = 100)
        String username
) {
}