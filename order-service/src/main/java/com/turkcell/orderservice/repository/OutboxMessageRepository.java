package com.turkcell.orderservice.repository;

import com.turkcell.orderservice.entity.OutboxMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;
import java.util.UUID;

public interface OutboxMessageRepository extends JpaRepository<OutboxMessage, UUID> {
    @Query(value = """
                    Select * from outbox_messages where status = 'PENDING' and retry_count < 5
                    ORDER BY created_at
                    LIMIT :limit
                    """, nativeQuery = true)
    Set<OutboxMessage> findPublishable(@Param("limit") int limit);
}
