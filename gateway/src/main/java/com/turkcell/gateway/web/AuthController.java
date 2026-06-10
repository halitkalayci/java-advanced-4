package com.turkcell.gateway.web;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
@RequestMapping("/bff")
public class AuthController
{
    private final WebClient bffClient;

    public AuthController(WebClient bffClient) {
        this.bffClient = bffClient;
    }


}
