package com.turkcell.productservice.infrastructure.web.dto;

import com.turkcell.productservice.application.port.in.ListProductUseCase;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(
        UUID id,
        String name,
        boolean active,
        BigDecimal price,
        String currency
) {
    public static ProductResponse from(ListProductUseCase.ProductView view)
    {
        return new ProductResponse(
                view.id(),
                view.name(),
                view.active(),
                view.price(),
                view.currency()
        );
    }
}
