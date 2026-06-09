package com.turkcell.productservice.infrastructure.web;

import com.turkcell.productservice.application.port.in.CreateProductUseCase;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    private final CreateProductUseCase createProductUseCase;

    public ProductsController(CreateProductUseCase createProductUseCase) {
        this.createProductUseCase = createProductUseCase;
    }
}
