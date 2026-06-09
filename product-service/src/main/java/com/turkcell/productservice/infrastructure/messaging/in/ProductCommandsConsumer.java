package com.turkcell.productservice.infrastructure.messaging.in;

import com.turkcell.productservice.infrastructure.messaging.event.OrderCreatedEvent;
import com.turkcell.productservice.infrastructure.persistence.InboxMessageDataRepository;
import com.turkcell.productservice.infrastructure.persistence.InboxMessageJpaEntity;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Instant;
import java.util.function.Consumer;

@Configuration
public class ProductCommandsConsumer
{
    private final InboxMessageDataRepository inboxMessageDataRepository;

    public ProductCommandsConsumer(InboxMessageDataRepository inboxMessageDataRepository) {
        this.inboxMessageDataRepository = inboxMessageDataRepository;
    }

    @Bean
    public Consumer<OrderCreatedEvent> orderCreatedEvent() {
        return event -> {
            InboxMessageJpaEntity inboxMessage = inboxMessageDataRepository.findById(event.eventId().toString()).orElse(null);

            if(inboxMessage!=null)
            {
                System.out.println("Bu mesaj zaten işlenmiş, es geçiliyor..");
                return;
            }

            System.out.println("Event yakalandı ve işlendi..: " + event.toString());

            inboxMessage = new InboxMessageJpaEntity(event.eventId().toString(), Instant.now());
            inboxMessageDataRepository.save(inboxMessage);
        };
    }
}
