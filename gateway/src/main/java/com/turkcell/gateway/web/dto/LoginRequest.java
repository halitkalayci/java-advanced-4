package com.turkcell.gateway.web.dto;

import jakarta.validation.constraints.NotBlank; /**
 * Gatewaya gelecek giriş isteği.
 */
public record LoginRequest(@NotBlank String username, @NotBlank String password) {}
