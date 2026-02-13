package com.bush.outbox.service.mapper;

import com.bush.outbox.domain.dto.OutboxRecordDto;
import com.bush.outbox.domain.entity.OutboxRecord;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.Named;

import java.time.ZonedDateTime;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface OutboxCreateMapper {
    @Mapping(target = "payload", source = "payload", qualifiedByName = "convertToJsonPayload")
    @Mapping(target = "operationId", ignore = true)
    @Mapping(target = "createdAt", expression = "java(getCurrentTime())")
    OutboxRecord mapToOutboxMetadata(OutboxRecordDto<?> outboxRecordDto);

    @Named("convertToJsonPayload")
    default String convertToJsonPayload(Object payload) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper.writeValueAsString(payload);
    }

    @Named("getCurrentTime")
    default ZonedDateTime getCurrentTime() {
        return ZonedDateTime.now();
    }
}
