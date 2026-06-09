package com.turkcell.productservice.application.port.in;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface ListProductUseCase {
    List<ProductView> list();


    record ProductView(UUID id, String name, boolean active, BigDecimal price, String currency) {}
}
