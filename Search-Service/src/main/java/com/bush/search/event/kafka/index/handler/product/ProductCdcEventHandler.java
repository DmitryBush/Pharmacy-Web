package com.bush.search.event.kafka.index.handler.product;

import com.bush.search.domain.index.CdcEvent;
import com.bush.search.event.kafka.index.handler.CdcEventHandler;
import com.bush.search.event.kafka.index.handler.strategy.index.ResolveIndexStrategyRegistry;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@KafkaListener(topics = "search-pharmacies.public.outbox_service_table", groupId = "product-processing-group")
@RequiredArgsConstructor
public class ProductCdcEventHandler implements CdcEventHandler<String> {
    private final ResolveIndexStrategyRegistry indexStrategyRegistry;

    @Override
    @KafkaHandler
    public void handle(CdcEvent<String> event) {
        indexStrategyRegistry.resolveIndexStrategy(event.objectName(), event.crudOperationConstantsType(), event.payload());
    }
}
