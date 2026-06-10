package com.turkcell.orderservice.controller;

import com.turkcell.orderservice.entity.Order;
import com.turkcell.orderservice.entity.OutboxMessage;
import com.turkcell.orderservice.entity.OutboxStatus;
import com.turkcell.orderservice.event.OrderCreatedEvent;
import com.turkcell.orderservice.repository.OrderRepository;
import com.turkcell.orderservice.repository.OutboxMessageRepository;
import jakarta.transaction.Transactional;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tools.jackson.databind.ObjectMapper;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@RequestMapping("/api/v1/orders")
@RestController
public class OrdersController {
    private final OrderRepository orderRepository;
    private final OutboxMessageRepository outboxMessageRepository;
    private final ObjectMapper objectMapper;

    public OrdersController(OrderRepository orderRepository, OutboxMessageRepository outboxMessageRepository, ObjectMapper objectMapper) {
        this.orderRepository = orderRepository;
        this.outboxMessageRepository = outboxMessageRepository;
        this.objectMapper = objectMapper;
    }


    @PostMapping
    @Transactional // İlgili fonksiyonun içerisinde outboxTable'a veri yazma işlemi varsa kesinlike @Transactional olmalı.
    public ResponseEntity<String> create(@AuthenticationPrincipal Jwt jwt) {

        UUID userId = UUID.fromString(jwt.getSubject());

        Order order = new Order();
        order.setOrderDate(Instant.now());
        order.setTotalAmount(BigDecimal.valueOf(1203));
        order.setUserId(userId);
        orderRepository.save(order);

        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(
                UUID.randomUUID(),
                order.id(),
                order.orderDate(),
                order.totalAmount()
        );

        // Direkt Kafkaya Göndermek... -> YANLIŞ

        // KAFKAYA gönderilecek eventi persiste et.
        OutboxMessage outboxMessage = new OutboxMessage();
        outboxMessage.setId(UUID.randomUUID());
        outboxMessage.setAggregateType(Order.class.getSimpleName());
        outboxMessage.setAggregateId(order.id().toString());
        outboxMessage.setCreatedAt(Instant.now());
        outboxMessage.setEventType("orderCreatedEvent");
        outboxMessage.setPayload(objectMapper.writeValueAsString(orderCreatedEvent));
        outboxMessage.setOutboxStatus(OutboxStatus.PENDING);

        outboxMessageRepository.save(outboxMessage);

        return ResponseEntity.ok("Sipariş alındı");
    }
}
