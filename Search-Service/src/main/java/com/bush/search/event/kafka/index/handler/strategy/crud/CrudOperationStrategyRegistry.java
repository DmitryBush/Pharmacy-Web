package com.bush.search.event.kafka.index.handler.strategy.crud;

import com.bush.search.domain.index.CrudOperationConstants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Slf4j
@Component
@RequiredArgsConstructor
public class CrudOperationStrategyRegistry {
    private final HashSet<CrudOperationStrategy> crudOperationStrategies;

    public <S, P> void resolveCrudOperation(CrudOperationConstants operation, Class<S> serviceClazz, P payload) {
        crudOperationStrategies.stream()
                .filter(crudOperationStrategy ->
                        crudOperationStrategy.isProcessingSupport(operation, serviceClazz, payload.getClass()))
                .findFirst()
                .ifPresentOrElse(crudOperationStrategy -> crudOperationStrategy.process(payload),
                        () -> {
                            log.error("There is no handler for {} and  {} operation type", serviceClazz, operation);
                            throw new IllegalArgumentException("There is no handler for " + serviceClazz
                                    + "and " + operation + " operation type");
                        });
    }
}
