package com.bush.search.event.kafka.index.handler.strategy.crud;

import com.bush.search.domain.index.CrudOperationConstants;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractCrudOperationStrategy<S, P> implements CrudOperationStrategy {
    private final CrudOperationConstants processingOperation;
    private final Class<S> serviceClazz;
    private final Class<P> payloadClazz;

    @Override
    public boolean isProcessingSupport(CrudOperationConstants crudOperationConstantsType, Class<?> requiredService,
                                       Class<?> requiredPayloadClazz) {
        return processingOperation.equals(crudOperationConstantsType) && requiredService.isAssignableFrom(serviceClazz)
                && requiredPayloadClazz.isAssignableFrom(payloadClazz);
    }

    @Override
    public void process(Object payload) {
        if (payload.getClass().isAssignableFrom(payloadClazz)) {
            processInternal(payloadClazz.cast(payloadClazz));
        } else {
            throw new IllegalArgumentException("An error has occurred when casting a payload to required class");
        }
    }

    protected abstract void processInternal(P payload);
}
