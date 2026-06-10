package com.turkcell.gateway.web;

import com.turkcell.gateway.session.SessionKeys;
import com.turkcell.gateway.web.dto.BffLoginResponse;
import com.turkcell.gateway.web.dto.LoginRequest;
import com.turkcell.gateway.web.dto.UserInfo;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/bff")
public class AuthController
{
    private final WebClient bffClient;

    public AuthController(WebClient bffClient) {
        this.bffClient = bffClient;
    }

    @PostMapping("/login")
    public Mono<UserInfo> login(@Valid @RequestBody LoginRequest loginRequest, WebSession session)
    {
        return bffClient
                .post()
                .uri("/auth/login")
                .bodyValue(loginRequest)
                .retrieve()
                .bodyToMono(BffLoginResponse.class)
                .map(resp -> {
                    session.getAttributes().put(SessionKeys.ACCESS_TOKEN, resp.accessToken());
                    session.getAttributes().put(SessionKeys.REFRESH_TOKEN, resp.refreshToken());
                    session.getAttributes().put(SessionKeys.USERNAME, "buraya_username_gelecek");
                    return new UserInfo("buraya_username_gelecek");
                })
                .onErrorMap(WebClientResponseException.class, this::relay);
    }

    private Throwable relay(WebClientResponseException ex)
    {
        return new ResponseStatusException(ex.getStatusCode(), ex.getResponseBodyAsString());
    }
}
