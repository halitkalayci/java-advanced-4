package com.turkcell.productservice.controller;


import com.turkcell.v1.api.ProductsApi;
import com.turkcell.v1.model.*;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ProductsController implements ProductsApi {
    @Override
    public ResponseEntity<Product> createProduct(ProductCreateRequest productCreateRequest) {
        return ProductsApi.super.createProduct(productCreateRequest);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(UUID id) {
        return ProductsApi.super.deleteProduct(id);
    }

    @Override
    public ResponseEntity<Product> getProductById(UUID id) {
        return ProductsApi.super.getProductById(id);
    }

    @Override
    public ResponseEntity<ProductPage> listProducts(Integer page, Integer size, @Nullable String q) {
        return ProductsApi.super.listProducts(page, size, q);
    }

    @Override
    public ResponseEntity<Product> replaceProduct(UUID id, ProductUpdateRequest productUpdateRequest) {
        return ProductsApi.super.replaceProduct(id, productUpdateRequest);
    }

    @Override
    public ResponseEntity<StockUpdateResponse> updateStock(StockUpdateRequest stockUpdateRequest) {
        return ProductsApi.super.updateStock(stockUpdateRequest);
    }
}
