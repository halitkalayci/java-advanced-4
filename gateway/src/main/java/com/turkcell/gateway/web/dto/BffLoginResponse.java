package com.turkcell.gateway.web.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * BFF'in döndüğü cevap
 */
public record BffLoginResponse(String accessToken, String refreshToken, long expiresIn) { }


