package com.turkcell.bff.web.dto;

import java.util.List;

public record LoginResponse(String accessToken, String refreshToken, long expiresIn) { }
