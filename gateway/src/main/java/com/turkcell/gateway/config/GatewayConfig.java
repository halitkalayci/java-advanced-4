package com.turkcell.gateway.config;

import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class GatewayConfig {
    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        return builder
                .routes()
                // Discovery Server + LB
                .route("product-route", r->r.path("/api/v1/products/**").uri("http://localhost:8080"))
                .build();
    }

    @Bean
    public WebClient bffClient()
    {
        return WebClient.builder().baseUrl("http://localhost:8086").build();
    }
}
