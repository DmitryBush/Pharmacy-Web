package com.bush.search.event.kafka.index.handler.strategy.index;

import com.bush.search.domain.index.CrudOperationConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;

@Component
@RequiredArgsConstructor
public class ResolveIndexStrategyRegistry {
    private final HashSet<ResolveIndexStrategy> resolveIndexStrategies;

    public void resolveIndexStrategy(String objectName, CrudOperationConstants operation, String jsonPayload) {
        resolveIndexStrategies.stream()
                .filter(resolveIndexStrategy -> resolveIndexStrategy.isProcessingSupported(objectName))
                .findFirst()
                .ifPresentOrElse(strategy -> strategy.indexObject(jsonPayload, operation),
                        () -> {
                            throw new IllegalArgumentException("There is no handler for this " +
                                    objectName + " object in bean registry");
                        });
    }
}
