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