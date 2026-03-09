package com.bush.outbox.domain.entity;

import com.bush.adapter.uuid.UuidTimeEpochGeneratorAdapter;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * Entity class that collects general information for outbox pattern
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "outbox_service_table")
public class OutboxRecord {
    @Id
    @Column(name = "operation_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    @UuidGenerator(algorithm = UuidTimeEpochGeneratorAdapter.class)
    @JdbcTypeCode(SqlTypes.UUID)
    private UUID operationId;
    /**
     * Domain entity name
     */
    @Column(name = "object_name", nullable = false)
    private String objectName;
    @Column(name = "operation_type")
    @Enumerated(EnumType.STRING)
    private CrudOperationType operationType;
    @Column(name = "created_at", nullable = false)
    private ZonedDateTime createdAt;
    /**
     * Target domain record, which was serialized to JSON
     */
    @Column(nullable = false)
    private String payload;
}
