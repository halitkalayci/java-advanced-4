package com.turkcell.orderservice.entity;

import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name="outbox_messages")
public class OutboxMessage {
    @Id
    private UUID id;

    private String aggregateType; // Order
    private String aggregateId; // Order tablosunda X idli veri
    private String eventType; // OrderCreated,OrderCanceled
    @Column(columnDefinition = "TEXT")
    private String payload;
    private String errorMessage;
    private int retryCount;

    private Instant createdAt;
    private Instant processedAt;

    @Enumerated(EnumType.STRING)
    private OutboxStatus outboxStatus;


    public UUID id() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String aggregateType() {
        return aggregateType;
    }

    public void setAggregateType(String aggregateType) {
        this.aggregateType = aggregateType;
    }

    public String aggregateId() {
        return aggregateId;
    }

    public void setAggregateId(String aggregateId) {
        this.aggregateId = aggregateId;
    }

    public String eventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public String payload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    public String errorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int retryCount() {
        return retryCount;
    }

    public void setRetryCount(int retryCount) {
        this.retryCount = retryCount;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant processedAt() {
        return processedAt;
    }

    public void setProcessedAt(Instant processedAt) {
        this.processedAt = processedAt;
    }

    public OutboxStatus outboxStatus() {
        return outboxStatus;
    }

    public void setOutboxStatus(OutboxStatus outboxStatus) {
        this.outboxStatus = outboxStatus;
    }
}

/**
 * aggregateType: Order
 * aggregateId: order'ın idsi
 * eventType: orderCreatedEvent
 * payload: {id:abc, createdDate:, items:[{}]}
 * errorMessage: null
 * retryCount: 0
 * createdAt: 09.06.2026
 * processedAt: -
 * outboxStatus: PENDING
 */