package com.turkcell.gateway.filter;

import com.turkcell.gateway.session.SessionKeys;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class TokenRelayGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        System.out.println("Filter çalıştı.");
        String path = exchange.getRequest().getPath().value();
        if(!path.startsWith("/api/"))
            return chain.filter(exchange);

        return exchange.getSession().flatMap(session -> {
           String token = session.getAttribute(SessionKeys.ACCESS_TOKEN);
           if(token == null || token.isBlank()){
               exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
               return exchange.getResponse().setComplete();
           }

            ServerHttpRequest mutated = exchange
                    .getRequest()
                    .mutate()
                    .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
                    .build();

           return chain.filter(exchange.mutate().request(mutated).build());
        });
    }

    @Override
    public int getOrder() {
        return 0;
    }
}
