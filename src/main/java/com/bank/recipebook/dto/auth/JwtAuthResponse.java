package com.bank.recipebook.dto.auth;

public record JwtAuthResponse(
        String accessToken,
        String tokenType
) {}