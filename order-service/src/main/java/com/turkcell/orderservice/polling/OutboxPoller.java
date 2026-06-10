package com.turkcell.orderservice.polling;

import com.turkcell.orderservice.entity.OutboxMessage;
import com.turkcell.orderservice.entity.OutboxStatus;
import com.turkcell.orderservice.repository.OutboxMessageRepository;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.Set;

@Component
public class OutboxPoller {
    private final OutboxMessageRepository outboxMessageRepository;
    private final StreamBridge streamBridge;
    private static final int MAX_RETRY = 5;

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
                streamBridge.send(event.eventType() + "-out-0",
                        MessageBuilder.withPayload(event.payload())
                                .setHeader(KafkaHeaders.KEY, event.id().toString().getBytes(StandardCharsets.UTF_8))
                                .build()
                );


                event.setOutboxStatus(OutboxStatus.SENT);
            } catch(Exception e)
            {
                if(event.retryCount() + 1 >- MAX_RETRY)
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
