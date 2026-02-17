package com.bush.search.event.kafka.index.handler.product.index;

import com.bush.search.domain.index.CrudOperationConstants;
import com.bush.search.domain.index.product.ProductPayload;
import com.bush.search.event.kafka.index.handler.strategy.crud.CrudOperationStrategyRegistry;
import com.bush.search.event.kafka.index.handler.strategy.index.ResolveIndexStrategy;
import com.bush.search.service.product.ProductService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductResolveIndexStrategy implements ResolveIndexStrategy {
    private final ObjectMapper objectMapper;
    private final CrudOperationStrategyRegistry crudOperationStrategyRegistry;
    @Override
    public boolean isProcessingSupported(String objectName) {
        return objectName.equals("product");
    }

    @Override
    public void indexObject(String jsonPayload, CrudOperationConstants operation) {
        try {
            ProductPayload productPayload = objectMapper.readValue(jsonPayload, ProductPayload.class);
            crudOperationStrategyRegistry.resolveCrudOperation(operation, ProductService.class, productPayload);
        } catch (JsonProcessingException e) {
            log.error("An error has occurred while parsing JSON - {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
