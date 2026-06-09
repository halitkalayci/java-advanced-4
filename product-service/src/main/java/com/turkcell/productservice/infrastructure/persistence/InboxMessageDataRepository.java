package com.turkcell.productservice.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InboxMessageDataRepository extends JpaRepository<InboxMessageJpaEntity, String> {
}
