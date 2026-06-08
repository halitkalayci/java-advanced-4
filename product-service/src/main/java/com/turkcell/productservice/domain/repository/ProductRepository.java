package com.turkcell.productservice.domain.repository;

import com.turkcell.productservice.domain.model.Product;
import com.turkcell.productservice.domain.vo.ProductId;

import java.util.List;
import java.util.Optional;

/**
 * Output Port: Domain'in Persistence'dan (kalıcılık katmanı) ne beklediğini tarif ettiği nokta.
 * NASIL Yapıldığı değil NE yapılması gerektiği.
 * (Dependency Inversion)
 * JpaAdapter -> Aşağıdaki işlemleri yapmak zorunda
 * XAdapter -> Aşağıdaki işlemleri yapmak zorunda
 */
public interface ProductRepository {
    void save(Product product);

    Optional<Product> findById(ProductId id);

    Optional<Product> findByName(String name);

    List<Product> findAll();
}
