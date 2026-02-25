package com.bush.outbox.repository;

import com.bush.outbox.domain.entity.OutboxRecord;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OutboxRepository extends JpaRepository<OutboxRecord, UUID> {
}
