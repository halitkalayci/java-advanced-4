package com.turkcell.productservice.infrastructure.persistence;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name="inbox_messages")
public class InboxMessageJpaEntity {
    @Id
    @Column(name="message_id",nullable = false, updatable = false)
    private String messageId;

    @Column(name="processed_at", nullable = false, updatable = false)
    private Instant processedAt;

    public InboxMessageJpaEntity()  {}

    public InboxMessageJpaEntity(String messageId, Instant processedAt) {
        this.messageId = messageId;
        this.processedAt = processedAt;
    }

    public String messageId() {
        return messageId;
    }

    public Instant processedAt() {
        return processedAt;
    }
}
