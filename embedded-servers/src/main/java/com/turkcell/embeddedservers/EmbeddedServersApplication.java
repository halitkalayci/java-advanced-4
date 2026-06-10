package com.turkcell.embeddedservers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.kafka.autoconfigure.KafkaAutoConfiguration;

// embedded-servers yalnızca broker'ı host eder; kendisi Kafka client'ı DEĞİLDİR.
// KafkaAutoConfiguration'ı dışlıyoruz, aksi halde Boot bir KafkaAdmin oluşturup
// broker'a bağlanmaya çalışır ve "Connection to node ... could not be established" spam'i üretir.
@SpringBootApplication(exclude = KafkaAutoConfiguration.class)
public class EmbeddedServersApplication {

    public static void main(String[] args) {
        SpringApplication.run(EmbeddedServersApplication.class, args);
    }

}
