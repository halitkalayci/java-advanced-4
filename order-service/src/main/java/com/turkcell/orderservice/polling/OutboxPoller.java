package com.turkcell.orderservice.polling;

import com.turkcell.orderservice.entity.OutboxMessage;
import com.turkcell.orderservice.repository.OutboxMessageRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class OutboxPoller {
    private final OutboxMessageRepository outboxMessageRepository;

    public OutboxPoller(OutboxMessageRepository outboxMessageRepository) {
        this.outboxMessageRepository = outboxMessageRepository;
    }

    @Scheduled(fixedDelay = 20000)
    public void publishPendingEvents() {
        Set<OutboxMessage> events = outboxMessageRepository.findPublishable(100);

        System.out.println("Gönderilmesi beklenen event sayısı: " + events.size());
    }
}
