package com.turkcell.bff.service;

import com.turkcell.bff.web.dto.LoginResponse;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClient;
import org.springframework.web.server.ResponseStatusException;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Service
public class KeycloakService
{
    private final ObjectMapper objectMapper;
    private final RestClient http;

    public KeycloakService(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        this.http = RestClient.create();
    }

    public LoginResponse login(String username, String password)
    {
        MultiValueMap<String, String> form = new LinkedMultiValueMap<>();
        form.add("grant_type", "password");
        form.add("client_id", "smartorder-public");
        form.add("username", username);
        form.add("password", password);

        Map<String, Object> tokenResponse;

        try {
            tokenResponse = http
                    .post()
                    .uri("http://localhost:8085/realms/smartorder/protocol/openid-connect/token")
                    .body(form)
                    .retrieve()
                    .body(Map.class);
        }
        catch(Exception e)
        {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Kullanıcı adı veya şifre hatalı.");
        }
        if(tokenResponse==null)
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Kullanıcı adı veya şifre hatalı.");

        String accessToken = String.valueOf(tokenResponse.get("access_token"));
        String refreshToken = String.valueOf(tokenResponse.get("refresh_token"));
        long expiresIn = ((Number)tokenResponse.getOrDefault("expires_in",0)).longValue();

        return new LoginResponse(accessToken, refreshToken, expiresIn);
    }
}
