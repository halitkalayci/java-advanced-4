package com.turkcell.productservice.application.service;

import com.turkcell.productservice.application.port.in.CreateProductUseCase;
import com.turkcell.productservice.domain.repository.ProductRepository;
import com.turkcell.productservice.domain.vo.ProductId;
import org.springframework.stereotype.Service;

@Service
public class ProductService implements CreateProductUseCase {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public ProductId create(CreateProductCommand createProductCommand) {
        // İş kuralları vs..
        // aksiyonel iş kuralı -> Veritabanında aynı isimde ürün var mı?
        return null;
    }
}
