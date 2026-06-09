package com.turkcell.orderservice.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/orders")
@RestController
public class OrdersController {

    @PostMapping
    public ResponseEntity<String> create() {
        System.out.println("Order Creation Simulation...");

        return ResponseEntity.ok("Sipariş alındı");
    }
}
