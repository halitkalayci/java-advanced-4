package com.turkcell.embeddedservers.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.kafka.test.EmbeddedKafkaKraftBroker;

import java.util.Locale;

@Configuration
public class EmbeddedKafkaConfig {

    @Bean(destroyMethod = "destroy")
    public EmbeddedKafkaKraftBroker embeddedKafkaBroker(ConfigurableEnvironment env) {
        Locale.setDefault(Locale.US);
        System.setProperty("user.language", "en");
        System.setProperty("user.country", "US");

        // ÖNEMLİ: EmbeddedKafkaKraftBroker (KafkaClusterTestKit) broker portunu SABİTLEYEMEZ.
        // - kafkaPorts(...) bu sürümde no-op'tur (JavaDoc'ta da yazar).
        // - Tüm listener'lar her zaman "localhost:0" ile prebind edilir; gerçekte
        //   PreboundSocketFactoryManager 'new InetSocketAddress(0)' kullanır, yani port her
        //   açılışta rastgeledir. advertised.listeners'ı elle sabitlemek de işe yaramaz çünkü
        //   broker fiziksel olarak o porta bind olmaz.
        // Bu yüzden portu sabitlemek yerine: broker'ı ayağa kaldırıp testkit'in seçtiği
        // GERÇEK adresi getBrokersAsString() ile okuyoruz ve bunu sabit bir HTTP endpoint'ten
        // (KafkaBrokersController, port 8090) dışarı veriyoruz. order-service başlarken bu
        // sabit adresten broker'ın o anki rastgele portunu öğrenir.
        EmbeddedKafkaKraftBroker broker =
                new EmbeddedKafkaKraftBroker(1, 1, "order-topic", "orders", "payments");
        broker.afterPropertiesSet();

        String brokers = broker.getBrokersAsString(); // testkit'in seçtiği gerçek (rastgele) adres
        System.setProperty("spring.embedded.kafka.brokers", brokers);
        env.getSystemProperties().put("spring.cloud.stream.kafka.binder.brokers", brokers);

        System.out.println("Embedded Kafka Broker started at " + brokers);
        return broker;
    }

}
