package com.turkcell.orderservice.controller;

import com.turkcell.orderservice.entity.Order;
import com.turkcell.orderservice.entity.OutboxMessage;
import com.turkcell.orderservice.entity.OutboxStatus;
import com.turkcell.orderservice.event.OrderCreatedEvent;
import com.turkcell.orderservice.repository.OrderRepository;
import com.turkcell.orderservice.repository.OutboxMessageRepository;
import org.aspectj.weaver.ast.Or;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> create() {
        Order order = new Order();
        order.setOrderDate(Instant.now());
        order.setTotalAmount(BigDecimal.valueOf(1203));
        orderRepository.save(order);

        OrderCreatedEvent orderCreatedEvent = new OrderCreatedEvent(
                order.id(),
                order.orderDate(),
                order.totalAmount()
        );

        OutboxMessage outboxMessage = new OutboxMessage();
        outboxMessage.setId(UUID.randomUUID());
        outboxMessage.setAggregateType(Order.class.getSimpleName());
        outboxMessage.setAggregateId(order.id().toString());
        outboxMessage.setCreatedAt(Instant.now());
        outboxMessage.setEventType(OrderCreatedEvent.class.getSimpleName());
        outboxMessage.setPayload(objectMapper.writeValueAsString(orderCreatedEvent));
        outboxMessage.setOutboxStatus(OutboxStatus.PENDING);

        outboxMessageRepository.save(outboxMessage);

        return ResponseEntity.ok("Sipariş alındı");
    }
}
