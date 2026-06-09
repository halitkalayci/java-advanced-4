package com.turkcell.productservice.infrastructure.messaging.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderCreatedEvent(UUID eventId,UUID id, Instant orderDate, BigDecimal totalAmount) { }
