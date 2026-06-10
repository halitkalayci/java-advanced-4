package com.turkcell.productservice.infrastructure.web;

import com.turkcell.productservice.application.port.in.CreateProductUseCase;
import com.turkcell.productservice.application.port.in.ListProductUseCase;
import com.turkcell.productservice.domain.vo.ProductId;
import com.turkcell.productservice.infrastructure.web.dto.CreateProductRequest;
import com.turkcell.productservice.infrastructure.web.dto.ProductResponse;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

// Create işlemini tetikliyorsun.
// HTTP JSON -> UseCase
@RestController
@RequestMapping("/api/v1/products")
public class ProductsController {
    private final CreateProductUseCase createProductUseCase;
    private final ListProductUseCase listProductUseCase;

    public ProductsController(CreateProductUseCase createProductUseCase, ListProductUseCase listProductUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.listProductUseCase = listProductUseCase;
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

    // Application DTO - web DTO'su her zaman ayrı olmalıdır.
    @GetMapping
    public List<ProductResponse> getAll() {
        return listProductUseCase.list().stream().map(ProductResponse::from).toList();
    }
}
