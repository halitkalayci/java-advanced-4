package com.turkcell.embeddedservers.controller;

import org.springframework.kafka.test.EmbeddedKafkaKraftBroker;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Embedded Kafka broker'ın o anki GERÇEK adresini (rastgele port) sabit bir HTTP
 * endpoint üzerinden dışarı verir. order-service başlarken bu adresi okuyup binder'ı
 * doğru porta yönlendirir. Broker portu her açılışta değişir; bu endpoint'in portu
 * (application.yaml -> server.port: 8090) sabittir, keşif noktası budur.
 */
@RestController
public class KafkaBrokersController {

    private final EmbeddedKafkaKraftBroker broker;

    public KafkaBrokersController(EmbeddedKafkaKraftBroker broker) {
        this.broker = broker;
    }

    @GetMapping("/kafka/brokers")
    public String brokers() {
        return broker.getBrokersAsString();
    }
}
