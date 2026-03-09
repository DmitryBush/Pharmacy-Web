package com.bush.outbox.domain.entity;

/**
 * Enum representing the type of domain operation to be persisted in the {@link OutboxRecord}
 */
public enum CrudOperationType {
    C, R, U, D
}
