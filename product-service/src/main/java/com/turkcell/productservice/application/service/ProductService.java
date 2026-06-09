package com.turkcell.productservice.application.service;

import com.turkcell.productservice.application.port.in.CreateProductUseCase;
import com.turkcell.productservice.domain.exception.DuplicateProductNameException;
import com.turkcell.productservice.domain.model.Product;
import com.turkcell.productservice.domain.repository.ProductRepository;
import com.turkcell.productservice.domain.vo.Money;
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
        if(productRepository.existsByName(createProductCommand.name()))
            throw new DuplicateProductNameException(createProductCommand.name());

        Product product = Product.create(
                createProductCommand.name(),
                Money.of(createProductCommand.priceAmount(), createProductCommand.currencyCode()));

        productRepository.save(product);

        return product.id();
    }
}
