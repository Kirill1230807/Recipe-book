package com.bank.recipebook.dto.auth;

public record LoginRequest(
        String email,
        String password
) {}