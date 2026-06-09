package com.turkcell.orderservice.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="orders")
public class Order {
    @Id
    @UuidGenerator
    private UUID id;
    private Instant orderDate;
    private BigDecimal totalAmount;


    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Instant orderDate() {
        return orderDate;
    }

    public void setOrderDate(Instant orderDate) {
        this.orderDate = orderDate;
    }

    public BigDecimal totalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }
}
