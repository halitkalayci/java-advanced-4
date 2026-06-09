package com.turkcell.productservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SpringDataProductRepository extends JpaRepository<ProductJpaEntity, UUID> {
    boolean existsByName(String name);
}
