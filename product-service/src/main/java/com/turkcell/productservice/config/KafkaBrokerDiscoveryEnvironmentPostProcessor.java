package com.turkcell.productservice.config;

import org.springframework.boot.EnvironmentPostProcessor;
import org.springframework.boot.SpringApplication;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * Embedded Kafka broker (embedded-servers modülü) her açılışta RASTGELE bir porta bind olur;
 * sabitlenemez (KafkaClusterTestKit sınırlaması). Bu yüzden product-service ayağa kalkarken,
 * embedded-servers'ın SABİT HTTP portundaki (8090) /kafka/brokers endpoint'inden broker'ın
 * o anki gerçek adresini öğrenir ve binder'ı buna yönlendirir.
 *
 * Bu bir EnvironmentPostProcessor'dır: Spring Cloud Stream binder'ı ayağa kalkmadan ÇOK önce,
 * environment hazırlanırken çalışır. Böylece broker adresi binder bağlanmadan set edilmiş olur.
 *
 * Kayıt: src/main/resources/META-INF/spring.factories
 */
public class KafkaBrokerDiscoveryEnvironmentPostProcessor implements EnvironmentPostProcessor {

    private static final String DISCOVERY_URL_PROPERTY = "embedded.kafka.discovery-url";
    private static final String DEFAULT_DISCOVERY_URL = "http://localhost:8090/kafka/brokers";

    // Toplam bekleme: embedded-servers henüz ayakta olmayabilir, kısa aralıklarla yeniden dene.
    private static final Duration TOTAL_TIMEOUT = Duration.ofSeconds(60);
    private static final Duration RETRY_INTERVAL = Duration.ofSeconds(2);

    private final HttpClient httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(3))
            .build();

    @Override
    public void postProcessEnvironment(ConfigurableEnvironment environment, SpringApplication application) {
        String discoveryUrl = environment.getProperty(DISCOVERY_URL_PROPERTY, DEFAULT_DISCOVERY_URL);

        String brokers = discoverBrokers(discoveryUrl);
        if (brokers == null) {
            System.err.println("[kafka-discovery] Broker adresi " + TOTAL_TIMEOUT.toSeconds()
                    + "sn içinde öğrenilemedi (" + discoveryUrl
                    + "). embedded-servers ayakta mı? application.yaml'daki broker ayarıyla devam ediliyor.");
            return;
        }

        Map<String, Object> props = new HashMap<>();
        props.put("spring.cloud.stream.kafka.binder.brokers", brokers);
        props.put("spring.kafka.bootstrap-servers", brokers);
        // addFirst: application.yaml dahil diğer kaynakların önüne geçsin.
        environment.getPropertySources().addFirst(new MapPropertySource("embeddedKafkaBrokerDiscovery", props));

        System.out.println("[kafka-discovery] Embedded Kafka broker adresi keşfedildi: " + brokers);
    }

    private String discoverBrokers(String discoveryUrl) {
        long deadline = System.nanoTime() + TOTAL_TIMEOUT.toNanos();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(discoveryUrl))
                .timeout(Duration.ofSeconds(3))
                .GET()
                .build();

        while (System.nanoTime() < deadline) {
            try {
                HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
                if (response.statusCode() == 200) {
                    String body = response.body().trim();
                    if (!body.isEmpty()) {
                        return body;
                    }
                }
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return null;
            }
            catch (Exception ex) {
                // embedded-servers henüz hazır değil; sessizce yeniden dene.
            }

            try {
                Thread.sleep(RETRY_INTERVAL.toMillis());
            }
            catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
                return null;
            }
        }
        return null;
    }
}
