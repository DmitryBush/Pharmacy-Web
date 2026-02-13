package com.bush.outbox.service;

import com.bush.outbox.domain.dto.OutboxRecordDto;
import com.bush.outbox.repository.OutboxRepository;
import com.bush.outbox.service.mapper.OutboxCreateMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service for publishing outbox records
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true, transactionManager = "outboxTransactionManager")
public class OutboxService {
    private final OutboxRepository outboxMetadataRepository;

    private final OutboxCreateMapper outboxCreateMapper;

    /**
     * Creates an outbox record if the transaction is open
     * @param outboxMetadataDto Immutable DTO representing a domain operation to be persisted in the outbox
     * @return Domain payload object
     * @param <T> Type of domain entity
     */
    @Transactional(propagation = Propagation.MANDATORY, transactionManager = "outboxTransactionManager")
    public <T> T createRecord(OutboxRecordDto<T> outboxMetadataDto) {
        Optional.of(outboxMetadataDto)
                .map(outboxCreateMapper::mapToOutboxMetadata)
                .map(outboxMetadataRepository::save);
        return outboxMetadataDto.payload();
    }
}
