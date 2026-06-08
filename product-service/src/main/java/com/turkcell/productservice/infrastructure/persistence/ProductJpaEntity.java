package com.turkcell.productservice.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Product domain nesnesine karışamadığım için o nesneye veritabanı olarak ben ayak uyduruyorum..
 * Ayak uydurmam için o nesneyi veritabamına uydurmam lazım.
 * BU SADECE VERİTABANI İLE DOMAİN MODELİ ARASINDA KÖPRÜ
 */
@Entity
@Table(name="products")
public class ProductJpaEntity {
    @Id
    @Column(name="id", nullable = false, updatable = false)
    private UUID id;
    @Column(name="name", nullable = false)
    private String name;
    @Column(name="active", nullable = false)
    private boolean active;
    @Column(name="price_amount", nullable = false)
    private BigDecimal priceAmount;
    @Column(name="price_currency", nullable = false)
    private String priceCurrency;

    public ProductJpaEntity() {}

    public ProductJpaEntity(UUID id, String name, boolean active, BigDecimal priceAmount, String priceCurrency) {
        this.id = id;
        this.name = name;
        this.active = active;
        this.priceAmount = priceAmount;
        this.priceCurrency = priceCurrency;
    }

    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String name() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean active() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public BigDecimal priceAmount() {
        return priceAmount;
    }

    public void setPriceAmount(BigDecimal priceAmount) {
        this.priceAmount = priceAmount;
    }

    public String priceCurrency() {
        return priceCurrency;
    }

    public void setPriceCurrency(String priceCurrency) {
        this.priceCurrency = priceCurrency;
    }
}
