package com.turkcell.orderservice.event;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record OrderCreatedEvent(UUID id, Instant orderDate, BigDecimal totalAmount) { }
