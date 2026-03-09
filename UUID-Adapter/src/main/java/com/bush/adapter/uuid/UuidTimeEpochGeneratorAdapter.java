package com.bush.adapter.uuid;

import com.fasterxml.uuid.Generators;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import org.hibernate.id.uuid.UuidValueGenerator;

import java.util.UUID;

/**
 * Adapter class for Hibernate that implements UUIDv7 generation
 */
public class UuidTimeEpochGeneratorAdapter implements UuidValueGenerator {
    @Override
    public UUID generateUuid(SharedSessionContractImplementor sharedSessionContractImplementor) {
        return Generators.timeBasedEpochRandomGenerator().generate();
    }
}
