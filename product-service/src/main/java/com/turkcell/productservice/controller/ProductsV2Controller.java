package com.turkcell.productservice.controller;

import com.turkcell.v2.api.ProductsV2Api;
import com.turkcell.v2.model.*;
import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class ProductsV2Controller implements ProductsV2Api {
    @Override
    public ResponseEntity<ProductV2> createProduct(ProductCreateRequestV2 productCreateRequestV2) {
        return ProductsV2Api.super.createProduct(productCreateRequestV2);
    }

    @Override
    public ResponseEntity<Void> deleteProduct(UUID id) {
        return ProductsV2Api.super.deleteProduct(id);
    }

    @Override
    public ResponseEntity<ProductV2> getProductById(UUID id) {
        return ProductsV2Api.super.getProductById(id);
    }

    @Override
    public ResponseEntity<ProductPageV2> listProducts(Integer page, Integer size, @Nullable String q) {
        return ProductsV2Api.super.listProducts(page, size, q);
    }

    @Override
    public ResponseEntity<ProductV2> replaceProduct(UUID id, ProductUpdateRequestV2 productUpdateRequestV2) {
        return ProductsV2Api.super.replaceProduct(id, productUpdateRequestV2);
    }

    @Override
    public ResponseEntity<StockUpdateResponseV2> updateStock(StockUpdateRequestV2 stockUpdateRequestV2) {
        return ProductsV2Api.super.updateStock(stockUpdateRequestV2);
    }
}
