package com.turkcell.orderservice.polling;

import com.turkcell.orderservice.entity.OutboxMessage;
import com.turkcell.orderservice.entity.OutboxStatus;
import com.turkcell.orderservice.repository.OutboxMessageRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Set;

@Component
public class OutboxPoller {
    private final OutboxMessageRepository outboxMessageRepository;
    private final StreamBridge streamBridge;

    public OutboxPoller(OutboxMessageRepository outboxMessageRepository, StreamBridge streamBridge) {
        this.outboxMessageRepository = outboxMessageRepository;
        this.streamBridge = streamBridge;
    }

    @Scheduled(fixedDelay = 20000)
    public void publishPendingEvents() {
        Set<OutboxMessage> events = outboxMessageRepository.findPublishable(100);

        for(OutboxMessage event: events)
        {
            try{
                // OrderCreatedEvent-out-0 ->
                // order-topic
                // {"id":"395087ec-07a5-49ed-813f-0c4d16f87685","orderDate":"2026-06-09T11:34:24.960878100Z","totalAmount":1203}
                streamBridge.send(event.eventType() + "-out-0", event.payload());
                event.setOutboxStatus(OutboxStatus.SENT);
            } catch(Exception e)
            {
                if(event.retryCount() >= 5)
                {
                    event.setErrorMessage(e.getMessage());
                    event.setOutboxStatus(OutboxStatus.FAILED);
                }
                else
                    event.setRetryCount(event.retryCount() + 1);
            }
            event.setProcessedAt(Instant.now());
            outboxMessageRepository.save(event);
        }

        System.out.println("Gönderilmesi beklenen event sayısı: " + events.size());
    }
}
