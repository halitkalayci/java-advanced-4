package com.turkcell.productservice.infrastructure.web;

import com.turkcell.productservice.application.port.in.CreateProductUseCase;
import com.turkcell.productservice.domain.vo.ProductId;
import com.turkcell.productservice.infrastructure.web.dto.CreateProductRequest;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

// Create işlemini tetikliyorsun.
// HTTP JSON -> UseCase
@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    private final CreateProductUseCase createProductUseCase;

    public ProductsController(CreateProductUseCase createProductUseCase) {
        this.createProductUseCase = createProductUseCase;
    }

    @PostMapping
    public ResponseEntity<ProductId> create(@Valid @RequestBody CreateProductRequest request,
                                            UriComponentsBuilder uriBuilder)
    {
        ProductId id = createProductUseCase.create(
                new CreateProductUseCase.CreateProductCommand(
                        request.name(),
                        request.price(),
                        request.currency()
                )
        );
        URI location = uriBuilder.path("/api/v1/products/{id}").buildAndExpand(id.value()).toUri();
        return ResponseEntity.created(location).body(id);
    }
}
