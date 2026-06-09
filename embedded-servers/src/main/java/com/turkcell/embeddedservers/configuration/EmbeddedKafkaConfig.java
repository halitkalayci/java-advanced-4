package com.turkcell.embeddedservers.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.EmbeddedKafkaKraftBroker;

import java.util.Locale;

@Configuration
public class EmbeddedKafkaConfig {

    @Bean(destroyMethod = "destroy")
    public EmbeddedKafkaKraftBroker embeddedKafkaBroker(ConfigurableEnvironment env) {
        Locale.setDefault(Locale.US);
        System.setProperty("user.language", "en");
        System.setProperty("user.country", "US");

        // EmbeddedKafkaKraftBroker listener'ları (PLAINTEXT + CONTROLLER) kendi içinde
        // KafkaClusterTestKit ile yönetir. Portu elle "listeners" property'siyle sabitlemek
        // çalışmaz; testkit kendi değerini üretip onu ezer ve broker rastgele porta bağlanır.
        // Doğru yol: kafkaPorts(...) ile portu sabitlemek.
        EmbeddedKafkaKraftBroker broker =
                new EmbeddedKafkaKraftBroker(1, 1, "orders", "payments")
                        .kafkaPorts(29023);

        broker.afterPropertiesSet();

        String brokers = "127.0.0.1:29023"; // client’ların göreceği adres
        System.setProperty("spring.embedded.kafka.brokers", brokers);
        env.getSystemProperties().put("spring.cloud.stream.kafka.binder.brokers", brokers);

        System.out.println("Embedded Kafka Broker started " + brokers);
        return broker;
    }

}
