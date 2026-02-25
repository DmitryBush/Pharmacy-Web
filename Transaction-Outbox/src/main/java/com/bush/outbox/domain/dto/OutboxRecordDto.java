package com.bush.outbox.domain.dto;

import com.bush.outbox.domain.entity.CrudOperationType;

/**
 * Immutable DTO representing a domain operation to be persisted in the outbox
 * @param objectName Domain entity name
 * @param operationType The type of operation is appropriate to CRUD
 * @param payload The target object whose fields should be serialized
 * @param <T> Type of domain entity
 */
public record OutboxRecordDto<T>(String objectName, CrudOperationType operationType, T payload) {
}
