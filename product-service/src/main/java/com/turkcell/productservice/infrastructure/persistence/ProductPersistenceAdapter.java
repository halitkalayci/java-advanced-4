package com.turkcell.productservice.infrastructure.persistence;

import com.turkcell.productservice.domain.model.Product;
import com.turkcell.productservice.domain.repository.ProductRepository;
import com.turkcell.productservice.domain.vo.Money;
import com.turkcell.productservice.domain.vo.ProductId;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;


// ProductRepository portunu JPA'a çevir.
@Component
public class ProductPersistenceAdapter implements ProductRepository {
    private final SpringDataProductRepository jpaRepo;

    public ProductPersistenceAdapter(SpringDataProductRepository jpaRepo) {
        this.jpaRepo = jpaRepo;
    }

    @Override
    public void save(Product product) {
        // Adapter => Domain to Infra ya da tam tersi mappinglerin yapıldığı yerdir.

        // MapStruct vb. AutoMapper
        jpaRepo.save(toEntity(product));
    }

    @Override
    public Optional<Product> findById(ProductId id) {
        return jpaRepo.findById(id.value()).map(this::toDomain);
    }

    @Override
    public Optional<Product> findByName(String name) {
        return Optional.empty();
    }

    @Override
    public List<Product> findAll() {
        return jpaRepo.findAll().stream().map(this::toDomain).toList();
    }

    @Override
    public boolean existsByName(String name) {
        return jpaRepo.existsByName(name);
    }


    private ProductJpaEntity toEntity(Product product)
    {
        return new ProductJpaEntity(
                product.id().value(),
                product.name(),
                product.isActive(),
                product.price().amount(),
                product.price().currency().toString()
        );
    }

    private Product toDomain(ProductJpaEntity entity)
    {
        return Product.reconstitute(
                ProductId.of(entity.id().toString()),
                entity.name(),
                entity.active(),
                Money.of(entity.priceAmount().toString(), entity.priceCurrency())
        );
    }
}
