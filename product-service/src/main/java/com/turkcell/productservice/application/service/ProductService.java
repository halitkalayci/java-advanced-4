package com.turkcell.productservice.application.service;

import com.turkcell.productservice.application.port.in.CreateProductUseCase;
import com.turkcell.productservice.application.port.in.ListProductUseCase;
import com.turkcell.productservice.domain.exception.DuplicateProductNameException;
import com.turkcell.productservice.domain.model.Product;
import com.turkcell.productservice.domain.repository.ProductRepository;
import com.turkcell.productservice.domain.vo.Money;
import com.turkcell.productservice.domain.vo.ProductId;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService implements CreateProductUseCase, ListProductUseCase {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    @Transactional
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

    @Override
    @Transactional
    public List<ProductView> list() {
        return productRepository.findAll().stream().map(ProductService::toView).toList();
    }


    private static ProductView toView(Product product)
    {
        return new ProductView(
                product.id().value(),
                product.name(),
                product.isActive(),
                product.price().amount(),
                product.price().currency().getCurrencyCode()
        );
    }
}
